package servlets;

import api.IBook;
import api.ILibrary;
import api.ILoan;
import api.IUser;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ReservationServlet extends HttpServlet {

    private static final String VIEW_HOME = "/WEB-INF/home.jsp";
    private static final String VIEW_RESERVATION = "/WEB-INF/reservation.jsp";

    private static final String ATTR_SESSION_USER = "user_session";
    private static final String ATTR_BOOKLIST = "booklist";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        if (session.getAttribute(ATTR_SESSION_USER) == null) {
            RequestDispatcher rd = request.getRequestDispatcher(VIEW_HOME);
            rd.forward(request, response);
        } else {
            try {
                IUser user = (IUser) session.getAttribute(ATTR_SESSION_USER);
                ILibrary library = (ILibrary) Naming.lookup("rmi://localhost:1099/library");
                List<ILoan> loanlist = library.findLoanByUserID(user.getUserID());
                if (!loanlist.isEmpty()) {
                    List<IBook> booklist = new ArrayList<>();
                    for (ILoan loan : loanlist) {
                        booklist.add(library.findBookByIsbn(loan.getIsbn()));
                    }
                    request.setAttribute(ATTR_BOOKLIST, booklist);
                }
            } catch (NotBoundException | MalformedURLException | RemoteException e) {
                System.out.println("Trouble: " + e);
            }
            RequestDispatcher rd = request.getRequestDispatcher(VIEW_RESERVATION);
            rd.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        if (session.getAttribute(ATTR_SESSION_USER) == null) {
            RequestDispatcher rd = request.getRequestDispatcher(VIEW_HOME);
            rd.forward(request, response);
        } else {
            try {
                long isbn = Long.parseLong(request.getParameter("isbn"));
                ILibrary library = (ILibrary) Naming.lookup("rmi://localhost:1099/library");
                library.deleteLoan(isbn);
            } catch (NotBoundException | MalformedURLException | RemoteException e) {
                System.out.println("Trouble: " + e);
            }
            RequestDispatcher rd = request.getRequestDispatcher(VIEW_RESERVATION);
            rd.forward(request, response);
        }
    }

}

package servlets;

import api.ILibrary;
import api.IUser;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ReserverServlet extends HttpServlet {

    private static final String VIEW_HOME = "/WEB-INF/home.jsp";
    private static final String VIEW_RESERVATION = "/WEB-INF/reservation.jsp";
    private static final String VIEW_BOOKLIST = "/WEB-INF/listbook.jsp";

    private static final String ATTR_SESSION_USER = "user_session";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        if (!request.getParameter("isbn").isEmpty()) {
            long isbn = Long.parseLong(request.getParameter("isbn"));

            if (session.getAttribute(ATTR_SESSION_USER) == null) {
                RequestDispatcher rd = request.getRequestDispatcher(VIEW_HOME);
                rd.forward(request, response);
            } else {
                try {
                    IUser user = (IUser) session.getAttribute(ATTR_SESSION_USER);
                    ILibrary library = (ILibrary) Naming.lookup("rmi://localhost:1099/library");
                    if (library.findLoanByIsbn(isbn) == null || library.findLoanByIsbn(isbn).getUserID() != user.getUserID()) {
                        library.createLoan(isbn, user.getUserID());
                        RequestDispatcher rd = request.getRequestDispatcher(VIEW_RESERVATION);
                        rd.forward(request, response);
                    }
                } catch (NotBoundException | MalformedURLException | RemoteException e) {
                    System.out.println("Trouble: " + e);
                }
            }
        }
        RequestDispatcher rd = request.getRequestDispatcher(VIEW_BOOKLIST);
        rd.forward(request, response);
    }
}

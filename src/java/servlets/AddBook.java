package servlets;

import api.ILibrary;
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

public class AddBook extends HttpServlet {

    private static final String VIEW_CATALOG = "/WEB-INF/catalog.jsp";
    private static final String VIEW_HOME = "/WEB-INF/home.jsp";

    private static final String ATTR_SESSION_USER = "user_session";
    private static final String ATTR_MESSAGE = "message";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        if (session.getAttribute(ATTR_SESSION_USER) != null) {
            long isbn = Long.parseLong(request.getParameter("isbn"));
            String title = request.getParameter("title");
            String author = request.getParameter("author");

            try {
                ILibrary library = (ILibrary) Naming.lookup("rmi://localhost:1099/library");
                library.createBook(isbn, title, author);
                request.setAttribute(ATTR_MESSAGE, "Livre ajouté");
                RequestDispatcher rd = request.getRequestDispatcher(VIEW_CATALOG);
                rd.forward(request, response);

            } catch (NotBoundException | MalformedURLException | RemoteException e) {
                System.out.println("Trouble: " + e);
            }
        } else {
            RequestDispatcher rd = request.getRequestDispatcher(VIEW_HOME);
            rd.forward(request, response);
        }
    }
}

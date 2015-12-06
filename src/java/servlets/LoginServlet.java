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

public class LoginServlet extends HttpServlet {

    private static final String VIEW_LOGIN = "/WEB-INF/login.jsp";
    private static final String VIEW_HOME = "/WEB-INF/home.jsp";

    private static final String ATTR_SESSION_USER = "user_session";
    private static final String ATTR_MESSAGE = "message";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute(ATTR_SESSION_USER) == null) {
            RequestDispatcher rd = request.getRequestDispatcher(VIEW_LOGIN);
            rd.forward(request, response);
        } else {
            RequestDispatcher rd = request.getRequestDispatcher(VIEW_HOME);
            rd.forward(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String message;

        if (session.getAttribute(ATTR_SESSION_USER) == null) {
            String lname = request.getParameter("lname");
            String fname = request.getParameter("fname");

            try {
                ILibrary library = (ILibrary) Naming.lookup("rmi://localhost:1099/library");
                IUser user = library.findUserByFullName(lname, fname);
                if (user == null) {
                    message = "Error login";
                    request.setAttribute(ATTR_MESSAGE, message);
                    RequestDispatcher rd = request.getRequestDispatcher(VIEW_LOGIN);
                    rd.forward(request, response);
                } else {
                    message = "Success login";
                    request.setAttribute(ATTR_MESSAGE, message);
                    session.setAttribute(ATTR_SESSION_USER, user);
                    RequestDispatcher rd = request.getRequestDispatcher(VIEW_HOME);
                    rd.forward(request, response);
                }

            } catch (NotBoundException | MalformedURLException | RemoteException e) {
                System.out.println("Trouble: " + e);
            }
        } else {
            RequestDispatcher rd = request.getRequestDispatcher(VIEW_HOME);
            rd.forward(request, response);
        }
    }

}

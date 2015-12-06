package servlets;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutServlet extends HttpServlet {

    private static final String VIEW_HOME = "/WEB-INF/home.jsp";

    private static final String ATTR_SESSION_USER = "user_session";
    private static final String ATTR_MESSAGE = "message";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String message;

        if (session.getAttribute(ATTR_SESSION_USER) == null) {
            RequestDispatcher rd = request.getRequestDispatcher(VIEW_HOME);
            rd.forward(request, response);
        } else {
            message = "Success logout";
            request.setAttribute(ATTR_MESSAGE, message);
            session.setAttribute(ATTR_SESSION_USER, null);
            RequestDispatcher rd = request.getRequestDispatcher(VIEW_HOME);
            rd.forward(request, response);
        }
    }

}

package servlets;

import api.IBook;
import api.ILibrary;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ListBookServlet extends HttpServlet {

    private static final String VIEW_LISTBOOK = "/WEB-INF/listbook.jsp";

    private static final String ATTR_SESSION_USER = "user_session";
    private static final String ATTR_LISTBOOK = "list_book";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            ILibrary library = (ILibrary) Naming.lookup("rmi://localhost:1099/library");
            List<IBook> booklist = library.findBookAll();
            request.setAttribute(ATTR_LISTBOOK, booklist);
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            System.out.println("Trouble: " + e);
        }
        RequestDispatcher rd = request.getRequestDispatcher(VIEW_LISTBOOK);
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

}

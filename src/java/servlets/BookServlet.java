package servlets;

import api.IBook;
import api.IComment;
import api.ILibrary;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Random;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BookServlet extends HttpServlet {

    private static final String VIEW_BOOK = "/WEB-INF/book.jsp";
    private static final String VIEW_HOME = "/WEB-INF/home.jsp";

    private static final String ATTR_BOOK = "book";
    private static final String ATTR_COMMENTLIST = "commentlist";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!request.getParameter("isbn").isEmpty()) {
            try {
                long isbn = Long.parseLong(request.getParameter("isbn"));
                ILibrary library = (ILibrary) Naming.lookup("rmi://localhost:1099/library");
                IBook book = library.findBookByIsbn(isbn);
                if (book != null) {
                    request.setAttribute(ATTR_BOOK, book);
                    List<IComment> commentlist = library.findCommentByIsbn(isbn);
                    if (!commentlist.isEmpty()) {
                        request.setAttribute(ATTR_COMMENTLIST, commentlist);
                    }
                    RequestDispatcher rd = request.getRequestDispatcher(VIEW_BOOK);
                    rd.forward(request, response);
                } else {
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        if (request.getParameter("comment") != null) {
            try {
                long isbn = Long.parseLong(request.getParameter("isbn"));
                String comment = (String) request.getParameter("comment");
                ILibrary library = (ILibrary) Naming.lookup("rmi://localhost:1099/library");
                long range = 1234567L;
                Random r = new Random();
                long number = (long) (r.nextDouble() * range);
                library.createComment(number, isbn, comment);
            } catch (NotBoundException | MalformedURLException | RemoteException e) {
                System.out.println("Trouble: " + e);
            }
        }
        RequestDispatcher rd = request.getRequestDispatcher(VIEW_BOOK);
        rd.forward(request, response);
    }

}

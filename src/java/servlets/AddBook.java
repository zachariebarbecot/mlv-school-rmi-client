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

public class AddBook extends HttpServlet {

    private static final String VIEW_BOOKLIST = "/WEB-INF/listbook.jsp";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        long isbn = Long.parseLong(request.getParameter("isbn"));
        String title = request.getParameter("title");
        String author = request.getParameter("author");

        try {
            ILibrary library = (ILibrary) Naming.lookup("rmi://localhost:1099/library");
            library.createBook(isbn, title, author);
            RequestDispatcher rd = request.getRequestDispatcher(VIEW_BOOKLIST);
            rd.forward(request, response);

        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            System.out.println("Trouble: " + e);
        }
    }
}

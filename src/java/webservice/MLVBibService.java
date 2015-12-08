package webservice;

import api.IBook;
import api.ILibrary;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

@WebService(serviceName = "MLVBibService")
public class MLVBibService {

    @WebMethod(operationName = "catalog")
    public List<Book> getCatalog() {
        try {
            ILibrary library = (ILibrary) Naming.lookup("rmi://localhost:1099/library");
            List<Book> catalog = new ArrayList<>();
            library.findBookAll().forEach((book) -> {
                try {
                    int year = LocalDateTime.now().getYear() - book.getCreated().getYear();
                    if (book.getCounter() >= 1 && year >= 2) {
                        catalog.add(new Book(book));
                    }
                } catch (RemoteException e) {
                    System.out.println("Trouble: " + e);
                }
            });
            return catalog;
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            System.out.println("Trouble: " + e);
        }
        return null;
    }

    @WebMethod(operationName = "available")
    public Boolean isAvailable(@WebParam(name = "isbn") long isbn) {
        try {
            ILibrary library = (ILibrary) Naming.lookup("rmi://localhost:1099/library");
            IBook book = library.findBookByIsbn(isbn);
            int year = LocalDateTime.now().getYear() - book.getCreated().getYear();
            if (library.findLoanByIsbn(isbn) == null
                    && (book.getCounter() >= 1 && year >= 2)) {
                return true;
            }
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            System.out.println("Trouble: " + e);
        }
        return false;
    }
}

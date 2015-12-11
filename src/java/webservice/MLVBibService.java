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

    private Cart cart = new Cart();
    private List<Book> catalog = new ArrayList<>();

    @WebMethod(operationName = "intiCatalog")
    public synchronized List<Book> initCatalog() {
        try {
            catalog = new ArrayList<>();
            ILibrary library = (ILibrary) Naming.lookup("rmi://localhost:1099/library");
            library.findBookAll().forEach((book) -> {
                try {
                    if (isAvailable(book.getIsbn())) {
                        Book b = new Book();
                        b.setIsbn(book.getIsbn());
                        b.setTitle(book.getTitle());
                        b.setAuthor(book.getAuthor());
                        b.setPrice(5.99);
                        catalog.add(b);
                    }
                } catch (RemoteException e) {
                    System.out.println("Trouble: " + e);
                }
            });
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            System.out.println("Trouble: " + e);
        }
        return catalog;
    }

    @WebMethod(operationName = "catalog")
    public synchronized List<Book> getCatalog() {
        return initCatalog();
    }

    @WebMethod(operationName = "available")
    public synchronized Boolean isAvailable(@WebParam(name = "isbn") long isbn) {
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

    @WebMethod(operationName = "addToCart")
    public synchronized List<Book> addToCart(@WebParam(name = "isbn") long isbn) {
        for (Book book : getCatalog()) {
            if (book.getIsbn() == isbn) {
                return cart.addToCart(book);
            }
        }
        return null;
    }

    @WebMethod(operationName = "removeToCart")
    public synchronized List<Book> removeToCart(@WebParam(name = "isbn") long isbn) {
        for (Book book : cart.getItems()) {
            if (book.getIsbn() == isbn) {
                return cart.removeToCart(book);
            }
        }
        return null;
    }

    @WebMethod(operationName = "getPriceCart")
    public synchronized double getPriceCart() {
        return cart.getPriceCart();
    }

    @WebMethod(operationName = "buyCart")
    public synchronized boolean buyCart() {
        try {
            ILibrary library = (ILibrary) Naming.lookup("rmi://localhost:1099/library");
            cart.getItems().forEach((book) -> {
                try {
                    catalog.remove(book);
                    library.deleteBook(book.getIsbn());
                } catch (RemoteException e) {
                    System.out.println("Trouble: " + e);
                }
            });
            cart.getItems().clear();
            return true;
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            System.out.println("Trouble: " + e);
        }
        return false;
    }

    @WebMethod(operationName = "getCart")
    public synchronized List<Book> getCart() {
        return cart.getItems();
    }

    @WebMethod(operationName = "buyBooksInCart")
    public boolean buyBooksInCart() {
        Banque bankService = new Banque_Service().getBanquePort();
        double totalPrice = getPriceCart();

        System.out.println("Solde : " + bankService.solde() + " total Price Cart : " + totalPrice);
        if (!bankService.debit("EUR", totalPrice)) {
            return false;
        }
        return buyCart();
    }

    @WebMethod(operationName = "emptyCart")
    public void emptyCart() {
        cart.getItems().clear();
    }
}

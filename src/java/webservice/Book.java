package webservice;

import api.IBook;
import java.rmi.RemoteException;
import java.util.Random;

public class Book {

    private long isbn;
    private String title;
    private String author;
    private double price;

    public Book(IBook book) throws RemoteException {
        this.isbn = book.getIsbn();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        Random r = new Random();
        double p = 0.99 + (r.nextDouble() * (19.99 - 0.99));
        this.price = p;
    }

    public long getIsbn() {
        return isbn;
    }

    public void setIsbn(long isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrix(double price) {
        this.price = price;
    }

}

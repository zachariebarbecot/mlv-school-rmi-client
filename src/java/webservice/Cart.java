package webservice;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cart implements Serializable {

    private List<Book> items = new ArrayList<>();

    public List<Book> addToCart(Book book) {
        for (Book b : items) {
            if (book.getIsbn() == b.getIsbn()) {
                return items;
            }
        }
        items.add(book);
        return items;
    }

    public List<Book> removeToCart(Book book) {
        if (items.contains(book)) {
            items.remove(book);
        }
        return items;
    }

    public double getPriceCart() {
        double total = 0.0;
        if (!items.isEmpty()) {
            for (Book b : items) {
                total += b.getPrice();
            }
        }
        return total;
    }

    public List<Book> getItems() {
        return items;
    }

    public void setItems(List<Book> items) {
        this.items = items;
    }

}

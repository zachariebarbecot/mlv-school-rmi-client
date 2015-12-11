package webservice;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
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
        double total = 0.00;
        if (!items.isEmpty()) {
            for (Book b : items) {
                total += b.getPrice();
            }
        }
        DecimalFormat twoDForm = new DecimalFormat("#0.00");
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        twoDForm.setDecimalFormatSymbols(dfs);
        return Double.parseDouble(twoDForm.format(total));
    }

    public List<Book> getItems() {
        return items;
    }

    public void setItems(List<Book> items) {
        this.items = items;
    }

}

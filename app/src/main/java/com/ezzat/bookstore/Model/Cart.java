package com.ezzat.bookstore.Model;

import com.ezzat.bookstore.Controller.confirmOrders;

import java.io.Serializable;
import java.util.ArrayList;

public class Cart implements Serializable {

    public ArrayList<Book> books = new ArrayList<>();
    public ArrayList<String> quan = new ArrayList<String>();
    public int index;

    public void removeBook() {
        books.remove(index);
        quan.remove(index);
    }

    void removeAll() {
        books.clear();
        quan.clear();
    }

    public void confirmCart() {
        confirmOrders confirmOrders = new confirmOrders();
        for (int i = 0; i < books.size(); i++) {
            confirmOrders.execute(new String[]{books.get(i).getISBN()+"", quan.get(i)});
        }
    }
}

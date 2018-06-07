package com.ezzat.bookstore.Model;

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
}

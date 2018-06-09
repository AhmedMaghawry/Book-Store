package com.ezzat.bookstore.Model;

public class Order {

    String isbn;
    String num;

    public Order(String isbn, String num) {
        this.isbn = isbn;
        this.num = num;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}

package com.ezzat.bookstore.Model;

import java.io.Serializable;

public class Book implements Serializable {
    private int ISBN;
    private String title;
    private String publisher;
    private String[] Author;
    private String pub_year;
    private int price;
    private String category;
    private int no_copies;
    private int min_quantity;

    public Book(int ISBN, String title, String publisher, String[] Author, String pub_year, int price, String category, int no_copies, int min_quantity) {
        this.ISBN = ISBN;
        this.title = title;
        this.publisher = publisher;
        this.Author = Author;
        this.pub_year = pub_year;
        this.price = price;
        this.category = category;
        this.no_copies = no_copies;
        this.min_quantity = min_quantity;
    }

    public int getISBN() {
        return ISBN;
    }

    public String getTitle() {
        return title;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getPub_year() {
        return pub_year;
    }

    public int getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public int getNo_copies() {
        return no_copies;
    }

    public int getMin_quantity() {
        return min_quantity;
    }

    public void setISBN(int ISBN) {
        this.ISBN = ISBN;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setPub_year(String pub_year) {
        this.pub_year = pub_year;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setNo_copies(int no_copies) {
        this.no_copies = no_copies;
    }

    public void setMin_quantity(int min_quantity) {
        this.min_quantity = min_quantity;
    }

    public void setAuthor(String[] Author) {
        this.Author = Author;
    }

    public String[] getAuthor() {
        return Author;
    }
}

package com.ezzat.bookstore.Controller.cardView;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ezzat.bookstore.Model.Book;
import com.ezzat.bookstore.Model.Cart;
import com.ezzat.bookstore.R;
import com.ezzat.bookstore.View.BookInfo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by default on 11/03/18.
 */

public class BookAdapterCard extends RecyclerView.Adapter<cardHolder> {

    List<Book> books;
    View itemView;
    int priotity;
    Cart ca;

    public BookAdapterCard(List<Book> books, int priotity, Cart ca) {
        this.books = books;
        this.priotity = priotity;
        this.ca = ca;
    }

    @Override
    public cardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card, parent, false);
        return new cardHolder(itemView);
    }

    @Override
    public void onBindViewHolder(cardHolder holder, int position) {
        final Book book = books.get(position);
        holder.title.setText(book.getTitle());
        holder.price.setText("$ " + book.getPrice());
        holder.publisher.setText(book.getPublisher());
        holder.category.setText(book.getCategory());
        //holder.thumbnail.setImageResource(book.getImg());
        holder.all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bookInfo = new Intent(itemView.getContext(), BookInfo.class);
                bookInfo.putExtra("book", book);
                bookInfo.putExtra("pri", priotity);
                bookInfo.putExtra("cart", ca);
                itemView.getContext().startActivity(bookInfo);
            }
        });
    }

    @Override
    public int getItemCount() {
        return books.size();
    }
}

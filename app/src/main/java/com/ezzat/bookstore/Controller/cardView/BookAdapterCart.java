package com.ezzat.bookstore.Controller.cardView;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ezzat.bookstore.Model.Book;
import com.ezzat.bookstore.Model.Cart;
import com.ezzat.bookstore.R;

public class BookAdapterCart extends RecyclerView.Adapter<cartHolder> {

    View itemView;
    int priotity;
    Cart ca;

    public BookAdapterCart(int priotity, Cart ca) {
        this.priotity = priotity;
        this.ca = ca;
    }

    @Override
    public cartHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_cart, parent, false);
        return new cartHolder(itemView);
    }

    @Override
    public void onBindViewHolder(cartHolder holder, final int position) {
        final Book book = ca.books.get(position);
        holder.title.setText(book.getTitle());
        holder.price.setText("$ " + book.getPrice());
        holder.publisher.setText(book.getPublisher());
        holder.category.setText(book.getCategory());
        holder.quantity.setText(ca.quan.get(position));
        //holder.thumbnail.setImageResource(book.getImg());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ca.index = position;
                ca.removeBook();
                notifyDataSetChanged();
            }
        });
        holder.total.setText(Integer.parseInt(ca.quan.get(position)) * book.getPrice() + "");
    }

    @Override
    public int getItemCount() {
        return ca.books.size();
    }

}

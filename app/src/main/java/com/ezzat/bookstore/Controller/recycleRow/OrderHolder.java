package com.ezzat.bookstore.Controller.recycleRow;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ezzat.bookstore.R;

public class OrderHolder extends RecyclerView.ViewHolder {

    TextView isbn;
    TextView num;
    Button acc;

    public OrderHolder(View itemView) {
        super(itemView);
        isbn = itemView.findViewById(R.id.is);
        num = itemView.findViewById(R.id.qua);
        acc = itemView.findViewById(R.id.acc);
    }
}

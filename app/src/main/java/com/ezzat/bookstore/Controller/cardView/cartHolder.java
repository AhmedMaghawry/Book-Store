package com.ezzat.bookstore.Controller.cardView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ezzat.bookstore.R;

/**
 * Created by default on 13/03/18.
 */

public class cartHolder extends RecyclerView.ViewHolder {
    public TextView title, publisher, category, price, quantity;
    LinearLayout all;
    Button delete;
    public cartHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.title);
        publisher = (TextView) itemView.findViewById(R.id.pub);
        category = (TextView) itemView.findViewById(R.id.cat);
        price = (TextView) itemView.findViewById(R.id.price);
        all = itemView.findViewById(R.id.all);
        delete = itemView.findViewById(R.id.delete);
        quantity = itemView.findViewById(R.id.qu);
    }
}

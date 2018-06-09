package com.ezzat.bookstore.Controller.recycleRow;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ezzat.bookstore.R;

public class UserHolder extends RecyclerView.ViewHolder {

    TextView name;
    Button promote;

    public UserHolder(View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.uName);
        promote = itemView.findViewById(R.id.promote);
    }
}

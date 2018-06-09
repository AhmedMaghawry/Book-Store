package com.ezzat.bookstore.Controller.recycleRow;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ezzat.bookstore.Controller.HttpJsonParser;
import com.ezzat.bookstore.Model.Order;
import com.ezzat.bookstore.Model.User;
import com.ezzat.bookstore.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderAdapter extends RecyclerView.Adapter<OrderHolder> {

    ArrayList<Order> orders;
    ViewGroup parent;

    public OrderAdapter(ArrayList<Order> orders) {
        this.orders = orders;
    }

    @NonNull
    @Override
    public OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_order, parent, false);
        this.parent = parent;
        return new OrderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHolder holder, final int position) {
        holder.isbn.setText(orders.get(position).getIsbn());
        holder.num.setText(orders.get(position).getNum());
        holder.acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AcceptOrder().execute(new String[]{orders.get(position).getIsbn()});
                orders.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }


    public class AcceptOrder extends AsyncTask<String, Void, Void> {

        HttpJsonParser jParser = new HttpJsonParser();
        private boolean finished = true;

        /**
         * getting All products from url
         * */
        protected Void doInBackground(String... args) {
            // Building Parameters
            Map<String, String> params = new HashMap<>();
            params.put("isbn", args[0]);
            JSONObject json = jParser.makeHttpRequest("http://10.42.0.1:8085/Android_DB_connect/confirmOrder.php", "GET", params);
            try {
                int success = json.getInt("success");
                if (success == 0) {
                    finished = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}

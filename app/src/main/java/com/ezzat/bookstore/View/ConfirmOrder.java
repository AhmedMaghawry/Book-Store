package com.ezzat.bookstore.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.ezzat.bookstore.Controller.HttpJsonParser;
import com.ezzat.bookstore.Controller.recycleRow.OrderAdapter;
import com.ezzat.bookstore.Controller.recycleRow.UserAdapter;
import com.ezzat.bookstore.Model.Order;
import com.ezzat.bookstore.Model.User;
import com.ezzat.bookstore.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ConfirmOrder extends AppCompatActivity {
    Toolbar toolbar;
    int priority;
    JSONArray booksArr = null;
    ArrayList<Order> orders = new ArrayList<>();
    private ProgressDialog pDialog;
    private ImageView logout, profile, promote, back, confirmOrder, statistics;
    RecyclerView recyclerView;
    private static String url_all_products = "http://10.42.0.1:8085/Android_DB_connect/getOrders.php";
    HttpJsonParser jParser = new HttpJsonParser();
    private ConfirmOrder self;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promote);
        priority = getIntent().getIntExtra("pri",0);
        setup_toolbar();
        self = this;
        new LoadOrders().execute();
    }

    public void setup_toolbar() {
        setSupportActionBar(toolbar);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        logout = toolbar.findViewById(R.id.logout);
        profile = toolbar.findViewById(R.id.profile);
        promote = toolbar.findViewById(R.id.promote);
        back = toolbar.findViewById(R.id.back);
        confirmOrder = toolbar.findViewById(R.id.confirmOrders);
        statistics = toolbar.findViewById(R.id.statistics);
        if (priority == 0) {
            promote.setVisibility(View.GONE);
            confirmOrder.setVisibility(View.GONE);
            statistics.setVisibility(View.GONE);
        } else {
            promote.setVisibility(View.VISIBLE);
            confirmOrder.setVisibility(View.VISIBLE);
            statistics.setVisibility(View.VISIBLE);
        }
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfirmOrder.this, WelcomeActivity.class);
                startActivity(intent);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfirmOrder.this, Profile.class);
                intent.putExtra("cart", getIntent().getSerializableExtra("cart"));
                intent.putExtra("user", getIntent().getSerializableExtra("user"));
                intent.putExtra("pri", getIntent().getIntExtra("pri", 0));
                startActivity(intent);
            }
        });

        promote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfirmOrder.this, Promote.class);
                intent.putExtra("cart", getIntent().getSerializableExtra("cart"));
                intent.putExtra("user", getIntent().getSerializableExtra("user"));
                intent.putExtra("pri", getIntent().getIntExtra("pri", 0));
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfirmOrder.this, HomeActivity.class);
                intent.putExtra("cart", getIntent().getSerializableExtra("cart"));
                intent.putExtra("user", getIntent().getSerializableExtra("user"));
                intent.putExtra("pri", getIntent().getIntExtra("pri", 0));
                startActivity(intent);
            }
        });

        confirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfirmOrder.this, ConfirmOrder.class);
                intent.putExtra("cart", getIntent().getSerializableExtra("cart"));
                intent.putExtra("user", getIntent().getSerializableExtra("user"));
                intent.putExtra("pri", getIntent().getIntExtra("pri", 0));
                startActivity(intent);
            }
        });

        statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfirmOrder.this, Statistics.class);
                intent.putExtra("cart", getIntent().getSerializableExtra("cart"));
                intent.putExtra("user", getIntent().getSerializableExtra("user"));
                intent.putExtra("pri", getIntent().getIntExtra("pri", 0));
                startActivity(intent);
            }
        });
    }

    class LoadOrders extends AsyncTask<String, String, String> {

        AlertDialog alertDialog;
        boolean finished = true;
        String msg;

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            alertDialog = new AlertDialog.Builder(ConfirmOrder.this).create();
            alertDialog.setTitle("Error");
            alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
            pDialog = new ProgressDialog(ConfirmOrder.this);
            pDialog.setMessage("Loading Orders. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting All products from url
         * */
        protected String doInBackground(String... args) {
            // Building Parameters
            Map<String, String> params = new HashMap<>();
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_all_products, "GET", params);

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt("success");

                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    booksArr = json.getJSONArray("orders");

                    // looping through All Products
                    for (int i = 0; i < booksArr.length(); i++) {
                        JSONObject c = booksArr.getJSONObject(i);
                        String isbn = c.getString("isbn");
                        String num = c.getString("num");
                        orders.add(new Order(isbn, num));
                    }
                    finished = true;
                } else  {
                    finished = false;
                    msg = json.getString("msg");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products

            pDialog.dismiss();
            if (!finished) {
                alertDialog.setMessage(msg);
                alertDialog.show();
            }
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    recyclerView = findViewById(R.id.rv);
                    Log.i("dodo", orders.size()+"");
                    OrderAdapter mAdapterUser = new OrderAdapter(orders);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(self);
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(mAdapterUser);
                }
            });
        }
    }
}

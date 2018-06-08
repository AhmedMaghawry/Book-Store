package com.ezzat.bookstore.View;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ezzat.bookstore.Controller.HttpJsonParser;
import com.ezzat.bookstore.Controller.cardView.BookAdapterCard;
import com.ezzat.bookstore.Controller.cardView.BookAdapterCart;
import com.ezzat.bookstore.Model.Book;
import com.ezzat.bookstore.Model.Cart;
import com.ezzat.bookstore.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Cart_Activity extends AppCompatActivity {

    Toolbar toolbar;
    private RecyclerView recyclerView;
    private BookAdapterCart mAdapterCard;
    private ImageView logout, profile, promote, back, makeOrder, confirmOrder, statistics;
    private Button confirm;
    private TextView cost;
    Cart cart;
    int start = 0;
    int end = 0;

    private ProgressDialog pDialog;
    private Cart_Activity self;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        confirm = findViewById(R.id.con);
        cost = findViewById(R.id.cost);
        self = this;
        cart = (Cart) getIntent().getSerializableExtra("cart");
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showForms();
            }
        });
        setup_toolbar();
        recyclerView = findViewById(R.id.rv);
        mAdapterCard = new BookAdapterCart(0, cart);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapterCard);
        cost.setText(getTotalCost(cart)+"");
    }

    private void showForms() {
        LayoutInflater li = LayoutInflater.from(self);
        View promptsView = li.inflate(R.layout.dialog_layout, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText cardNum = (EditText) promptsView
                .findViewById(R.id.credit);
        final EditText exp = (EditText) promptsView
                .findViewById(R.id.date);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                end = cart.books.size();
                                startExe();
                                for (int i = 0; i < cart.books.size(); i++) {
                                    new confirmOrders().execute(new String[]{cart.books.get(i).getISBN() + "", cart.quan.get(i)});
                                    start++;
                                }
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    private int getTotalCost(Cart cart) {
        int total = 0;
        for (int i =0; i < cart.books.size(); i++) {
            total += cart.books.get(i).getPrice() * Integer.parseInt(cart.quan.get(i));
        }
        return total;
    }


    public void setup_toolbar() {
        setSupportActionBar(toolbar);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        logout = toolbar.findViewById(R.id.logout);
        profile = toolbar.findViewById(R.id.profile);
        promote = toolbar.findViewById(R.id.promote);
        back = toolbar.findViewById(R.id.back);
        makeOrder = toolbar.findViewById(R.id.placeOrders);
        confirmOrder = toolbar.findViewById(R.id.confirmOrders);
        statistics = toolbar.findViewById(R.id.statistics);
            promote.setVisibility(View.GONE);
            makeOrder.setVisibility(View.GONE);
            confirmOrder.setVisibility(View.GONE);
            statistics.setVisibility(View.GONE);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Cart_Activity.this, WelcomeActivity.class);
                startActivity(intent);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Cart_Activity.this, WelcomeActivity.class);
                intent.putExtra("cart", cart);
                startActivity(intent);
            }
        });

        promote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Cart_Activity.this, WelcomeActivity.class);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Cart_Activity.this, HomeActivity.class);
                intent.putExtra("pri", getIntent().getIntExtra("pri", 0));
                startActivity(intent);
            }
        });

        makeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Cart_Activity.this, WelcomeActivity.class);
                startActivity(intent);
            }
        });

        confirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Cart_Activity.this, WelcomeActivity.class);
                startActivity(intent);
            }
        });

        statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Cart_Activity.this, WelcomeActivity.class);
                startActivity(intent);
            }
        });
    }


    public class confirmOrders extends AsyncTask<String, Void, Void> {

        HttpJsonParser jParser = new HttpJsonParser();
        /**
         * getting All products from url
         * */
        protected Void doInBackground(String... args) {
            // Building Parameters
            Map<String, String> params = new HashMap<>();
            params.put("isbn", args[0]);
            params.put("quantity", args[1]);
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest("http://10.42.0.1:8085/Android_DB_connect/confirmCart.php", "GET", params);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (start == end) {
                pDialog.dismiss();
                Intent intent = new Intent(Cart_Activity.this, HomeActivity.class);
                intent.putExtra("pri", getIntent().getIntExtra("pri", 0));
                startActivity(intent);
            } /*else {
                execute(new String[]{cart.books.get(start).getISBN()+"", cart.quan.get(start)});
                start++;
            }*/
        }
    }


    public void startExe() {
        pDialog = new ProgressDialog(Cart_Activity.this);
        pDialog.setMessage("Confirming. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }
}

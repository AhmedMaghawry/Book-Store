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
import android.widget.Toast;

import com.ezzat.bookstore.Controller.HttpJsonParser;
import com.ezzat.bookstore.Controller.cardView.BookAdapterCard;
import com.ezzat.bookstore.Controller.cardView.BookAdapterCart;
import com.ezzat.bookstore.Model.Book;
import com.ezzat.bookstore.Model.Cart;
import com.ezzat.bookstore.Model.User;
import com.ezzat.bookstore.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Cart_Activity extends AppCompatActivity {

    Toolbar toolbar;
    private RecyclerView recyclerView;
    private BookAdapterCart mAdapterCard;
    private ImageView logout, profile, promote, back, confirmOrder, statistics;
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
        cardNum.setLines(1);
        final EditText exp = (EditText) promptsView
                .findViewById(R.id.date);
        exp.setLines(1);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                end = cart.books.size();
                                startExe();
                                for (int i = 0; i < cart.books.size(); i++) {
                                    new confirmOrders().execute(new String[]{cart.books.get(i).getISBN() + "", cart.quan.get(i), i+""});
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
        confirmOrder = toolbar.findViewById(R.id.confirmOrders);
        statistics = toolbar.findViewById(R.id.statistics);
            promote.setVisibility(View.GONE);
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
                Intent intent = new Intent(Cart_Activity.this, Profile.class);
                intent.putExtra("cart", cart);
                intent.putExtra("user", getIntent().getSerializableExtra("user"));
                intent.putExtra("pri", getIntent().getIntExtra("pri", 0));
                startActivity(intent);
            }
        });

        promote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Cart_Activity.this, Promote.class);
                intent.putExtra("cart", getIntent().getSerializableExtra("cart"));
                intent.putExtra("user", getIntent().getSerializableExtra("user"));
                intent.putExtra("pri", getIntent().getIntExtra("pri", 0));
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Cart_Activity.this, HomeActivity.class);
                intent.putExtra("cart", getIntent().getSerializableExtra("cart"));
                intent.putExtra("user", getIntent().getSerializableExtra("user"));
                intent.putExtra("pri", getIntent().getIntExtra("pri", 0));
                startActivity(intent);
            }
        });

        confirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Cart_Activity.this, ConfirmOrder.class);
                intent.putExtra("cart", getIntent().getSerializableExtra("cart"));
                intent.putExtra("user", getIntent().getSerializableExtra("user"));
                intent.putExtra("pri", getIntent().getIntExtra("pri", 0));
                startActivity(intent);
            }
        });

        statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Cart_Activity.this, Statistics.class);
                intent.putExtra("cart", getIntent().getSerializableExtra("cart"));
                intent.putExtra("user", getIntent().getSerializableExtra("user"));
                intent.putExtra("pri", getIntent().getIntExtra("pri", 0));
                startActivity(intent);
            }
        });
    }


    public class confirmOrders extends AsyncTask<String, Void, Void> {

        HttpJsonParser jParser = new HttpJsonParser();
        AlertDialog alertDialog;
        boolean finished = true;
        String msg;
        int index;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            alertDialog = new AlertDialog.Builder(Cart_Activity.this).create();
            alertDialog.setTitle("Error");
            alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
        }

        /**
         * getting All products from url
         * */
        protected Void doInBackground(String... args) {
            // Building Parameters
            Map<String, String> params = new HashMap<>();
            params.put("isbn", args[0]);
            params.put("quantity", args[1]);
            index = Integer.parseInt(args[2]);
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest("http://10.42.0.1:8085/Android_DB_connect/confirmCart.php", "GET", params);
            try {
                int success = json.getInt("success");
                if (success == 0) {
                    finished = false;
                } else {
                    finished = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (start == end && finished) {
                User u = (User) getIntent().getSerializableExtra("user");
                new addStat().execute(new String[]{cart.books.get(index).getISBN()+"", u.getUserName(), (cart.books.get(index).getPrice() * Integer.parseInt(cart.quan.get(index))) + "", "2018-06-12"});
                pDialog.dismiss();
                Intent intent = new Intent(Cart_Activity.this, HomeActivity.class);
                intent.putExtra("user", u);
                intent.putExtra("pri", getIntent().getIntExtra("pri", 0));
                startActivity(intent);
            }

            if (!finished) {
                alertDialog.setMessage(msg);
                alertDialog.show();
            }
        }
    }


    public void startExe() {
        pDialog = new ProgressDialog(Cart_Activity.this);
        pDialog.setMessage("Confirming. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    public class addStat extends AsyncTask<String, Void, Void> {

        HttpJsonParser jParser = new HttpJsonParser();
        AlertDialog alertDialog;
        boolean finished = true;
        String msg;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            alertDialog = new AlertDialog.Builder(Cart_Activity.this).create();
            alertDialog.setTitle("Error");
            alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
        }

        /**
         * getting All products from url
         * */
        protected Void doInBackground(String... args) {
            Map<String, String> params = new HashMap<>();
            params.put("isbn", args[0]);
            params.put("username", args[1]);
            params.put("total", args[2]);
            params.put("date", args[3]);
            JSONObject json = jParser.makeHttpRequest("http://10.42.0.1:8085/Android_DB_connect/addStatistics.php", "GET", params);
            try {
                int success = json.getInt("success");
                if (success == 0) {
                    finished = false;
                } else {
                    finished = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (!finished) {
                alertDialog.setMessage(msg);
                alertDialog.show();
            }
        }
    }
}

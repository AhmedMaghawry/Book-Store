package com.ezzat.bookstore.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.ezzat.bookstore.Controller.HttpJsonParser;
import com.ezzat.bookstore.Controller.cardView.BookAdapterCard;
import com.ezzat.bookstore.Model.Book;
import com.ezzat.bookstore.Model.Cart;
import com.ezzat.bookstore.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BookAdapterCard mAdapterCard;
    // Progress Dialog
    private ProgressDialog pDialog;
    List<Book> temp;
    int limit = 0;
    // Creating JSON Parser object
    HttpJsonParser jParser = new HttpJsonParser();

    private ImageView logout, profile, promote, back, makeOrder, confirmOrder, statistics;
    private FloatingActionButton add, cart;

    Button backy, next;

    // url to get all products list
    private static String url_all_products = "http://10.42.0.1:8085/Android_DB_connect/getBooks.php";

    private Spinner spinner;
    private static final String[]paths = {"ISBN", "TITLE", "Category", "Author", "Publisher"};

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_BOOKS = "books";
    private static final String ISBN = "isbn";
    private static final String title = "title";
    private static final String publisher = "pub";
    private static final String author = "auth";
    private static final String pub_year = "year";
    private static final String price = "price";
    private static final String category = "cat";
    private static final String no_copies = "num";
    private static final String min_quantity = "min";

    // products JSONArray
    JSONArray booksArr = null;
    HomeActivity con;
    Toolbar toolbar;
    int priority;
    Cart carty;

    private List<Book> books = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        final Intent in = getIntent();
        priority = in.getIntExtra("pri",0);
        carty = (Cart) in.getSerializableExtra("cart");
        if (carty == null) {
            carty = new Cart();
        }
        con = this;
        setup_toolbar();
        add = findViewById(R.id.add);
        cart = findViewById(R.id.cart);
        if (priority == 0) {
            add.setVisibility(View.GONE);
            cart.setVisibility(View.VISIBLE);
        }else {
            cart.setVisibility(View.GONE);
            add.setVisibility(View.VISIBLE);
        }
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, WelcomeActivity.class);
                startActivity(intent);
            }
        });
        backy = findViewById(R.id.backIt);
        next = findViewById(R.id.next);
        final Cart finalCarty = carty;
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, Cart_Activity.class);
                intent.putExtra("cart", finalCarty);
                startActivity(intent);
            }
        });

        spinner = (Spinner)findViewById(R.id.menu);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(HomeActivity.this,
                R.layout.spinner_layout,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Loading products in Background Thread
        new LoadAllProducts().execute(limit+"");

        backy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limit -= 100;
                if (limit < 0) {
                    limit = 0;
                } else {
                    temp = new ArrayList<>(books);
                    books.clear();
                    new LoadAllProducts().execute(limit+"");
                    mAdapterCard.notifyDataSetChanged();
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    limit += 100;
                    temp = new ArrayList<>(books);
                    books.clear();
                    new LoadAllProducts().execute(limit+"");
                    mAdapterCard.notifyDataSetChanged();
            }
        });

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
        if (priority == 0) {
            promote.setVisibility(View.GONE);
            makeOrder.setVisibility(View.GONE);
            confirmOrder.setVisibility(View.GONE);
            statistics.setVisibility(View.GONE);
        } else {
            promote.setVisibility(View.VISIBLE);
            makeOrder.setVisibility(View.VISIBLE);
            confirmOrder.setVisibility(View.VISIBLE);
            statistics.setVisibility(View.VISIBLE);
        }
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, WelcomeActivity.class);
                startActivity(intent);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, WelcomeActivity.class);
                intent.putExtra("cart", carty);
                startActivity(intent);
            }
        });

        promote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, WelcomeActivity.class);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, WelcomeActivity.class);
                startActivity(intent);
            }
        });

        makeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, WelcomeActivity.class);
                startActivity(intent);
            }
        });

        confirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, WelcomeActivity.class);
                startActivity(intent);
            }
        });

        statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, WelcomeActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Background Async Task to Load all books by making HTTP Request
     * */
    class LoadAllProducts extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(HomeActivity.this);
            pDialog.setMessage("Loading Books. Please wait...");
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
            params.put("num", args[0]);
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_all_products, "GET", params);

            // Check your log cat for JSON reponse
            Log.d("All Books: ", json.toString());
            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    booksArr = json.getJSONArray(TAG_BOOKS);

                    // looping through All Products
                    for (int i = 0; i < booksArr.length(); i++) {
                        JSONObject c = booksArr.getJSONObject(i);
                        int isb = c.getInt(ISBN);
                        String tit = c.getString(title);
                        String pub = c.getString(publisher);
                        JSONArray auths = c.getJSONArray(author);
                        String year = c.getString(pub_year);
                        int pri = c.getInt(price);
                        String cate = c.getString(category);
                        int num = c.getInt(no_copies);
                        int mini = c.getInt(min_quantity);
                        String[] au = new String[auths.length()];
                        for (int j = 0; j < auths.length(); j++) {
                            au[j] = (String) auths.get(j);
                        }
                        for (int j = 0; j < au.length; j++) {
                            Log.i("dodo", au[j]);
                        }
                        books.add(new Book(isb, tit, pub, au, year, pri, cate, num, mini));
                    }
                } else  {
                    books = temp;
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
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    if (books.size() != 0) {
                        recyclerView = findViewById(R.id.rv);
                        mAdapterCard = new BookAdapterCard(books, priority, carty);
                        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(con, 2);
                        recyclerView.setLayoutManager(mLayoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(mAdapterCard);
                    } else {
                        limit -= 100;
                    }
                }
            });
        }
    }
}

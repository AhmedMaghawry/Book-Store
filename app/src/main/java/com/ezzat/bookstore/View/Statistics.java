package com.ezzat.bookstore.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ezzat.bookstore.Controller.HttpJsonParser;
import com.ezzat.bookstore.Controller.cardView.BookAdapterCard;
import com.ezzat.bookstore.Controller.recycleRow.UserAdapter;
import com.ezzat.bookstore.Model.Book;
import com.ezzat.bookstore.Model.Cart;
import com.ezzat.bookstore.Model.User;
import com.ezzat.bookstore.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Statistics extends AppCompatActivity {

    Toolbar toolbar;
    int priority;
    JSONArray booksArr = null;
    JSONArray usersArr = null;
    ArrayList<User> users = new ArrayList<>();
    ArrayList<Book> books = new ArrayList<>();
    String totaltv;
    RecyclerView rvCustomer, rvBook;
    HttpJsonParser jParser = new HttpJsonParser();
    TextView totalVal;
    private ImageView logout, profile, promote, back, confirmOrder, statistics;
    final String url_total = "http://10.42.0.1:8085/Android_DB_connect/getStatisticsTotal.php";
    final String url_Customers = "http://10.42.0.1:8085/Android_DB_connect/getStatisticsUsers.php";
    final String url_Books = "http://10.42.0.1:8085/Android_DB_connect/getStatisticsBooks.php";
    private Statistics self;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        rvCustomer = findViewById(R.id.rvC);
        rvBook = findViewById(R.id.rvB);
        totalVal = findViewById(R.id.total);
        priority = getIntent().getIntExtra("pri",0);
        self = this;
        setup_toolbar();

        new LoadTotal().execute();
        new LoadCustomers().execute();
        new LoadBooks().execute();
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
                Intent intent = new Intent(Statistics.this, WelcomeActivity.class);
                startActivity(intent);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Statistics.this, Profile.class);
                intent.putExtra("cart", getIntent().getSerializableExtra("cart"));
                intent.putExtra("user", getIntent().getSerializableExtra("user"));
                intent.putExtra("pri", getIntent().getIntExtra("pri", 0));
                startActivity(intent);
            }
        });

        promote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Statistics.this, Promote.class);
                intent.putExtra("cart", getIntent().getSerializableExtra("cart"));
                intent.putExtra("user", getIntent().getSerializableExtra("user"));
                intent.putExtra("pri", getIntent().getIntExtra("pri", 0));
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Statistics.this, HomeActivity.class);
                intent.putExtra("cart", getIntent().getSerializableExtra("cart"));
                intent.putExtra("user", getIntent().getSerializableExtra("user"));
                intent.putExtra("pri", getIntent().getIntExtra("pri", 0));
                startActivity(intent);
            }
        });

        confirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Statistics.this, ConfirmOrder.class);
                intent.putExtra("cart", getIntent().getSerializableExtra("cart"));
                intent.putExtra("user", getIntent().getSerializableExtra("user"));
                intent.putExtra("pri", getIntent().getIntExtra("pri", 0));
                startActivity(intent);
            }
        });

        statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Statistics.this, Statistics.class);
                intent.putExtra("cart", getIntent().getSerializableExtra("cart"));
                intent.putExtra("user", getIntent().getSerializableExtra("user"));
                intent.putExtra("pri", getIntent().getIntExtra("pri", 0));
                startActivity(intent);
            }
        });
    }


    class LoadCustomers extends AsyncTask<String, String, String> {

        /**
         * getting All products from url
         * */
        protected String doInBackground(String... args) {
            // Building Parameters
            Map<String, String> params = new HashMap<>();
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_Customers, "GET", params);

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt("success");

                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    usersArr = json.getJSONArray("users");

                    // looping through All Products
                    for (int i = 0; i < usersArr.length(); i++) {
                        JSONObject c = usersArr.getJSONObject(i);
                        String username = c.getString("username");
                        String password = c.getString("password");
                        String fi = c.getString("fi");
                        String la = c.getString("la");
                        String em = c.getString("em");
                        String ph = c.getString("ph");
                        String shi = c.getString("shi");
                        users.add(new User(username, fi, la, password, em, ph, shi));
                    }
                } else  {
                    //books = temp;
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
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    UserAdapter mAdapterUser = new UserAdapter(users, true);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(self);
                    rvCustomer.setLayoutManager(mLayoutManager);
                    rvCustomer.setItemAnimator(new DefaultItemAnimator());
                    rvCustomer.setAdapter(mAdapterUser);
                }
            });
        }
    }

    class LoadBooks extends AsyncTask<String, String, String> {

        /**
         * getting All products from url
         * */
        protected String doInBackground(String... args) {
            // Building Parameters
            Map<String, String> params = new HashMap<>();
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_Books, "GET", params);

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
                        String year = c.getString(pub_year);
                        int pri = c.getInt(price);
                        String cate = c.getString(category);
                        int num = c.getInt(no_copies);
                        int mini = c.getInt(min_quantity);
                        String[] au = new String[]{};
                        books.add(new Book(isb, tit, pub, au, year, pri, cate, num, mini));
                    }
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
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    if (books.size() != 0) {
                        BookAdapterCard mAdapterCard = new BookAdapterCard(books, priority, (Cart) getIntent().getSerializableExtra("cart"), (User)getIntent().getSerializableExtra("user"), true);
                        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(self, 2);
                        rvBook.setLayoutManager(mLayoutManager);
                        rvBook.setItemAnimator(new DefaultItemAnimator());
                        rvBook.setAdapter(mAdapterCard);
                    }
                }
            });
        }
    }

    class LoadTotal extends AsyncTask<String, String, String> {

        /**
         * getting All products from url
         * */
        protected String doInBackground(String... args) {
            // Building Parameters
            Map<String, String> params = new HashMap<>();
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_total, "GET", params);

            try {
                    // products found
                    totaltv = json.getString("total");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    totalVal.setText(totaltv);
                }
            });
        }
    }

}

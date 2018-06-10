package com.ezzat.bookstore.View;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ezzat.bookstore.Controller.HttpJsonParser;
import com.ezzat.bookstore.Model.User;
import com.ezzat.bookstore.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddBook extends AppCompatActivity {

    Toolbar toolbar;
    int priority;
    private ImageView logout, profile, promote, back, confirmOrder, statistics;
    private Button add;
    private EditText isbn, title, publisher, year, price, num, min;
    private Spinner cate;
    private User user;
    private AddBook self;
    private static final String[]paths = {"Science", "Art", "Religion", "History", "Geography"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        self = this;
        user = (User) getIntent().getSerializableExtra("user");
        priority = getIntent().getIntExtra("pri",0);
        setup_toolbar();
        setup_views();
        setup_buttons();
    }

    private void setup_buttons() {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] params = new String[] {
                  isbn.getText().toString(),
                  title.getText().toString(),
                  publisher.getText().toString(),
                  year.getText().toString(),
                  price.getText().toString(),
                  cate.getSelectedItem().toString(),
                  num.getText().toString(),
                  min.getText().toString()
                };
                new addBooke().execute(params);
            }
        });
    }

    private void setup_views() {
        add = findViewById(R.id.add);
        isbn = findViewById(R.id.isbn);
        isbn.setLines(1);
        title = findViewById(R.id.title);
        title.setLines(1);
        publisher = findViewById(R.id.pub);
        publisher.setLines(1);
        year = findViewById(R.id.year);
        year.setLines(1);
        price = findViewById(R.id.price);
        price.setLines(1);
        cate = findViewById(R.id.cat);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddBook.this,
                R.layout.spinner_layout,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cate.setAdapter(adapter);
        cate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        num = findViewById(R.id.cop);
        num.setLines(1);
        min = findViewById(R.id.min);
        min.setLines(1);
    }

    public void setup_toolbar() {
        setSupportActionBar(toolbar);
        toolbar = findViewById(R.id.tool_bar);
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
                Intent intent = new Intent(AddBook.this, WelcomeActivity.class);
                startActivity(intent);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddBook.this, Profile.class);
                intent.putExtra("cart", getIntent().getSerializableExtra("cart"));
                intent.putExtra("user", getIntent().getSerializableExtra("user"));
                intent.putExtra("pri", getIntent().getIntExtra("pri", 0));
                startActivity(intent);
            }
        });

        promote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddBook.this, Promote.class);
                intent.putExtra("cart", getIntent().getSerializableExtra("cart"));
                intent.putExtra("user", getIntent().getSerializableExtra("user"));
                intent.putExtra("pri", getIntent().getIntExtra("pri", 0));
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddBook.this, HomeActivity.class);
                intent.putExtra("cart", getIntent().getSerializableExtra("cart"));
                intent.putExtra("user", getIntent().getSerializableExtra("user"));
                intent.putExtra("pri", getIntent().getIntExtra("pri", 0));
                startActivity(intent);
            }
        });

        confirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddBook.this, ConfirmOrder.class);
                intent.putExtra("cart", getIntent().getSerializableExtra("cart"));
                intent.putExtra("user", getIntent().getSerializableExtra("user"));
                intent.putExtra("pri", getIntent().getIntExtra("pri", 0));
                startActivity(intent);
            }
        });

        statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddBook.this, Statistics.class);
                intent.putExtra("cart", getIntent().getSerializableExtra("cart"));
                intent.putExtra("user", getIntent().getSerializableExtra("user"));
                intent.putExtra("pri", getIntent().getIntExtra("pri", 0));
                startActivity(intent);
            }
        });
    }

    public class addBooke extends AsyncTask<String, Void, Void> {

        HttpJsonParser jParser = new HttpJsonParser();
        private boolean finished = true;

        /**
         * getting All products from url
         * */
        protected Void doInBackground(String... args) {
            Map<String, String> params = new HashMap<>();
            params.put("isbn", args[0]);
            params.put("title", args[1]);
            params.put("pub", args[2]);
            params.put("year", args[3]);
            params.put("price", args[4]);
            params.put("cat", args[5]);
            params.put("num", args[6]);
            params.put("min", args[7]);
            JSONObject json = jParser.makeHttpRequest("http://10.42.0.1:8085/Android_DB_connect/addBook.php", "GET", params);
            try {
                int success = json.getInt("success");
                if (success == 0) {
                    finished = false;
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(self, "Error Happened", Toast.LENGTH_SHORT);
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (finished) {
                Intent intent = new Intent(AddBook.this, HomeActivity.class);
                intent.putExtra("cart", getIntent().getSerializableExtra("cart"));
                intent.putExtra("user", user);
                intent.putExtra("pri", getIntent().getIntExtra("pri", 0));
                startActivity(intent);
            }
        }
    }
}

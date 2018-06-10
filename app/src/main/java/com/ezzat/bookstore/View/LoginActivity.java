package com.ezzat.bookstore.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ezzat.bookstore.Controller.HttpJsonParser;
import com.ezzat.bookstore.Controller.cardView.BookAdapterCard;
import com.ezzat.bookstore.Model.Book;
import com.ezzat.bookstore.Model.User;
import com.ezzat.bookstore.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    Button login;
    User user;
    boolean finished = true;
    int priority;
    private ProgressDialog pDialog;
    JSONArray userArr = null;
    HttpJsonParser jParser = new HttpJsonParser();
    EditText userNameEd, passwordEd;
    private LoginActivity self;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        self = this;
        userNameEd = findViewById(R.id.username);
        userNameEd.setLines(1);
        passwordEd = findViewById(R.id.password);
        passwordEd.setLines(1);
        priority = getIntent().getIntExtra("pri", 0);
        login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LoginActivity.LoginIt().execute(new String[]{userNameEd.getText().toString(), passwordEd.getText().toString()});
            }
        });
    }

    /**
     * Background Async Task to Load all books by making HTTP Request
     * */
    class LoginIt extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("Login In. Please wait...");
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
            params.put("username", args[0]);
            params.put("password", args[1]);
            JSONObject json;
            if (priority == 0) {
                // getting JSON string from URL
                json = jParser.makeHttpRequest("http://10.42.0.1:8085/Android_DB_connect/loginUser.php", "GET", params);
            } else {
                // getting JSON string from URL
                json = jParser.makeHttpRequest("http://10.42.0.1:8085/Android_DB_connect/loginManager.php", "GET", params);
            }
            try {
                Log.i("dodo", json.toString());
                // Checking for SUCCESS TAG
                int success = json.getInt("success");

                if (success == 1) {
                    userArr = json.getJSONArray("user");
                        finished = true;
                        JSONObject c = userArr.getJSONObject(0);
                        String username = c.getString("username");
                        String password = c.getString("password");
                        String firstN = c.getString("firstN");
                        String lastN = c.getString("lastN");
                        String email = c.getString("email");
                        String phone = c.getString("phone");
                        String ship = c.getString("ship");
                        user = new User(username, firstN, lastN, password, email, phone, ship);
                } else  {
                    finished = false;
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
            if(finished) {
                // updating UI from Background Thread
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                intent.putExtra("user", user);
                intent.putExtra("pri", priority);
                startActivity(intent);
            } else  {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(self, "Error Happened", Toast.LENGTH_SHORT);
                        userNameEd.setText("");
                        passwordEd.setText("");
                    }
                });
            }
        }
    }
}

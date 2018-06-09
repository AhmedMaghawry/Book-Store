package com.ezzat.bookstore.View;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ezzat.bookstore.Controller.HttpJsonParser;
import com.ezzat.bookstore.Controller.cardView.BookAdapterCard;
import com.ezzat.bookstore.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    Button reg;
    EditText fi, la, em, pass, usr, ph, sh;
    private RegisterActivity self;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        self = this;
        reg = findViewById(R.id.reg);
        fi = findViewById(R.id.first);
        la = findViewById(R.id.last);
        em = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        usr = findViewById(R.id.user);
        ph = findViewById(R.id.phone);
        sh = findViewById(R.id.address);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new RegisterActivity.registerNow().execute(new String[]{usr.getText().toString(), pass.getText().toString(), fi.getText().toString(), la.getText().toString(), em.getText().toString(), ph.getText().toString(), sh.getText().toString()});
            }
        });
    }

    public class registerNow extends AsyncTask<String, Void, Void> {

        HttpJsonParser jParser = new HttpJsonParser();
        private boolean finished = true;

        /**
         * getting All products from url
         * */
        protected Void doInBackground(String... args) {
            // Building Parameters
            Map<String, String> params = new HashMap<>();
            params.put("username", args[0]);
            params.put("password", args[1]);
            params.put("fiName", args[2]);
            params.put("laName", args[3]);
            params.put("email", args[4]);
            params.put("phone", args[5]);
            params.put("shi", args[6]);
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest("http://10.42.0.1:8085/Android_DB_connect/adduser.php", "GET", params);
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
                Intent intent = new Intent(RegisterActivity.this, WelcomeActivity.class);
                startActivity(intent);
            }
        }
    }
}

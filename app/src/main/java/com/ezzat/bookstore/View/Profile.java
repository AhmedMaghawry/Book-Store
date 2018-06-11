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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ezzat.bookstore.Controller.HttpJsonParser;
import com.ezzat.bookstore.Model.User;
import com.ezzat.bookstore.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Profile extends AppCompatActivity {

    Toolbar toolbar;
    int priority;
    private ImageView logout, profile, promote, back, confirmOrder, statistics;
    private TextView userName, firstN, lastN, password, email, phone, ship;
    private Button editFirst, editLast, editPass, editEmail, editPhone, editShip,confirm, cancel;
    private User user;
    private Profile self;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        self = this;
        user = (User) getIntent().getSerializableExtra("user");
        priority = getIntent().getIntExtra("pri",0);
        setup_toolbar();
        setup_views();
        setup_buttons();
    }

    private void setup_buttons() {
        editPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boilr(phone);
            }
        });

        editShip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boilr(ship);
            }
        });

        editPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boilr(password);
            }
        });

        editLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boilr(lastN);
            }
        });

        editFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boilr(firstN);
            }
        });

        editEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boilr(email);
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Profile.updateUser().execute(new String[]{userName.getText().toString(), password.getText().toString()
                        , firstN.getText().toString(), lastN.getText().toString(), email.getText().toString(),
                        phone.getText().toString(), ship.getText().toString()});
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, HomeActivity.class);
                intent.putExtra("cart", getIntent().getSerializableExtra("cart"));
                intent.putExtra("user", getIntent().getSerializableExtra("user"));
                intent.putExtra("pri", getIntent().getIntExtra("pri", 0));
                startActivity(intent);
            }
        });
    }

    void boilr(final TextView te) {
        LayoutInflater li = LayoutInflater.from(self);
        View promptsView = li.inflate(R.layout.edit_dialog_layout, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                self);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText editText = (EditText) promptsView
                .findViewById(R.id.edit);
        editText.setLines(1);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                te.setText(editText.getText().toString());
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

    private void setup_views() {
        userName = findViewById(R.id.username);
        firstN = findViewById(R.id.fiN);
        lastN = findViewById(R.id.laN);
        password = findViewById(R.id.pass);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        ship = findViewById(R.id.shi);
        editPass = findViewById(R.id.editpass);
        editPhone = findViewById(R.id.editphone);
        editShip = findViewById(R.id.editshi);
        editLast = findViewById(R.id.editla);
        editFirst= findViewById(R.id.editfi);
        editEmail = findViewById(R.id.editemail);
        cancel = findViewById(R.id.cancel);
        confirm = findViewById(R.id.confirm);
        userName.setText(user.getUserName());
        firstN.setText(user.getFirstN()+"");
        lastN.setText(user.getLastN());
        password.setText(user.getPassword()+"");
        email.setText(user.getEmail());
        phone.setText(user.getPhone()+"");
        ship.setText(user.getShip());
            editEmail.setVisibility(View.VISIBLE);
            editFirst.setVisibility(View.VISIBLE);
            editLast.setVisibility(View.VISIBLE);
            editShip.setVisibility(View.VISIBLE);
            editPass.setVisibility(View.VISIBLE);
            editPhone.setVisibility(View.VISIBLE);
    }

    private boolean checkNum(String s) {
        try {
            Integer.parseInt(s);
        } catch (Exception e) {
            return false;
        }
        return true;
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
                Intent intent = new Intent(Profile.this, WelcomeActivity.class);
                startActivity(intent);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, Profile.class);
                intent.putExtra("cart", getIntent().getSerializableExtra("cart"));
                intent.putExtra("user", getIntent().getSerializableExtra("user"));
                intent.putExtra("pri", getIntent().getIntExtra("pri", 0));
                startActivity(intent);
            }
        });

        promote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, Promote.class);
                intent.putExtra("cart", getIntent().getSerializableExtra("cart"));
                intent.putExtra("user", getIntent().getSerializableExtra("user"));
                intent.putExtra("pri", getIntent().getIntExtra("pri", 0));
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, HomeActivity.class);
                intent.putExtra("cart", getIntent().getSerializableExtra("cart"));
                intent.putExtra("user", getIntent().getSerializableExtra("user"));
                intent.putExtra("pri", getIntent().getIntExtra("pri", 0));
                startActivity(intent);
            }
        });

        confirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, ConfirmOrder.class);
                intent.putExtra("cart", getIntent().getSerializableExtra("cart"));
                intent.putExtra("user", getIntent().getSerializableExtra("user"));
                intent.putExtra("pri", getIntent().getIntExtra("pri", 0));
                startActivity(intent);
            }
        });

        statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, Statistics.class);
                intent.putExtra("cart", getIntent().getSerializableExtra("cart"));
                intent.putExtra("user", getIntent().getSerializableExtra("user"));
                intent.putExtra("pri", getIntent().getIntExtra("pri", 0));
                startActivity(intent);
            }
        });
    }

    public class updateUser extends AsyncTask<String, Void, Void> {

        HttpJsonParser jParser = new HttpJsonParser();
        AlertDialog alertDialog;
        boolean finished = true;
        String msg;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            alertDialog = new AlertDialog.Builder(Profile.this).create();
            alertDialog.setTitle("Error");
            alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
        }

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
            JSONObject json;
            if (priority == 0) {
                // getting JSON string from URL
                json = jParser.makeHttpRequest("http://10.42.0.1:8085/Android_DB_connect/updateUser.php", "GET", params);
            } else {
                Log.i("newD", args[2]);
                // getting JSON string from URL
                json = jParser.makeHttpRequest("http://10.42.0.1:8085/Android_DB_connect/updateManager.php", "GET", params);
            }
            try {
                user = new User(args[0],args[2], args[3], args[1], args[4], args[5], args[6]);
                int success = json.getInt("success");
                if (success == 0) {
                    finished = false;
                    msg = json.getString("msg");
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
            if (finished) {
                Intent intent = new Intent(Profile.this, HomeActivity.class);
                intent.putExtra("cart", getIntent().getSerializableExtra("cart"));
                intent.putExtra("user", user);
                intent.putExtra("pri", getIntent().getIntExtra("pri", 0));
                startActivity(intent);
            } else {
                alertDialog.setMessage(msg);
                alertDialog.show();
            }
        }
    }
}

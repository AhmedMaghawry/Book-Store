package com.ezzat.bookstore.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ezzat.bookstore.R;

public class LoginActivity extends AppCompatActivity {

    Button skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        skip = (Button) findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = getIntent();
                int priority = in.getIntExtra("pri",0);
                Intent go = new Intent(LoginActivity.this, HomeActivity.class);
                go.putExtra("pri", priority);
                startActivity(go);
            }
        });
    }
}

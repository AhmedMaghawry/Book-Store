package com.ezzat.bookstore.View;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ezzat.bookstore.Model.Book;
import com.ezzat.bookstore.Model.Cart;
import com.ezzat.bookstore.R;

public class BookInfo extends AppCompatActivity {

    Toolbar toolbar;
    int priority;
    private ImageView logout, profile, promote, back;
    private TextView title, isbn, publiser, year, cat, price, author;
    private EditText quan;
    private Button order;
    private Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);
        book = (Book) getIntent().getSerializableExtra("book");
        Log.i("dodo", book.getISBN()+"");
        priority = getIntent().getIntExtra("pri",0);
        Log.i("dodo", priority+"");
        setup_toolbar();
        setup_views();
    }

    private void setup_views() {
        title = findViewById(R.id.title);
        isbn = findViewById(R.id.isbn);
        publiser = findViewById(R.id.pub);
        year = findViewById(R.id.year);
        cat = findViewById(R.id.cat);
        price = findViewById(R.id.price);
        author = findViewById(R.id.auth);
        title.setText(book.getTitle());
        isbn.setText(book.getISBN()+"");
        publiser.setText(book.getPublisher());
        year.setText(book.getPub_year()+"");
        cat.setText(book.getCategory());
        price.setText(book.getPrice()+"");
        String str = "";
        for (String x : book.getAuthor()) {
            str = str + x + " , ";
        }
        author.setText(str);
        quan = findViewById(R.id.count);
        order = findViewById(R.id.order);
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkNum(quan.getText().toString())) {
                    Intent go = new Intent(BookInfo.this, HomeActivity.class);
                    go.putExtra("pri", priority);
                    Cart cart = (Cart) getIntent().getSerializableExtra("cart");
                    cart.books.add(book);
                    cart.quan.add(quan.getText().toString());
                    go.putExtra("cart", cart);
                    startActivity(go);
                } else {
                    new AlertDialog.Builder(BookInfo.this)
                            .setTitle("Error Num")
                            .setMessage("You entered a wrong quantity")
                            .setCancelable(false)
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Whatever...
                                }
                            }).show();
                }
            }
        });
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
        if (priority == 0)
            promote.setVisibility(View.GONE);
        else
            promote.setVisibility(View.VISIBLE);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookInfo.this, WelcomeActivity.class);
                startActivity(intent);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookInfo.this, WelcomeActivity.class);
                intent.putExtra("cart", getIntent().getSerializableExtra("cart"));
                startActivity(intent);
            }
        });

        promote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookInfo.this, WelcomeActivity.class);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookInfo.this, HomeActivity.class);
                intent.putExtra("cart", getIntent().getSerializableExtra("cart"));
                startActivity(intent);
            }
        });
    }
}

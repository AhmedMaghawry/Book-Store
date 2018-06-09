package com.ezzat.bookstore.Controller.recycleRow;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ezzat.bookstore.Controller.HttpJsonParser;
import com.ezzat.bookstore.Model.User;
import com.ezzat.bookstore.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserAdapter extends RecyclerView.Adapter<UserHolder> {

    ArrayList<User> users;
    ViewGroup parent;

    public UserAdapter(ArrayList<User> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_user, parent, false);
        this.parent = parent;
        return new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, final int position) {
        holder.name.setText(users.get(position).getUserName());
        holder.promote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new promoteUser().execute(new String[]{users.get(position).getUserName()});
                users.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }


    public class promoteUser extends AsyncTask<String, Void, Void> {

        HttpJsonParser jParser = new HttpJsonParser();
        private boolean finished = true;

        /**
         * getting All products from url
         * */
        protected Void doInBackground(String... args) {
            // Building Parameters
            Map<String, String> params = new HashMap<>();
            params.put("username", args[0]);
            JSONObject json = jParser.makeHttpRequest("http://10.42.0.1:8085/Android_DB_connect/promote.php", "GET", params);
            try {
                int success = json.getInt("success");
                if (success == 0) {
                    finished = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}

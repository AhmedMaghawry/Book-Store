package com.ezzat.bookstore.Controller;
import android.os.AsyncTask;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class confirmOrders extends AsyncTask<String, Void, Void> {

    HttpJsonParser jParser = new HttpJsonParser();

    /**
     * getting All products from url
     * */
    protected Void doInBackground(String... args) {
        // Building Parameters
        Map<String, String> params = new HashMap<>();
        params.put("isbn", args[0]);
        params.put("quantity", args[1]);
        // getting JSON string from URL
        JSONObject json = jParser.makeHttpRequest("http://10.42.0.1:8085/Android_DB_connect/confirmCart.php", "GET", params);
        return null;
    }

}

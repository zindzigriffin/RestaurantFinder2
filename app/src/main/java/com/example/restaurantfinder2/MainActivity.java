package com.example.restaurantfinder2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.RequestHeaders;
import com.codepath.asynchttpclient.RequestParams;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.restaurantfinder2.models.Restaurants;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {
    public static final String list_of_restaurants = "https://api.yelp.com/v3/businesses/search?term=food&location=San%20Francisco";
    public static final String TAG = "MainActivity";
    public static final String finalToken = "PvwkfBvULdU-SXiMYBE6IDJQ8bNIKtZc0kHZ0CbUEOli3rMz0RprwInlAniV1G887HJvjOMjSs9K00fD8v627WbtZ5bxn5-qUXQ1rkXKpPXbXQ5aRF37LrDaluTtYHYx";
    List<Restaurants> restaurant;
    Button logoutButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AsyncHttpClient client = new AsyncHttpClient();
        logoutButton = findViewById(R.id.logoutButton);
        RequestParams params = new RequestParams();
        params.put("limit", "5");
        params.put("page", 0);
        RequestHeaders header = new RequestHeaders();
        header.put("Authorization", "Bearer " + finalToken);
        //Implementing a get request on the URL
        RequestHeaders request = new RequestHeaders();
        client.get(list_of_restaurants, header, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray businesses = jsonObject.getJSONArray("businesses");
                    Log.i(TAG, "Results "+ businesses.toString());
                    restaurant = Restaurants.fromJSONArray(businesses);
                    Log.i(TAG, "Restaurant "+ restaurant.size());
                } catch (JSONException e) {
                    Log.e(TAG, "hit JSON Exception", e);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "onFailure");

            }
        });
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOut();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
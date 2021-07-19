package com.example.restaurantfinder2.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.RequestHeaders;
import com.codepath.asynchttpclient.RequestParams;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.restaurantfinder2.R;
import com.example.restaurantfinder2.adapters.RestaurantsAdapter;
import com.example.restaurantfinder2.models.Restaurants;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;
//This class retrieves data from the yelp api and displays a list of restaurants
//This class is launched after the login activity once the user has logged in appropriately
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
        //Create the adapter
        RestaurantsAdapter restaurantAdapter =  new RestaurantsAdapter(this, restaurant);
        RecyclerView recyclerViewRestaurants = findViewById(R.id.rvRestaurants);
        restaurant = new ArrayList<>();
        //Set the adapter on the recyclerView
        recyclerViewRestaurants.setAdapter(restaurantAdapter);
        //Set the layout manager on the recyclerView
        recyclerViewRestaurants.setLayoutManager(new LinearLayoutManager(this));
        //Create an instance of the asyncHttpClient method
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
                    //Returns the value mapped by name if it exists and is a JSONArray, or throws otherwise.
                    JSONArray businesses = jsonObject.getJSONArray("businesses");
                    Log.i(TAG, "Results "+ businesses.toString());
                    //Appends all of the elements in the specified collection to the end of this list, in the order that they are returned by the specified collection's iterator (optional operation).
                    restaurant.addAll(Restaurants.fromJSONArray(businesses));
                    //Notify any registered observers that the data set has changed.
                    restaurantAdapter.notifyDataSetChanged();
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
        //onClick listener for the user to logout and return to the sign in screen
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
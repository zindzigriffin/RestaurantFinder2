package com.example.restaurantfinder2.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.RequestHeaders;
import com.codepath.asynchttpclient.RequestParams;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.restaurantfinder2.R;
import com.example.restaurantfinder2.activities.LoginActivity;
import com.example.restaurantfinder2.adapters.RestaurantsAdapter;
import com.example.restaurantfinder2.models.Restaurants;
import com.parse.ParseUser;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;
//This fragment contains the user's main feed which is a list of restaurants and their attributes.
public class MainFragment extends Fragment {
    public static final String list_of_restaurants = "https://api.yelp.com/v3/businesses/search?term=food&location=San%20Francisco";
    public static final String TAG = "MainActivity";
    public static final String finalToken = "PvwkfBvULdU-SXiMYBE6IDJQ8bNIKtZc0kHZ0CbUEOli3rMz0RprwInlAniV1G887HJvjOMjSs9K00fD8v627WbtZ5bxn5-qUXQ1rkXKpPXbXQ5aRF37LrDaluTtYHYx";
    List<Restaurants> restaurant;
    private RecyclerView rvRestaurants;
    private RestaurantsAdapter restaurantsAdapter;
    Button logoutButton;


    public MainFragment() {
        // Required empty public constructor
    }


    //Sets the xml layout
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }
    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //find the recyclerView of the restaurants by ID
        rvRestaurants = view.findViewById(R.id.rvRestaurants);
        //create an arrayList of restaurants
        restaurant = new ArrayList<>();
        //Create the adapter
        restaurantsAdapter =  new RestaurantsAdapter(getContext(), restaurant);
        //Set the layout manager on the recyclerView
        rvRestaurants.setLayoutManager(new LinearLayoutManager(getContext()));
        //Set the adapter on the recyclerView
        rvRestaurants.setAdapter(restaurantsAdapter);
        //Create an instance of the asyncHttpClient method
        AsyncHttpClient client = new AsyncHttpClient();
        //find the logout button by ID
        logoutButton = view.findViewById(R.id.logoutButton);
        RequestParams params = new RequestParams();
        params.put("limit", "25");
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
                    List<Restaurants> restaurants = Restaurants.fromJSONArray(businesses);
                    //Appends all of the elements in the specified collection to the end of this list, in the order that they are returned by the specified collection's iterator (optional operation).
                    restaurant.addAll(restaurants);
                    //Notify any registered observers that the data set has changed.
                    restaurantsAdapter.notifyDataSetChanged();
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
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
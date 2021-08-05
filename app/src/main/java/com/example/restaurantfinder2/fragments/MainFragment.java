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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.RequestHeaders;
import com.codepath.asynchttpclient.RequestParams;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.restaurantfinder2.R;
import com.example.restaurantfinder2.Utils.EndlessRecyclerViewScrollListener;
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
    private RecyclerView recyclerViewRestaurants;
    private RestaurantsAdapter restaurantsAdapter;

    Button logoutButton;
    // Store a member variable for the pull to refresh
    private SwipeRefreshLayout mSwipeContainer;
    // Store a member variable for the listener
    private EndlessRecyclerViewScrollListener mScrollListener;
    long max_ID;


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

        RecyclerView recyclerViewItems = (RecyclerView) view.findViewById(R.id.rvRestaurants);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewItems.setLayoutManager(linearLayoutManager);
        // Lookup the swipe container view
        mSwipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        //set the listener to be notified when a refresh is triggered via the swipe gesture.
        mSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //Calls the populateHomeTimeLine method
                populateHomeTimeLine();


            }
        });
        //Set the color resources used in the progress animation from color resources.
        mSwipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        //find the recyclerView of the restaurants by ID
        recyclerViewRestaurants= view.findViewById(R.id.rvRestaurants);
        //create an arrayList of restaurants
        restaurant = new ArrayList<>();
        //find the logout button by ID
        logoutButton = view.findViewById(R.id.logoutButton);
        //Create the adapter
        restaurantsAdapter = new RestaurantsAdapter(getContext(), restaurant);
        //Set the layout manager on the recyclerView
        recyclerViewRestaurants.setLayoutManager(new LinearLayoutManager(getContext()));
        //Set the adapter on the recyclerView
        recyclerViewRestaurants.setAdapter(restaurantsAdapter);
        //onClick listener for the user to logout and return to the sign in screen
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOut();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        // Retain an instance so that you can call `resetState()` for fresh searches
        mScrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                loadNextDataFromApi(page);
            }
        };
        // Adds the scroll listener to RecyclerView
        recyclerViewRestaurants.addOnScrollListener(mScrollListener);

    }
    // Append the next page of data into the adapter
    // This method probably sends out a network request and appends new data items to your adapter.
    public void loadNextDataFromApi(int page) {
        populateHomeTimeLine();
        //getting the last restaurant and setting it as the max_id
        //max_ID = restaurant.get(restaurant.size()-1);
    }
    //Method to make a network request to the yelp api
    public void populateHomeTimeLine() {
        AsyncHttpClient client = new AsyncHttpClient();
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
                    //Removes all of the elements from this last
                    restaurant.clear();
                    //Returns the value mapped by name if it exists and is a JSONArray, or throws otherwise.
                    JSONArray businesses = jsonObject.getJSONArray("businesses");
                    Log.i(TAG, "Results " + businesses.toString());
                    List<Restaurants> restaurants = Restaurants.fromJSONArray(businesses);
                    //Appends all of the elements in the specified collection to the end of this list, in the order that they are returned by the specified collection's iterator (optional operation).
                    restaurant.addAll(restaurants);
                    //Notify any registered observers that the data set has changed.
                    restaurantsAdapter.notifyDataSetChanged();
                    //Notify the swipeContainer widget that the refresh state has changed.
                    mSwipeContainer.setRefreshing(false);
                    Log.i(TAG, "Restaurant " + restaurant.size());
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

    }
}
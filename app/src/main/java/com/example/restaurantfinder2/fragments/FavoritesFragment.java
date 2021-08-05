package com.example.restaurantfinder2.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantfinder2.R;
import com.example.restaurantfinder2.adapters.RestaurantsAdapter;
import com.example.restaurantfinder2.models.Restaurants;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

//The favorites fragment will store the user's favorite restaurants which will be acquired when the user double taps on the image of the restaurant in the main fragment.
public class FavoritesFragment extends Fragment {
    //Initialize the variables
    private static final String TAG = "FavoritesFragment";
    private RestaurantsAdapter restaurantsAdapter;
    private List<Restaurants> mRestaurantsList;
    private Context mContext;
    public RecyclerView recyclerViewFavorites;


    public FavoritesFragment() {
        // Required empty public constructor
    }

    //The method onCreate is the initial creation of the fragment
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    //Method to inflate the layout for this fragment and initialize all of the objects used in this fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i(TAG, "Inside FavoritesFragment");
        View root = inflater.inflate(R.layout.fragment_favorites, container, false);
        return root;
    }
    //This is the method where any view lookups and attaching listeners are used
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        //Initialize the recyclerView
        RecyclerView recyclerViewItems = (RecyclerView) view.findViewById(R.id.rvFavorites);
        //Create an instance of a vertical layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        //A LayoutManager is responsible for measuring and positioning item views within a RecyclerView as well as determining the policy for when to recycle item views that are no longer visible to the user.
        //set the layout manager for this recyclerView
        recyclerViewItems.setLayoutManager(linearLayoutManager);
        //Initialize restaurantsList
        mRestaurantsList = new ArrayList<>();
        //Initialize a list of restaurant lieks
        List<Restaurants> likes = new ArrayList<>();
        //Initialize the restaurants adapter
        restaurantsAdapter = new RestaurantsAdapter(getContext(), mRestaurantsList);
        //find the recyclerView of the restaurants by ID
        recyclerViewFavorites = view.findViewById(R.id.rvFavorites);
        //Set a new adapter to provide child views on demand.
        //When adapter is changed, all existing views are recycled back to the pool. If the pool has only one adapter, it will be cleared
        recyclerViewFavorites.setAdapter(restaurantsAdapter);
        //The ParseQuery class defines a query that is used to fetch ParseObjects
        //Creates a new query for the given class name. A default query with no further parameters will retrieve all ParseObjects of the provided class name.
        ParseQuery<Restaurants> query = ParseQuery.getQuery("Like");
        //Add a constraint to the query that requires a particular key's value to be equal to the provided value.
        //.getCurrentUser This retrieves the currently logged in ParseUser with a valid session, either from memory or disk if necessary
        query.whereEqualTo("username", ParseUser.getCurrentUser());
        //Retrieves a list of ParseObjects that satisfy this query from the source in a background thread
        query.findInBackground(new FindCallback<Restaurants>() {
            @Override
            public void done(List<Restaurants> likes, ParseException e) {
                //empty constructor to create a new post
                Restaurants restaurants = new Restaurants();
                if (e == null) {
                    //Log statements for purpose of debugging
                    //TODO: delete log statements on final code cleanup
                    Log.i(TAG, "Success: " + likes.toString());
                   Log.d(TAG, "Number of likes: " +likes.size());
                   //for each loop to iterate through the liked restaurants
                    for (Restaurants restaurant : likes) {
                        //add all of the liked content to the list
                        mRestaurantsList.addAll(likes);
                        //notify the adapter that the data set has changed
                        restaurantsAdapter.notifyDataSetChanged();

                    }
                } else {
                    //Toast and log statements for purpose of debugging
                    //TODO: delete log statements on final code cleanup
                    Toast.makeText(getContext(), "There was an error", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Error occurred for Parse: " + e);
                }
            }

        });
    }
}
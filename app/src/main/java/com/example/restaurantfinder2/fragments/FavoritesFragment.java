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

//The favorites fragment will store the user's favorite restaurants which will be acquired when the user clicks on the save button in the main fragment.
//TODO: Cleanup debug lines and fix null object reference errors
public class FavoritesFragment extends Fragment {
    private static final String TAG = "FavoritesFragment";
    private RestaurantsAdapter restaurantsAdapter;
    private List<Restaurants> restaurantsList;
    private Context mContext;
    private int pager;
    public RecyclerView recyclerViewFavorites;


    public FavoritesFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i(TAG, "Inside FavoritesFragment");
        View root = inflater.inflate(R.layout.fragment_favorites, container, false);
        return root;
    }

    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        RecyclerView recyclerViewItems = (RecyclerView) view.findViewById(R.id.rvFavorites);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewItems.setLayoutManager(linearLayoutManager);
        restaurantsList = new ArrayList<>();
        List<Restaurants> likes = new ArrayList<>();
        restaurantsAdapter = new RestaurantsAdapter(getContext(), restaurantsList);
        //find the recyclerView of the restaurants by ID
        recyclerViewFavorites = view.findViewById(R.id.rvFavorites);
        recyclerViewFavorites.setAdapter(restaurantsAdapter);
        ParseQuery<Restaurants> query = ParseQuery.getQuery("Like");
        query.whereEqualTo("username", ParseUser.getCurrentUser());
        Restaurants restaurants = new Restaurants();
        query.findInBackground(new FindCallback<Restaurants>() {
            @Override
            public void done(List<Restaurants> likes, ParseException e) {
                //empty constructor to create a new post
                //Restaurants restaurants = new Restaurants();
                if (e == null) {
                    Log.i(TAG, "Success: " + likes.toString());
                   Log.d(TAG, "Number of likes: " +likes.size());
                    for (Restaurants restaurant : likes) {
                        restaurantsList.addAll(likes);
                        restaurantsAdapter.notifyDataSetChanged();

                    }
                } else {
                    Toast.makeText(getContext(), "There was an error", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Error occurred for Parse: " + e);
                }
            }

        });
    }
}
package com.example.restaurantfinder2.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.RequestHeaders;
import com.codepath.asynchttpclient.RequestParams;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.restaurantfinder2.R;
import com.example.restaurantfinder2.adapters.RecommendationsAdapter;
import com.example.restaurantfinder2.models.Recommendations;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

//The recommend fragment will allow the user to view recommended restaurants based on preferences such as cuisine type, location, and price.
//TODO: Clean up debug lines (log statmements and toast statements)
//TODO: Implement the recommend by category functionality
//TODO: Unhardcode the lcoation
public class RecommendFragment extends Fragment{

    //Initialize variables
    public static final String BASE_URL = "https://api.yelp.com/v3/businesses/search";
    public static final String TAG = "RecommendFragment";
    public static final String finalToken = "PvwkfBvULdU-SXiMYBE6IDJQ8bNIKtZc0kHZ0CbUEOli3rMz0RprwInlAniV1G887HJvjOMjSs9K00fD8v627WbtZ5bxn5-qUXQ1rkXKpPXbXQ5aRF37LrDaluTtYHYx";
    public RecyclerView recyclerViewRecommendations;
    public RecommendationsAdapter recommendationsAdapter;
    private ArrayList<Recommendations> recommendationsArrayList = new ArrayList<>();
//    public Spinner priceSpinner;
//    private ArrayList<Recommendations> pricesList = new ArrayList<Recommendations>();
//    ArrayAdapter<String> priceListAdapter;
    public RecommendFragment() {
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
        return inflater.inflate(R.layout.fragment_recommend, container, false);
    }
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Setting toolbar
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        recyclerViewRecommendations = view.findViewById(R.id.rvRecommend);
        // Initialize the adapter
        recommendationsAdapter = new RecommendationsAdapter(getContext(), recommendationsArrayList);
        // Attach the adapter to the RecyclerView
        recyclerViewRecommendations.setAdapter(recommendationsAdapter);
        // Set layout manager to position the items
        recyclerViewRecommendations.setLayoutManager(new LinearLayoutManager(getContext()));
        //Get data from the searchView & query the API to get the results (Recipes)
        final SearchView searchView = view.findViewById(R.id.search_view2);
        //priceSpinner = view.findViewById(R.id.spinnerPrice);

        //searchView listener that receives callbacks when the user performs actions in the searchView such as clicking on buttons or providing a query.
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "success!");
                //calls the recipes api
                fetchRecommended(query);
                Toast.makeText(getContext(), "Location : " + query, Toast.LENGTH_SHORT).show();
                return true;
            }


            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }
    //Method to call recommended restaurants
    public void fetchRecommended(String query) {
        //create an instance of the client
        AsyncHttpClient client = new AsyncHttpClient();
        Log.i(TAG, "query:" + query);
        RequestParams params = new RequestParams();
        params.put("location",query);
        params.put("price",2);
        params.put("radius","200");
        RequestHeaders header = new RequestHeaders();
        //inserting api as a header
        header.put("Authorization", "Bearer " + finalToken);
        //making a get request to the API
        client.get(BASE_URL, header, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                //printing onSuccess if response is successful
                Log.d(TAG, "yay");
                JSONObject jsonObject = json.jsonObject;
                try {
                    //Returns the value mapped by name if it exists and is a JSONArray, or throws an exception otherwise.
                    JSONArray location = jsonObject.getJSONArray("businesses");
                    Log.i(TAG, "Recommendations" + location.toString());
                    List<Recommendations> recommendations = Recommendations.fromJSONArray(location);
                    //add all of the recommended restaurants to the arrayList
                    recommendationsArrayList.addAll(recommendations);
                    //notify the adapter that the data has changed
                    recommendationsAdapter.notifyDataSetChanged();
                    //recipesAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    Log.e(TAG, "hit JSON Exception", e);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "onFailure" + statusCode + response);

            }
        });


    }

}
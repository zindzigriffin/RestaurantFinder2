package com.example.restaurantfinder2.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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

//The recommend fragment will allow the user to view recommended restaurants based on the preferences selected (location, categories, and price).
public class RecommendFragment extends Fragment {

    //Declare variables
    public static final String BASE_URL = "https://api.yelp.com/v3/businesses/search";
    public static final String TAG = "RecommendFragment";
    public static final String finalToken = "PvwkfBvULdU-SXiMYBE6IDJQ8bNIKtZc0kHZ0CbUEOli3rMz0RprwInlAniV1G887HJvjOMjSs9K00fD8v627WbtZ5bxn5-qUXQ1rkXKpPXbXQ5aRF37LrDaluTtYHYx";
    public RecyclerView recyclerViewRecommendations;
    public RecommendationsAdapter recommendationsAdapter;
    private ArrayList<Recommendations> recommendationsArrayList = new ArrayList<>();
    private String mQuery;
    private String mCuisine;
    private String mPrice;
    public Button buttonSubmit;
    public RecommendFragment() {
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
        return inflater.inflate(R.layout.fragment_recommend, container, false);
    }
    //This is the method where any view lookups and attaching listeners are used
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        //Called immediately after onCreateView(LayoutInflater, ViewGroup, Bundle) has returned, but before any saved state has been restored in to the view.
        //This gives subclasses a chance to initialize themselves once they know their view hierarchy has been completely created.
        super.onViewCreated(view, savedInstanceState);
        // Spinner element
        Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
        //Spinner click listener for price type
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // Callback method to be invoked when an item in this view has been selected.
            //This callback is invoked only when the newly selected position is different from the previously selected position or if there was no selected item.
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mPrice = parent.getItemAtPosition(position).toString();
            }
            //Callback method to be invoked when the selection disappears from this view.
            // The selection can disappear for instance when touch is activated or when the adapter becomes empt
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //set the cuisine type to  null if nothing is selected
                mCuisine = null;
                //set the price level filtering to null if nothing is selected
                mPrice = null;
            }
        });
        // Spinner element
        Spinner spinner_cuisine = (Spinner) view.findViewById(R.id.spinner_cuisine);
        //Spinner click listener for cuisine type (register a callback to be invoked when an item in this adapterview has been selected
        spinner_cuisine.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //Callback method to be invoked when an item in this view has been selected.
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //get the item at a specific position and return a string representation of the object and set it to price
                mCuisine = parent.getItemAtPosition(position).toString();

            }
            //Callback method to be invoked when the selection disappears from this view.
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //set cuisine to null if nothing is selected
                mCuisine = null;
                //set price to null if nothing is selected
                mPrice = null;
            }
        });
        //Finds the first descendant view with the given ID
        buttonSubmit = view.findViewById(R.id.btnSubmit);
        //Register a callback to be invoked when this view is clicked
        buttonSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //get the data corresponding to the currently selected item
                spinner_cuisine.getSelectedItem();
                //get the data corresponding to the currently selected item
                spinner.getSelectedItem();
                //call the fetch recommended method
                //fetchRecommended();
                //toast message to let the user know what items they have selected
                Toast.makeText(getContext(),
                        "OnClickListener : " +
                                "\nSpinner 1 : "+ String.valueOf(spinner_cuisine.getSelectedItem()) +
                                "\nSpinner 2 : "+ String.valueOf(spinner.getSelectedItem()),
                        Toast.LENGTH_SHORT).show();
            }

        });

        // Spinner Drop down of price filtering
        List<String> categories = new ArrayList<String>();
        //Appends the specified element to the end of this list
        //Constructs an arrayList of cuisines
        categories.add("$");
        categories.add("$$");
        categories.add("$$$");
        categories.add("$$$$");

        // Spinner Drop down for cuisines filtering
        //Constructs an arrayList of cuisines
        List<String> cuisines = new ArrayList<String>();
        //Appends the specified element to the end of this list
        cuisines.add("Japanese");
        cuisines.add("Seafood");
        cuisines.add("Chinese");
        cuisines.add("American");
        cuisines.add("Italian");


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, categories);
        //attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        // Creating adapter for spinner
        ArrayAdapter<String> cuisinesAdapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, cuisines);
        //attaching data adapter to spinner
        spinner_cuisine.setAdapter(cuisinesAdapter);
        //submit button initialization
        buttonSubmit = (Button) view.findViewById(R.id.btnSubmit);
        // Setting toolbar
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        //Initialize the recyclerview
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
            //Called when the user submits the query.
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "success!");
                //calls the recipes api
                //set the instance variable named query to the parameter
                RecommendFragment.this.mQuery = query;
                fetchRecommended(mQuery);
                Toast.makeText(getContext(), "Location : " + query, Toast.LENGTH_SHORT).show();
                return true;
            }

            //Called when the query text is changed by the user.
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

        });

    }
    //Method to call recommended restaurants
    public void fetchRecommended(String mQuery) {
        //create an instance of the client
        AsyncHttpClient client = new AsyncHttpClient();
        Log.i(TAG, "query:" + mQuery);
        RequestParams params = new RequestParams();
        params.put("location",mQuery);
        params.put("radius","200");
        params.put("price", 2);
        if(mCuisine !=null){
            params.put("categories", String.valueOf(mCuisine));
        }
        if(mPrice !=null){
            params.put("price", String.valueOf(mPrice));
        }
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
                    Log.i(TAG, "list of recommendations" + recommendations.toString());
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
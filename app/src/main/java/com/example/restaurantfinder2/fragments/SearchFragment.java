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
import com.example.restaurantfinder2.adapters.RecipesAdapter;
import com.example.restaurantfinder2.models.Recipes;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;
//The search fragment allows the user to search for dishes by entering some ingredients into a searchbar.
public class SearchFragment extends Fragment {
    //Declare variables
    public static final String APP_ID = "5420521";
    public static final String API_KEY = "960b664e1bmsh613e17b2b2ceab3p1a2fe3jsnce5dbacacf52";
    public static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/filter.php";
    public static final String TAG = "SearchFragment";
    public RecyclerView recyclerViewRecipes;
    public RecipesAdapter recipesAdapter;
    private ArrayList<Recipes> aRecipes = new ArrayList<>();


    public SearchFragment() {
        // Required empty public constructor
    }

    //Method to inflate the layout for this fragment and initialize all of the objects used in this fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }
    //This is the method where any view lookups and attaching listeners are used
    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Setting toolbar
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        recyclerViewRecipes = view.findViewById(R.id.rvRecipes);
        // Initialize the adapter
        recipesAdapter = new RecipesAdapter(getContext(), aRecipes);
        // Attach the adapter to the RecyclerView
        recyclerViewRecipes.setAdapter(recipesAdapter);
        // Set layout manager to position the items
        recyclerViewRecipes.setLayoutManager(new LinearLayoutManager(getContext()));

        //Get data from the searchView & query the API to get the results (Recipes)
        final SearchView searchView = view.findViewById(R.id.search_view);
        //Sets a listener for user actions within the SearchView.
        //listener – the listener object that receives callbacks when the user performs actions in the SearchView such as clicking on buttons or typing a query.
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //Called when the user submits the query.
            @Override
            public boolean onQueryTextSubmit(String query) {
                fetchRecipes(query);
                Toast.makeText(getContext(), "Our word : " + query, Toast.LENGTH_SHORT).show();
                return true;
            }
            //Called when the query text is changed by the user.
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    public void fetchRecipes(String query) {
        //creating an instance of AsyncHttpClient
        AsyncHttpClient client = new AsyncHttpClient();
        //creating an instance of RequestParams
        RequestParams params = new RequestParams();
        params.put("i",query);
        RequestHeaders header = new RequestHeaders();
        //inserting api as a header
        header.put("x-rapidapi-key", "960b664e1bmsh613e17b2b2ceab3p1a2fe3jsnce5dbacacf52");
        header.put("x-rapidapi-host", "themealdb.p.rapidapi.com");
        //making a get request to the API
        client.get(BASE_URL, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                //set the json object
                JSONObject jsonObject = json.jsonObject;
                try {
                    //Returns the value mapped by name if it exists and is a JSONArray, or throws otherwise.
                    JSONArray meals = jsonObject.getJSONArray("meals");
                    List<Recipes> recipes = Recipes.fromJSONArray(meals);
                    //add all the elements to the list
                    aRecipes.addAll(recipes);
                    //notify the adapter that the data has changed
                    recipesAdapter.notifyDataSetChanged();
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


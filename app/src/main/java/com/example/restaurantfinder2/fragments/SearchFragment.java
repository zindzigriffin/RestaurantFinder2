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
//The search fragment allows the user to searchh for recipes by entering some ingredients into a searchbar.
public class SearchFragment extends Fragment {
    public static final String APP_ID = "5420521";
    public static final String API_KEY = "960b664e1bmsh613e17b2b2ceab3p1a2fe3jsnce5dbacacf52";
    public static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/filter.php?i=chicken_breast";
    public static final String TAG = "SearchFragment";
    public RecyclerView recyclerViewRecipes;
    public RecipesAdapter recipesAdapter;
    private ArrayList<Recipes> aRecipes = new ArrayList<>();


    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Setting toolbar
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        recyclerViewRecipes = view.findViewById(R.id.rvRecipes);
        Log.i(TAG, "hi");
        // Initialize the adapter
        recipesAdapter = new RecipesAdapter(getContext(), aRecipes);
        // Attach the adapter to the RecyclerView
        recyclerViewRecipes.setAdapter(recipesAdapter);
        Log.i(TAG, "hi1");
        // Set layout manager to position the items
        recyclerViewRecipes.setLayoutManager(new LinearLayoutManager(getContext()));
        Log.i(TAG, "hi2");
        //creating an instance of AsyncHttpClient

        //Get data from the searchView & query the API to get the results (Recipes)
        final SearchView searchView = view.findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "success!");
                fetchRecipes(query);
                Toast.makeText(getContext(), "Our word : " + query, Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    public void fetchRecipes(String query) {
        AsyncHttpClient client = new AsyncHttpClient();
        Log.i(TAG, "query" + query);
        RequestParams params = new RequestParams();
        params.put("i",query);
        RequestHeaders header = new RequestHeaders();
        //inserting api as a header
        header.put("x-rapidapi-key", "960b664e1bmsh613e17b2b2ceab3p1a2fe3jsnce5dbacacf52");
        header.put("x-rapidapi-host", "themealdb.p.rapidapi.com");
        //making a get request to the API
        Log.d(TAG, "onSuccess");
        client.get(BASE_URL, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                //printing onSuccess if response is successful
                Log.d(TAG, "onSuccess2");
                JSONObject jsonObject = json.jsonObject;
                Log.i(TAG, "recipes2");
                try {
                    //Returns the value mapped by name if it exists and is a JSONArray, or throws otherwise.
                    JSONArray meals = jsonObject.getJSONArray("meals");
                    List<Recipes> recipes = Recipes.fromJSONArray(meals);
                    Log.i(TAG, "recipes");
                    aRecipes.addAll(recipes);
                    recipesAdapter.notifyDataSetChanged();
                    Log.i(TAG, "Meals" + meals.toString());
                    //recipesAdapter.notifyDataSetChanged();
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


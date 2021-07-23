package com.example.restaurantfinder2.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Recipes {
    String label;
    String image;

    //Construct a restaurant object

    //Construct a restaurant object
    public Recipes(JSONObject jsonObject) throws JSONException {
        label = jsonObject.getString("strMeal");
        image = jsonObject.getString("strMealThumb");
    }


    public static List<Recipes> fromJSONArray(JSONArray mealsJsonArray) throws JSONException {
        List<Recipes> recipes = new ArrayList<>();
        for(int i = 0; i< mealsJsonArray.length(); i++ ){
            //add a restaurant to each position of the array
            recipes.add(new Recipes(mealsJsonArray.getJSONObject(i)));
        }
        return recipes;
    }


    public String getLabel(){
        return label;
    }
    public String getImage(){
        return image;
    }



}

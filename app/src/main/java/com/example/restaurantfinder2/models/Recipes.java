package com.example.restaurantfinder2.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
//This is the model class of recipes that stores the data for the recipes
//TODO: add "m" to all member variables and make sure everything is spelled out fully
public class Recipes {
    String label;
    String image;

    //Construct a restaurant object

    //Construct a recipes object
    public Recipes(JSONObject jsonObject) throws JSONException {
        label = jsonObject.getString("strMeal");
        image = jsonObject.getString("strMealThumb");
    }

    //Iterating through the list of recipes and constructing an element for each restaurant in the JSON array
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

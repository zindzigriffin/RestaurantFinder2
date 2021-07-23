package com.example.restaurantfinder2.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Restaurants {
    String alias;
    String id;
    String name;
    String phone;
    String imageUrl;
    //String price;
    String rating;
    JSONObject location;

    //Construct a restaurant object
    public Restaurants(JSONObject jsonObject) throws JSONException {
        alias = jsonObject.getString("alias");
        name = jsonObject.getString("name");
        id = jsonObject.getString("phone");
        imageUrl = jsonObject.getString("image_url");
        phone = jsonObject.getString("phone");
        //price = jsonObject.getString("price");
        rating = jsonObject.getString("rating");
        location = jsonObject.getJSONObject("location");
        String address1 = location.getString("address1");
    }
    //iterating through the list of restaurants and constructing an element for each restaurant in the JSON array
    public static List<Restaurants> fromJSONArray(JSONArray restaurantsJsonArray) throws JSONException {
        List<Restaurants> restaurant = new ArrayList<>();
        for(int i = 0; i< restaurantsJsonArray.length(); i++ ){
            //add a restaurant to each position of the array
            restaurant.add(new Restaurants(restaurantsJsonArray.getJSONObject(i)));
        }
        return restaurant;
    }

    public String getAlias() {
        return alias;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getImageUrl() {
        return imageUrl;
    }


    public String getRating() {
        return rating;
    }

    public JSONObject getLocation() {
        return location;
    }
}


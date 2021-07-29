package com.example.restaurantfinder2.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Recommendations {
    JSONObject address;
    String image;
    String name;

    //Construct a restaurant object
    public Recommendations(JSONObject jsonObject) throws JSONException {
        name = jsonObject.getString("name");
        image = jsonObject.getString("image_url");
        address = jsonObject.getJSONObject("location");
        String address1 = address.getString("address1");
    }

    //iterating through the list of restaurants and constructing an element for each restaurant in the JSON array
    public static List<Recommendations> fromJSONArray(JSONArray recommendationsJsonArray) throws JSONException {
        List<Recommendations> recommendation = new ArrayList<>();
        for(int i = 0; i< recommendationsJsonArray.length(); i++ ){
            //add a restaurant to each position of the array
            recommendation.add(new Recommendations(recommendationsJsonArray.getJSONObject(i)));
        }
        return recommendation;
    }
    public String getImageUrl() {
        return image;
    }
    public String getName() {

        return name;
    }
    public JSONObject getLocation() {
        return address;
    }

}

package com.example.restaurantfinder2;

import android.app.Application;

import com.parse.Parse;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("56rgn6fQtB8FuMLrT4XwEIgp3yMvIG4XyCzpzuvC")
                .clientKey("WWDulf5T5gvdxu4snl8Pp1lQuhPozQo4Cwj5t9YD")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}

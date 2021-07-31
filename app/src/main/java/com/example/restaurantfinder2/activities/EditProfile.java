package com.example.restaurantfinder2.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.restaurantfinder2.R;

public class EditProfile extends AppCompatActivity {
    public static final String TAG = "EditProfile";

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Intent data = getIntent();
        String firstName = data.getStringExtra("firstname");
        String lastName = data.getStringExtra("lastname");
        Log.d(TAG, "onCreate" +firstName + lastName);

    }
}
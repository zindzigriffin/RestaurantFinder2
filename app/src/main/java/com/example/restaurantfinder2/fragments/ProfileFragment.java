package com.example.restaurantfinder2.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.restaurantfinder2.R;

import org.jetbrains.annotations.NotNull;

//The profile fragment will allow the user to view their profile and make changes such as edit their first and last name
//TODO: Implement the edit profile funcitonality. Change password button is just there for aesthetic (does not work).
public class ProfileFragment extends Fragment {
    public static final String TAG = "ProfileFragment";

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    //The onCreateView method is called when Fragment should create its View object hierarchy
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        return view;
    }
    //This event is triggered soon after onCreateView();
    //Any view setup should occur here
    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}
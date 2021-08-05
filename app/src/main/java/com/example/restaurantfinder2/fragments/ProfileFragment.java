package com.example.restaurantfinder2.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.restaurantfinder2.R;

import org.jetbrains.annotations.NotNull;

//The profile fragment will allow the user to edit and view their profile
public class ProfileFragment extends Fragment {
    //Declare variables
    public static final String TAG = "ProfileFragment";
    private Button mEditprofile;
    public EditText firstNameEditText;
    public EditText lastNameEditText;
    public TextView textViewNames;


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
    //This is the method where any view lookups and attaching listeners are used
    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        //Called immediately after onCreateView(LayoutInflater, ViewGroup, Bundle) has returned, but before any saved state has been restored in to the view
        //Initialize variables used in this class
        super.onViewCreated(view, savedInstanceState);
        mEditprofile = view.findViewById(R.id.editProfile);
        final EditText firstNameEditText = view.findViewById(R.id.etFirstName);
        final EditText lastNameEditText= view.findViewById(R.id.etLastName);
        final TextView textViewNames = view.findViewById(R.id.tvNames);


            // Add a clickListener on the edit profile to modify the first and last name of the user
            mEditprofile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "Clicked First Name", Toast.LENGTH_LONG).show();
                    //return the text of the firstname that the textView is displaying as a string
                    String newText = firstNameEditText.getText().toString();
                    //return the text of the lastname that the textView is displaying as a string
                    String newText1 = lastNameEditText.getText().toString();
                    //set the text of whatever the user types in for the first name and lastname
                    textViewNames.setText("Welcome: "+ newText + " "+ newText1);
                    //toast message for the user to see that the data is saved
                    Toast.makeText(getContext(), "Hi" + newText + newText1, Toast.LENGTH_LONG).show();

                }
            });


        }
    }
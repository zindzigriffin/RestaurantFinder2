package com.example.restaurantfinder2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class RegistrationActivity extends AppCompatActivity {
    public static final String TAG = "RegistrationActivity";
    //Create instance variables
    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button buttonSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        if (ParseUser.getCurrentUser() != null) {
            mainActivity();
        }
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonSignUp = findViewById(R.id.buttonSignUp);
        //Setup an OnClick listener for the sign up button
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick sign up button");
                if (!editTextUsername.getText().toString().isEmpty() && !editTextPassword.getText().toString().isEmpty()) {
                    ParseUser user = new ParseUser();
                    user.setUsername(editTextUsername.getText().toString());
                    user.setPassword(editTextPassword.getText().toString());
                    //calls the loginUser method
                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e == null){
                                Toast.makeText(getApplicationContext(), "Sign up Sucessful", Toast.LENGTH_LONG).show();
                                mainActivity();
                            }
                            else {
                                Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }
//        public void signUpUser(String username, String password){
//            // Create the ParseUser
//            ParseUser user = new ParseUser();
//            // Set core properties
//            user.setUsername(username);
//            user.setPassword(password);
//            // Invoke signUpInBackground
//            user.signUpInBackground(new SignUpCallback() {
//                public void done(ParseException e) {
//                    if (e == null) {
//                        mainActivity();
//                    } else {
//                        Log.e(TAG, "Issue with login", e);
//                    }
//                    }
//            });
//        }
    //Method to launch the Main Activity
    private void mainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

}
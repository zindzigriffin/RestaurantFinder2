package com.example.restaurantfinder2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

//This class allows the user to sign in and login on Instagram. This screen is launched from the splash screen using an Intent
//The login activity screen launches the Main Activity Screen
public class LoginActivity extends AppCompatActivity {
    public static final String TAG = "LoginActivity";
    //Create instance variables
    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button buttonLogin;
    private Button buttonSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if(ParseUser.getCurrentUser()!=null){
            goMainActivity();
        }

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonSignUp = findViewById(R.id.buttonSignUp);
        //Setup an OnClick listener for the logout button
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick login button");
                //converts the username's text to a string
                String username = editTextUsername.getText().toString();
                //grabs the password for the user and converts it to a string
                String password = editTextPassword.getText().toString();
                //calls the loginUser method
                loginUser(username, password);
            }
        });
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
                                goMainActivity();
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
    //This method allows the user to login by taking their username and password as a parameter
    //If the user has logged in appropriately a Success message will pop up on the screen using Toast and then goes to the Main Activity
    //Otherwise if they did not an Issue with login message pops up using Toast
    private void loginUser(String username, String password){
        Log.i(TAG, "Attempting to login user" + username);
        //TODO: navigate to the main activity if the user has signed in properly
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(e!=null){
                    Log.e(TAG, "Issue with login", e);
                    Toast.makeText(LoginActivity.this,"Issue with login",Toast.LENGTH_SHORT).show();
                    return;
                }
                goMainActivity();
                Toast.makeText(LoginActivity.this,"Success",Toast.LENGTH_SHORT).show();
                Log.e(TAG, "login activity repeating");
            }
        });
    }

    //Method to launch the Main Activity
    private void goMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
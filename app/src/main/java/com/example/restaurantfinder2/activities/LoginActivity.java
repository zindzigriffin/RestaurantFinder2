package com.example.restaurantfinder2.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restaurantfinder2.R;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

//This class allows the user to login and sign up for an account to get access to the content of the Restaruant Finder app.
//This is the first screen that is launched when the user opens the app.
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
        //This retrieves the currently logged in ParseUser with a valid session and checks to see if that current user is not null.
        //If the currently logged in user is not null then it calls the main activity
        if(ParseUser.getCurrentUser()!=null){
            //Calls the method goMainActivity which launches the main feed of the app.
            goMainActivity();
        }
        //Finds a view that was identified by the android: id XML attribute that was created and processed in onCreate.
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
        //Setup OnClick listener using the sign up button
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //log statement to see if the user signed up
                Log.i(TAG, "onClick sign up button");
                //if statement to convert the username and password to a string and check to see if it's empty
                if (!editTextUsername.getText().toString().isEmpty() && !editTextPassword.getText().toString().isEmpty()) {
                    //create an instance of a ParseUser
                    ParseUser user = new ParseUser();
                    //grabs the username, converts it to a string and sets the username so it's not null or blank
                    user.setUsername(editTextUsername.getText().toString());
                    //grabs the password, converts it to a string and sets the password so it is not null or blank
                    user.setPassword(editTextPassword.getText().toString());
                    //Signs up a new user.
                    // You should call this instead of ParseObject.save() for new ParseUsers. This will create a new ParseUser on the server, and also persist the session on disk so that you can access the user using getCurrentUser().
                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e == null){
                                //if the user signed up currently then show the toast message "sign up sucessfu"
                                Toast.makeText(getApplicationContext(), "Sign up Sucessful", Toast.LENGTH_LONG).show();
                                //then call the goMainActivity method
                                goMainActivity();
                            }
                            else {
                                //getApplicationContext() - Return the context of the single, global Application object of the current process
                                //getMessage() -  Returns the detail message string of this throwable.
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
        //Logs in a user with a username and password. On success, this saves the session to disk, so you can retrieve the currently logged in user using getCurrentUser().
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
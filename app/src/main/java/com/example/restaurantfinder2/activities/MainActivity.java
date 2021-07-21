package com.example.restaurantfinder2.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.restaurantfinder2.R;
import com.example.restaurantfinder2.fragments.MainFragment;
import com.example.restaurantfinder2.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

//This class retrieves data from the yelp api and displays a list of restaurants
//This class is launched after the login activity once the user has logged in appropriately
public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    //handles transition from one fragment to another
    FragmentManager fragmentManager = getSupportFragmentManager();
    BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationView = findViewById(R.id.bottomNavigation);

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem menuItem) {
                Fragment selectedFragment;
                switch (menuItem.getItemId()) {
                    case R.id.action_favorites:
                        selectedFragment = new ProfileFragment();
                        Log.d(TAG, "Favorites!");
                        break;
                    case R.id.action_home:
                        selectedFragment = new MainFragment();
                        Log.d(TAG, "Home!");
                        break;
                    case R.id.action_profile:
                        selectedFragment = new ProfileFragment();
                        Log.d(TAG, "Profile!");
                        break;
                    case R.id.action_recommend:
                        selectedFragment = new ProfileFragment();
                        Log.d(TAG, "Recommend!");
                        break;
                    case R.id.action_search:
                        selectedFragment = new ProfileFragment();
                        Log.d(TAG, "Search!");
                        break;
                    default:
                        selectedFragment = new MainFragment();
                        break;
                }
                //makes the fragment manager work
                fragmentManager.beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                return true;
            }
        });
        navigationView.setSelectedItemId(R.id.action_home);
    }


}
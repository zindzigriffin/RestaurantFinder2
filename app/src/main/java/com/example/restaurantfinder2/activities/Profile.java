package com.example.restaurantfinder2.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.restaurantfinder2.R;
import com.example.restaurantfinder2.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

public class Profile extends AppCompatActivity {
    public static final String TAG = "ProfileActivity";
    private BottomNavigationView bottomNavigationView;
    final FragmentManager fragmentManager = getSupportFragmentManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        init();
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem menuItem) {
                Fragment fragment = null;
                switch (menuItem.getItemId()) {
                    case R.id.action_favorites:
                        fragment = new ProfileFragment();
                        break;
                    case R.id.action_home:
                        Toast.makeText(Profile.this,"Home!",Toast.LENGTH_SHORT).show();
                        fragment = new ProfileFragment();
                        break;
                    case R.id.action_profile:
                        Toast.makeText(Profile.this,"Profile!",Toast.LENGTH_SHORT).show();
                        fragment = new ProfileFragment();
                        break;
                    case R.id.action_recommend:
                        Toast.makeText(Profile.this,"Recommend!",Toast.LENGTH_SHORT).show();
                        fragment = new ProfileFragment();
                        break;
                    case R.id.action_search:
                        Toast.makeText(Profile.this,"Search!",Toast.LENGTH_SHORT).show();
                        fragment = new ProfileFragment();
                    default:
                        break;
                };
                fragmentManager.beginTransaction().replace(R.id.flcontainer,fragment).commit();
                return true;
            }
        });
    }
    private void init(){
        Log.d(TAG,"init:inflating" + getString(R.string.profile_fragment));

        ProfileFragment fragment = new ProfileFragment();
        FragmentTransaction transaction = Profile.this.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.flcontainer, fragment);
        transaction.addToBackStack(getString(R.string.profile_fragment));
        transaction.commit();

    }
}
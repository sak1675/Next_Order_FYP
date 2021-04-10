package com.example.nextorder;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

public class StatusActivity extends AppCompatActivity {
    ImageButton navigation, profile;

    Button CurrentOrder, CompletedOrder;

    NavigationView navview;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        navigation = findViewById(R.id.status_nav);
        navview = findViewById(R.id.status_navbar);
        profile = findViewById(R.id.statusprofilebtn);
        CurrentOrder = findViewById(R.id.CurrentOrder);
        CompletedOrder = findViewById(R.id.CompletedOrder);



        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Create a new Fragment to be placed in the activity layout
        Fragment fragment = new CurrentOrderFragment();
        // Add the fragment to the 'fragment_container' Layout present in Activity
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();


        navview.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.navhome:
                        Intent inten = new Intent(getApplicationContext(),HomeActivity.class);
                        startActivity(inten);
                        return true;
                    case R.id.navOrders:
                        Intent intent = new Intent(getApplicationContext(),OrderActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.navGroups:
                        Intent intent1 = new Intent(getApplicationContext(),GroupActivity.class);
                        startActivity(intent1);
                        return true;
                    case R.id.navStatus:
                        Intent status = new Intent(getApplicationContext(), StatusActivity.class);
                        startActivity(status);
                        return true;
                    case R.id.navContact:
                        Intent intent3 = new Intent(getApplicationContext(),ContactsActivity.class);
                        startActivity(intent3);
                        return true;
                    case R.id.navClose:
                        navview.setVisibility(View.INVISIBLE);
                        return true;
                }
                return false;
            }
        });

        navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navview.setVisibility(View.VISIBLE);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(StatusActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        CurrentOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                // Create a new Fragment to be placed in the activity layout
                Fragment fragment = new CurrentOrderFragment();
                // Add the fragment to the 'fragment_container' Layout present in Activity
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        CompletedOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                // Create a new Fragment to be placed in the activity layout
                Fragment fragment = new CompletedOrderFragment();
                // Add the fragment to the 'fragment_container' Layout present in Activity
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });
    }
}
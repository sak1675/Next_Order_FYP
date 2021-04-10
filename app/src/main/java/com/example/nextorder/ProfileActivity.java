package com.example.nextorder;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.navigation.NavigationView;

public class ProfileActivity extends AppCompatActivity {
    ImageButton navigation;
    NavigationView navview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        navigation = findViewById(R.id.profile_nav);
        navview = findViewById(R.id.profile_navbar);


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
    }
}
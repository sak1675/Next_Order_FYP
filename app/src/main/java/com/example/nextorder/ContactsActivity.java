package com.example.nextorder;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.navigation.NavigationView;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactsActivity extends AppCompatActivity {
    ImageButton navigation, profile;
    NavigationView navview;
    Button send;
    CircleImageView facebook,mail;

    EditText contactustxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        navigation = findViewById(R.id.contact_nav);
        profile = findViewById(R.id.contactprofilebtn);
        navview = findViewById(R.id.contact_navbar);
        contactustxt = findViewById(R.id.contactuxtxt);
        send = findViewById(R.id.contactsend);
        facebook = findViewById(R.id.facebook);
        mail = findViewById(R.id.gmail);



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

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}
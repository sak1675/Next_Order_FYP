package com.example.nextorder;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

public class MenuActivity extends AppCompatActivity {
    ImageButton navigation, searchbtn, orderlist;
    EditText searchtxt;
    RecyclerView menuview;

    NavigationView navview;

    MenuAdapter menuAdapter;
    ArrayList<MenuModel> menulist;

    private static final String uRl = "https://192.168.1.114/nextorder/MenuModel.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        navigation = findViewById(R.id.menu_nav);
        searchbtn = findViewById(R.id.menu_searchbtn);
        orderlist = findViewById(R.id.ordersitems);
        searchtxt = findViewById(R.id.menu_searchbar);
        navview = findViewById(R.id.menu_navbar);


        menuview = findViewById(R.id.menurecycleview);

        menulist = new ArrayList<MenuModel>();

        getMenuData();
        trustEveryone();

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

        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        orderlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),OrderlistActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getMenuData() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, uRl,
                response -> {
                    try {
                        JSONArray array = new JSONArray(response);
                        for (int i=0; i<array.length();i++) {
                            JSONObject jsonObject = array.getJSONObject(i);

                            String DishName = jsonObject.getString("DishName");
                            String DishPrice = jsonObject.getString("DishPrice");
                            String DishImage = jsonObject.getString("DishImage");
                            String DishDescription = jsonObject.getString("DishDescription");


                            menulist.add(new MenuModel(DishName, DishImage, DishPrice, DishDescription));
                            menuAdapter = new MenuAdapter(MenuActivity.this, menulist);
                            menuview.setAdapter(menuAdapter);
                            menuAdapter.notifyDataSetChanged();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();

                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }

    private void trustEveryone() {
        try {
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier(){
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }});
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new X509TrustManager[]{new X509TrustManager(){
                public void checkClientTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {}
                public void checkServerTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {}
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }}}, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(
                    context.getSocketFactory());
        } catch (Exception e) { // should never happen
            e.printStackTrace();
        }
    }
}
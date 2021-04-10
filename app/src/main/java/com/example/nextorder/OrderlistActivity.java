package com.example.nextorder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OrderlistActivity extends AppCompatActivity {
    RecyclerView orderview;
    Button Order;
    ImageButton Home;
    LinearLayoutManager linearLayoutManager;
    ArrayList<OrderlistModel> olist;

    OrderlistAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderlist);

        Home = findViewById(R.id.orderlisthome);
        Order = findViewById(R.id.Orderlist_Order);


        orderview = findViewById(R.id.orderlistview);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        orderview.setLayoutManager(linearLayoutManager);
        olist = new ArrayList<OrderlistModel>();


        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inten = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(inten);
            }
        });

        Order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < olist.size(); i++) {
                    OrderlistModel orderlistModel = olist.get(i);

                    String DishName = orderlistModel.getDishname();
                    String UserName = orderlistModel.getUserName();
                    String DishPrice = orderlistModel.getDishPrice();
                    String quantity = orderlistModel.getQuantity();

                }
                Intent inten = new Intent(getApplicationContext(),OrderingActivity.class);
                startActivity(inten);
            }
        });

        getOrderData();

    }


    public static String getDefaults(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }

    private void getOrderData() {
        String username = getDefaults("username", this);
        String uRl = "https://192.168.1.114/nextorder/orderlist.php?Username=\"" + username + "\"";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, uRl,
                response -> {
                    try {
                        JSONArray array = new JSONArray(response);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject jsonObject = array.getJSONObject(i);
                            String UserName = jsonObject.getString("Username");
                            String DishImage = jsonObject.getString("DishImage");
                            String DishName = jsonObject.getString("DishName");
                            String DishPrice = jsonObject.getString("DishPrice");
                            String quantity = jsonObject.getString("quantity");


                            olist.add(new OrderlistModel(UserName, DishImage, DishName, DishPrice, quantity));
                            adapter = new OrderlistAdapter(OrderlistActivity.this, olist);
                            orderview.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


}
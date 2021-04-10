package com.example.nextorder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

public class OrderingActivity extends AppCompatActivity {
    ImageButton navigation, profile;

    Button Bill, Cancel;

    EditText CustomOrder;

    RecyclerView orderview;

    NavigationView navview;

    ArrayList<OrderingModel> olist;


    OrderingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordering);

        navigation = findViewById(R.id.ordering_nav);
        profile = findViewById(R.id.orderingprofilebtn);
        navview = findViewById(R.id.ordering_navbar);
        Bill = findViewById(R.id.OrderHere);
        Cancel = findViewById(R.id.CancleOrder);
        CustomOrder = findViewById(R.id.customordertxt);



        orderview = findViewById(R.id.orderingrecycleview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        orderview.setLayoutManager(linearLayoutManager);
        olist = new ArrayList<OrderingModel>();


        trustEveryone();


        navview.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navhome:
                        Intent inten = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(inten);
                        return true;
                    case R.id.navOrders:
                        Intent intent = new Intent(getApplicationContext(), OrderActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.navGroups:
                        Intent intent1 = new Intent(getApplicationContext(), GroupActivity.class);
                        startActivity(intent1);
                        return true;
                    case R.id.navStatus:
                        Intent status = new Intent(getApplicationContext(), StatusActivity.class);
                        startActivity(status);
                        return true;
                    case R.id.navContact:
                        Intent intent3 = new Intent(getApplicationContext(), ContactsActivity.class);
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

        Bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < olist.size(); i++) {
                    OrderingModel orderingModel = olist.get(i);

                    String DishName = orderingModel.getDishname();
                    String DishPrice = orderingModel.getDishPrice();
                    String quantity = orderingModel.getQuantity();
                    String Customorder = CustomOrder.getText().toString();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

                    String otime = sdf.format(Calendar.getInstance().getTime());


                    order(DishName,DishPrice,quantity,Customorder,otime);

                }

                clear();
                Intent bills = new Intent(getApplicationContext(), BillActivity.class);
                startActivity(bills);
            }
        });
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Cancel = new Intent(getApplicationContext(), OrderlistActivity.class);
                startActivity(Cancel);
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
                            String DishName = jsonObject.getString("DishName");
                            String DishPrice = jsonObject.getString("DishPrice");
                            String quantity = jsonObject.getString("quantity");


                            olist.add(new OrderingModel(UserName, DishName, DishPrice, quantity));
                            adapter = new OrderingAdapter(OrderingActivity.this, olist);
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

    private void order( final String DishName, final String DishPrice, final String quantity, final String CustomOrder, final String OrderTime){
        String username = getDefaults("username", this);
        String uRl = "https://192.168.1.114/nextorder/addorders.php";
        StringRequest request = new StringRequest(Request.Method.POST, uRl, response -> {

            if (response.equals("Your order is placed")){
                Toast.makeText(OrderingActivity.this, response, Toast.LENGTH_SHORT).show();

                finish();
            }else {
                Toast.makeText(OrderingActivity.this, response, Toast.LENGTH_SHORT).show();
            }

        }, error -> {
            Toast.makeText(OrderingActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<>();
                param.put("DishName",DishName);
                param.put("username",username);
                param.put("DishPrice",DishPrice);
                param.put("quantity",quantity);
                param.put("CustomOrder",CustomOrder);
                param.put("OrderTime",OrderTime);

                return param;

            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(OrderingActivity.this).addToRequestQueue(request);

    }

    public  void clear()
    {
        String username = getDefaults("username", this) ;

        String uRl = "https://192.168.1.114/nextorder/ClearOrderlist.php";
        StringRequest request = new StringRequest(Request.Method.POST, uRl, response -> {

            if (response.equals("Your order is Removed")){
                //Toast.makeText(this, response, Toast.LENGTH_SHORT).show();


            }else {
                Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
            }

        }, error -> {
            Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show();

        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<>();
                param.put("Username", username);
                return param;

            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(this).addToRequestQueue(request);

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
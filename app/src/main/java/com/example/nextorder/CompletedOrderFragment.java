package com.example.nextorder;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
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


public class CompletedOrderFragment extends Fragment {

    ArrayList<StatusModel> OrderStatusList;

    CompletedStatusAdapter adapter;
    RecyclerView CompletedView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_completed_order, container, false);

        CompletedView = view.findViewById(R.id.Completedstatusview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        CompletedView.setLayoutManager(linearLayoutManager);
        OrderStatusList = new ArrayList<StatusModel>();

        getOrderData();

        return view;
    }
    public static String getDefaults(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }

    private void getOrderData() {
        String username = getDefaults("username", getContext());
        String uRl = "https://192.168.1.114/nextorder/Status.php?Username=\"" + username + "\"";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, uRl,
                response -> {
                    try {
                        JSONArray array = new JSONArray(response);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject jsonObject = array.getJSONObject(i);
                            String Oid = jsonObject.getString("Oid");
                            String UserName = jsonObject.getString("Username");
                            String DishName = jsonObject.getString("DishName");
                            String DishPrice = jsonObject.getString("DishPrice");
                            String Quantity = jsonObject.getString("Quantity");
                            String CustomOrder = jsonObject.getString("CustomOrder");
                            String OrderDate = jsonObject.getString("OrderDate");
                            String BeingPrepared = jsonObject.getString("BeingPrepared");
                            String OrderReady = jsonObject.getString("OrderReady");

                            Log.i("OrderReady", OrderReady);
                            Log.i("BeingPrepared", BeingPrepared);
                            if (OrderReady.equals("0")) {
                                OrderStatusList.add(new StatusModel(Oid, UserName, DishName, DishPrice, Quantity, CustomOrder, OrderDate, BeingPrepared, OrderReady));
                                adapter = new CompletedStatusAdapter(getContext(), OrderStatusList);
                                CompletedView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }
}
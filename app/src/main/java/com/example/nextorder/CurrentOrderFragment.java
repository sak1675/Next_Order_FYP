package com.example.nextorder;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

public class CurrentOrderFragment extends Fragment {
    ArrayList<StatusModel> OrderStatusList;

    StatusAdapter adapter;
    RecyclerView CurrentView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_current_order, container, false);


        CurrentView = view.findViewById(R.id.Currentstatusview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        CurrentView.setLayoutManager(linearLayoutManager);
        OrderStatusList = new ArrayList<StatusModel>();


        trustEveryone();
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

                            OrderStatusList.add(new StatusModel(Oid, UserName, DishName, DishPrice, Quantity, CustomOrder, OrderDate, BeingPrepared,OrderReady));
                            adapter = new StatusAdapter(getContext(), OrderStatusList);
                            CurrentView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
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
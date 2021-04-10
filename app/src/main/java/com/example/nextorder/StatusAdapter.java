package com.example.nextorder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.StatusViewHolder>{

    private final Context context;
    private final ArrayList<StatusModel> OrderStatusList;

    public StatusAdapter(Context context, ArrayList<StatusModel> orderStatusList) {
        this.context = context;
        OrderStatusList = orderStatusList;
    }


    @NonNull
    @Override
    public StatusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.current_status_detail,parent,false);
        trustEveryone();
        return new StatusAdapter.StatusViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StatusAdapter.StatusViewHolder holder, int position) {

        StatusModel statusModel = OrderStatusList.get(position);


        if (!(OrderStatusList == null)) {
            holder.DishName.setText("DishName: " + statusModel.getDishName());
            holder.DishPrice.setText("DishPrice: " +statusModel.getDishPrice());
            holder.Quantity.setText(statusModel.getQuantity());
            if(statusModel.getCustomOrder().equals("")){
                holder.CustomOrder.setText("No Custom Order Were Ordered");
            }else {
                holder.CustomOrder.setText("CustomOrder: " + statusModel.getCustomOrder());
            }
            holder.OrderDate.setText("Ordered At " +statusModel.getOrderDate());

            String BeingPreparedCheck = statusModel.getBeingPrepared();
            if (BeingPreparedCheck.equals("0")) {
                holder.BeingPrepared.setText("Your Order Is Being Prepared Please wait");
                holder.Remove.setVisibility(View.INVISIBLE);
                holder.Update.setVisibility(View.INVISIBLE);
            } else {
                holder.BeingPrepared.setText("");


                holder.Quantity.setEnabled(true);

                holder.Remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final String oid = statusModel.getOid();
                        String username = getDefaults("username", context);

                        String uRl = "https://192.168.1.114/nextorder/Removeorders.php";
                        StringRequest request = new StringRequest(Request.Method.POST, uRl, response -> {

                            if (response.equals("Your order is Removed")) {
                                Toast.makeText(context, response, Toast.LENGTH_SHORT).show();


                            } else {
                                Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                            }

                        }, error -> {
                            Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();

                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                HashMap<String, String> param = new HashMap<>();
                                param.put("Oid", oid);
                                param.put("Username", username);
                                return param;

                            }
                        };

                        request.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                        MySingleton.getmInstance(context).addToRequestQueue(request);
                        notifyDataSetChanged();

                        Intent intent = new Intent(context,StatusActivity.class);
                        context.startActivity(intent);
                    }
                });

                holder.Update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String qty = String.valueOf(holder.Quantity.getText());

                        holder.Quantity.setText(qty);
                        altertable(statusModel.getOid() , qty);

                    }
                });
            }
        } else {
            holder.OrderDate.setText("There are no Pending Order. Please Place Order First");
        }

    }

    public void altertable(String Oid, String Quantity){

        String url = "https://192.168.1.114/nextorder/Alterorder.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, response -> {

            if (response.equals("Quantity changed")){
                Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
            }

        }, error -> {
            Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();

        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<>();
                param.put("Oid",Oid);
                param.put("Quantity",Quantity);
                return param;

            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(context).addToRequestQueue(request);

        Intent intent = new Intent(context,StatusActivity.class);
        context.startActivity(intent);
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

    @Override
    public int getItemCount() {
        return OrderStatusList.size();
    }

    public static String getDefaults(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }

    public class StatusViewHolder extends RecyclerView.ViewHolder {
        TextView DishName, DishPrice,CustomOrder,OrderDate,BeingPrepared;
        EditText Quantity;

        Button Update,Remove;
        public StatusViewHolder(@NonNull View itemView) {
            super(itemView);
            DishName = itemView.findViewById(R.id.statusDishName);
            DishPrice = itemView.findViewById(R.id.statusDishPrice);
            Quantity = itemView.findViewById(R.id.statusQuantity);
            CustomOrder = itemView.findViewById(R.id.statusCustomOrder);
            OrderDate = itemView.findViewById(R.id.statusOrderDate);
            BeingPrepared = itemView.findViewById(R.id.statusIdentifier);
            Update = itemView.findViewById(R.id.updateOrdersbtn);
            Remove = itemView.findViewById(R.id.removeOrdersbtn);

        }
    }
}

package com.example.nextorder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderlistAdapter extends RecyclerView.Adapter<OrderlistAdapter.OrderlistViewholder> {

    private final Context context;
    private final ArrayList<OrderlistModel> Orderlist;

    public OrderlistAdapter(Context context, ArrayList<OrderlistModel> orderlist) {
        this.context = context;
        Orderlist = orderlist;
    }


    @NonNull
    @Override
    public OrderlistViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderlistdetail,parent,false);
        return new OrderlistViewholder(v);
    }

    public static String getDefaults(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderlistViewholder holder, int position) {

        final OrderlistModel orderlistModel = Orderlist.get(position);

        holder.DishName.setText(orderlistModel.getDishname());
        holder.DishPrice.setText(orderlistModel.getDishPrice());

        Glide.with(context)
                .asBitmap()
                .load("http://192.168.1.114/nextorder/images/" +orderlistModel.getDishImage())
                .into(holder.DishImage);

        TextView Quantity = holder.quantity;

        holder.quantity.setText(orderlistModel.getQuantity());
        holder.Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qty=Integer.parseInt(Quantity.getText().toString());
                qty++;
                Quantity.setText(Integer.toString(qty));
                orderlistModel.setQuantity(Integer.toString(qty));

                altertable(orderlistModel.getDishname() ,orderlistModel.getDishPrice() ,Integer.toString(qty));
            }
        });

        holder.Sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Quantity.getText().toString().equals("0"))
                {
                    //Log.i("qwer123456","12345"+qty.getText().toString());
                    Toast.makeText(context, "Cannot decrease the Quantity..",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    int qty=Integer.parseInt(Quantity.getText().toString());
                    qty--;
                    Quantity.setText(Integer.toString(qty));
                    orderlistModel.setQuantity(Integer.toString(qty));

                    altertable(orderlistModel.getDishname() ,orderlistModel.getDishPrice() ,Integer.toString(qty));
                }
            }
        });
        final String getDishName = orderlistModel.getDishname();
        holder.Remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = getDefaults("username", context) ;

                String uRl = "https://192.168.1.114/nextorder/Removeorder.php";
                StringRequest request = new StringRequest(Request.Method.POST, uRl, response -> {

                    if (response.equals("Your order is Removed")){
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
                        param.put("DishName",getDishName);
                        param.put("Username", username);
                        return param;

                    }
                };

                request.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                MySingleton.getmInstance(context).addToRequestQueue(request);
                notifyDataSetChanged();


                Intent intent = new Intent(context,OrderlistActivity.class);
                context.startActivity(intent);
            }
        });
    }
    public void altertable(String DishName, String DishPrice,String Quantity){
        String username = getDefaults("username", context) ;

        String url = "https://192.168.1.114/nextorder/Alterorderlist.php";
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
                param.put("DishName",DishName);
                param.put("DishPrice",DishPrice);
                param.put("Quantity",Quantity);
                param.put("Username", username);
                return param;

            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(context).addToRequestQueue(request);
    }

    @Override
    public int getItemCount() {
        return Orderlist.size();
    }

    public class OrderlistViewholder extends RecyclerView.ViewHolder {
        ImageView DishImage;
        TextView DishName, DishPrice;
        EditText quantity;
        ImageButton Add, Sub, Remove;
        public OrderlistViewholder(@NonNull View itemView) {
            super(itemView);



            DishImage = itemView.findViewById(R.id.orderlistDishImage);
            DishName = itemView.findViewById(R.id.orderlistDishName);
            DishPrice = itemView.findViewById(R.id.orderlistDishPrice);
            quantity = itemView.findViewById(R.id.quantity);
            Add = itemView.findViewById(R.id.addquantity);
            Sub = itemView.findViewById(R.id.subquantity);
            Remove = itemView.findViewById(R.id.remove);

        }
    }
}

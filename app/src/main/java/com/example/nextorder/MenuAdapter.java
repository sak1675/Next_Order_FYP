package com.example.nextorder;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<MenuModel> MenuList;


    public MenuAdapter(Context context, ArrayList<MenuModel> menuList) {
        this.context = context;
        MenuList = menuList;
    }

    @NonNull
    @Override
    public MenuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.menudetail,parent,false);
        return new ViewHolder(v);
    }

    public static String getDefaults(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuAdapter.ViewHolder holder, int position) {

        final MenuModel menuModel = MenuList.get(position);

        holder.DishName.setText(menuModel.getDishName());
        holder.DishPrice.setText("Rs." + menuModel.getDishPrice() + " Per Plate");

        Glide.with(context)
                .asBitmap()
               .load("http://192.168.1.114/nextorder/images/" +menuModel.getDishImage())
                .into(holder.DishImage);


        final String getDishName = menuModel.getDishName();
        final String getDishPrice = menuModel.getDishPrice();
        holder.OrderDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = getDefaults("username", context) ;

                String uRl = "https://192.168.1.114/nextorder/addtoorderlist.php";
                StringRequest request = new StringRequest(Request.Method.POST, uRl, response -> {

                    if (response.equals("Your order is added")){
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
                        param.put("DishPrice",getDishPrice);
                        param.put("Username", username);
                        return param;

                    }
                };

                request.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                MySingleton.getmInstance(context).addToRequestQueue(request);
            }
        });


    }

    @Override
    public int getItemCount() {
        return MenuList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView DishName, DishPrice;
        ImageView DishImage;
        Button OrderDish;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //

            DishName = itemView.findViewById(R.id.DishName);
            DishPrice = itemView.findViewById(R.id.DishPrice);
            DishImage = itemView.findViewById(R.id.DishImage);
            OrderDish = itemView.findViewById(R.id.OrderDish);
        }
    }
}

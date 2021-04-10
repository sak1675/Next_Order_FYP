package com.example.nextorder;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrderingAdapter extends RecyclerView.Adapter<OrderingAdapter.OrderinglistViewholder>{

    private final Context context;
    private final ArrayList<OrderingModel> Orderlist;


    public OrderingAdapter(Context context, ArrayList<OrderingModel> orderlist) {
        this.context = context;
        Orderlist = orderlist;
    }

    @NonNull
    @Override
    public OrderingAdapter.OrderinglistViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderinglist,parent,false);
        return new OrderingAdapter.OrderinglistViewholder(v);
    }

    public static String getDefaults(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderingAdapter.OrderinglistViewholder holder, int position) {

        final OrderingModel orderlistModel = Orderlist.get(position);

        holder.DishName.setText(orderlistModel.getDishname());

        String DishPrice;

        DishPrice = Integer.toString(Integer.parseInt(orderlistModel.getDishPrice()) * Integer.parseInt(orderlistModel.getQuantity()));

        holder.DishPrice.setText(DishPrice);
        holder.quantity.setText(orderlistModel.getQuantity());



    }

    int TotalPrice (ArrayList<OrderingModel> Orderlist) {
        int  TotalPrice = 0;
        for (int i = 0; i <= Orderlist.size();i++) {
            int qty=Integer.parseInt(Orderlist.get(i).getQuantity());
            TotalPrice = Integer.parseInt(Orderlist.get(i).getDishPrice()) * qty;
        }
        return TotalPrice;
       // setDefaults("TotalPrice", String.valueOf(TotalPrice), context);
    }


    @Override
    public int getItemCount() {
        return Orderlist.size();
    }

    public class OrderinglistViewholder extends RecyclerView.ViewHolder {
        TextView DishName, DishPrice, quantity;
        public OrderinglistViewholder(@NonNull View itemView) {
            super(itemView);
            DishName = itemView.findViewById(R.id.orderingDishName);
            DishPrice = itemView.findViewById(R.id.orderingDishPrice);
            quantity = itemView.findViewById(R.id.orderingDishquantity);
        }
    }
}

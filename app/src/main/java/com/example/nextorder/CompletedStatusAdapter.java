package com.example.nextorder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CompletedStatusAdapter extends RecyclerView.Adapter<CompletedStatusAdapter.CompletedStatusViewHolder> {

    private final Context context;
    private final ArrayList<StatusModel> OrderStatusList;

    public CompletedStatusAdapter(Context context, ArrayList<StatusModel> orderStatusList) {
        this.context = context;
        OrderStatusList = orderStatusList;
    }

    @NonNull
    @Override
    public CompletedStatusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.completed_status_detail,parent,false);
        return new CompletedStatusAdapter.CompletedStatusViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CompletedStatusAdapter.CompletedStatusViewHolder holder, int position) {

        StatusModel statusModel = OrderStatusList.get(position);

        String OrderReadyCheck = statusModel.getOrderReady();

        if(OrderReadyCheck.equals("0")) {
            holder.DishName.setText("DishName: " + statusModel.getDishName());
            holder.DishPrice.setText("DishPrice: " +statusModel.getDishPrice());
            holder.Quantity.setText("Quantity: " +statusModel.getQuantity());
            if(statusModel.getCustomOrder().equals("")){
                holder.CustomOrder.setText("No Custom Order Were Ordered");
            }else {
                holder.CustomOrder.setText("CustomOrder: " + statusModel.getCustomOrder());
            }
            holder.OrderDate.setText("Ordered At " +statusModel.getOrderDate());
        } else
        {

            
        }
    }

    @Override
    public int getItemCount() {
        return OrderStatusList.size();
    }

    public class CompletedStatusViewHolder extends RecyclerView.ViewHolder {

        TextView DishName, DishPrice, Quantity,CustomOrder,OrderDate;
        public CompletedStatusViewHolder(@NonNull View itemView) {
            super(itemView);

            DishName = itemView.findViewById(R.id.completedDishName);
            DishPrice = itemView.findViewById(R.id.completedDishPrice);
            Quantity = itemView.findViewById(R.id.completedQuantity);
            CustomOrder = itemView.findViewById(R.id.completedCustomOrder);
            OrderDate = itemView.findViewById(R.id.completedOrderDate);
        }
    }
}

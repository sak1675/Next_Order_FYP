package com.example.nextorder;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.orderViewHolder> {

    private final Context context;
    private final ArrayList<OrderModel> orderlist;


    public OrderAdapter(Context context, ArrayList<OrderModel> orderlist) {
        this.context = context;
        this.orderlist = orderlist;
    }

    @NonNull
    @Override
    public orderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderdetail,parent,false);
        return new orderViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull orderViewHolder holder, int position) {

        final OrderModel orderModel = orderlist.get(position);

        holder.Orderid.setText(String.valueOf(position + 1));
        holder.OrderName.setText( "Order Name:" +orderModel.getOrderName() + "'s Order");
        holder.OrderTime.setText("Order Time:" + orderModel.getOrderTime());


        //Very important code for some problems
        if (holder.timer != null) {
            holder.timer.cancel();
        }


        long Sessionstart = System.currentTimeMillis();

            SimpleDateFormat DF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date event_date = DF.parse(orderModel.getOrderTime());
            long ordertimeinmills = event_date.getTime();
            long subtimer = Sessionstart - ordertimeinmills;



            long timer = Long.parseLong(String.valueOf(((position + 1) * 15)));


            timer = timer * 60 * 1000 - subtimer;

            if (timer < 0){

                holder.OrderReadyTime.setText("Your Order is almost Complete");
            }
            else{

                holder.timer = new CountDownTimer(timer, 1000) {
                    public void onTick(long millisUntilFinished) {
                        long hour = TimeUnit.MILLISECONDS.toHours(millisUntilFinished);
                        millisUntilFinished -= TimeUnit.HOURS.toMillis(hour);

                        long minute = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished);
                        millisUntilFinished -= TimeUnit.MINUTES.toMillis(minute);

                        long second = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished);
                        holder.OrderReadyTime.setText("Order Ready: " + hour + ":" + minute + ":" + second);

                    }

                    public void onFinish() {
                        holder.OrderReadyTime.setText("Your Order is almost Complete");
                    }
                }.start();
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }




    }


    @Override
    public int getItemCount() {
        return orderlist.size();
    }





        public class orderViewHolder extends RecyclerView.ViewHolder {
        TextView Orderid, OrderName, OrderReadyTime, OrderTime, BillAmount;
        CountDownTimer timer;
        public orderViewHolder(@NonNull View itemView) {
            super(itemView);

            Orderid = itemView.findViewById(R.id.orderid);
            OrderName = itemView.findViewById(R.id.ordername);
            OrderTime = itemView.findViewById(R.id.orderTime);
            OrderReadyTime = itemView.findViewById(R.id.orderReadyTime);

        }
    }
}


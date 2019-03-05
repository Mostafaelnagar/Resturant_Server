package com.example.mostafa.eatitserver.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mostafa.eatitserver.Model.Order;
import com.example.mostafa.eatitserver.R;

import java.util.List;

/**
 * Created by mostafa on 1/7/2018.
 */

class myViewHolder extends RecyclerView.ViewHolder {
    TextView name, quantity, price, discount;

    public myViewHolder(View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.product_Name);
        quantity = itemView.findViewById(R.id.product_Quntity);
        price = itemView.findViewById(R.id.product_price);
        discount = itemView.findViewById(R.id.product_Discount);
    }
}


public class orderDetailAdapter extends RecyclerView.Adapter<myViewHolder> {
    List<Order> orderList;

    public orderDetailAdapter(List<Order> orderList) {
        this.orderList = orderList;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_detail, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.name.setText(String.format("Name : %s",order.getProductName()));
        holder.quantity.setText(String.format("Quantity : %s",order.getQuantity()));
        holder.price.setText(String.format("Price : %s",order.getPrice()));
        holder.discount.setText(String.format("Discount : %s",order.getDiscount()));

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}

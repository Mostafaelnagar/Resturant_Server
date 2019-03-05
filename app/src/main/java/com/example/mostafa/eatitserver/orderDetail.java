package com.example.mostafa.eatitserver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mostafa.eatitserver.Common.Common;
import com.example.mostafa.eatitserver.ViewHolder.orderDetailAdapter;

public class orderDetail extends AppCompatActivity {
    TextView order_id, order_Phone, order_Total, order_Address, order_Comment;
    RecyclerView lstFoods;
    RecyclerView.LayoutManager layoutManager;
    String order_id_value = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        order_id = (TextView) findViewById(R.id.order_id);
        order_Phone = (TextView) findViewById(R.id.order_Phone);
        order_Total = (TextView) findViewById(R.id.order_Total);
        order_Address = (TextView) findViewById(R.id.order_Address);
        order_Comment = (TextView) findViewById(R.id.order_Comment);
        lstFoods = (RecyclerView) findViewById(R.id.lstFoods);
        lstFoods.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        lstFoods.setLayoutManager(layoutManager);
        if (getIntent() != null) {
            order_id_value = getIntent().getStringExtra("order_Id");
        }
//set Valuse
        order_id.setText(String.format("ID : %s",order_id_value));
        order_Phone.setText(String.format("Phone : %s",Common.currentRequset.getPhone()));
        order_Total.setText(String.format("Total : %s",Common.currentRequset.getTotal()));
        order_Address.setText(String.format("Address : %s",Common.currentRequset.getAddress()));
        order_Comment.setText(String.format("Comment : %s",Common.currentRequset.getComment()));
        orderDetailAdapter adapter = new orderDetailAdapter(Common.currentRequset.getFoods());
        adapter.notifyDataSetChanged();
        lstFoods.setAdapter(adapter);

    }
}

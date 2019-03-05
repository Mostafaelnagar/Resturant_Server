package com.example.mostafa.eatitserver;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.mostafa.eatitserver.Common.Common;
import com.example.mostafa.eatitserver.Interface.ItemClicklistener;
import com.example.mostafa.eatitserver.Model.MyResponse;
import com.example.mostafa.eatitserver.Model.Notification;
import com.example.mostafa.eatitserver.Model.Request;
import com.example.mostafa.eatitserver.Model.Sender;
import com.example.mostafa.eatitserver.Model.Token;
import com.example.mostafa.eatitserver.Remote.APIService;
import com.example.mostafa.eatitserver.ViewHolder.OrderViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jaredrummler.materialspinner.MaterialSpinner;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderStatus extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Request, OrderViewHolder> adapter;
    FirebaseDatabase db;
    DatabaseReference request;
    MaterialSpinner spinner;
    APIService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);
        mService = Common.getFCMClient();

        db = FirebaseDatabase.getInstance();
        request = db.getReference("Requests");
        recyclerView = (RecyclerView) findViewById(R.id.listOrders);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        if (Common.isConnected(getBaseContext()))
            loadOrders();

        else {
            Toast.makeText(OrderStatus.this, "Please Check Your Internet", Toast.LENGTH_SHORT).show();
            return;
        }

    }

    private void loadOrders() {
        adapter = new FirebaseRecyclerAdapter<Request, OrderViewHolder>(
                Request.class, R.layout.order_layout, OrderViewHolder.class, request

        ) {
            @Override
            protected void populateViewHolder(OrderViewHolder viewHolder, final Request model, final int position) {
                viewHolder.txtOrderId.setText(String.format("ID : %s", adapter.getRef(position).getKey()));
                viewHolder.txtOrderStatus.setText(String.format("Status : %s", Common.convertCodeToStatus(model.getStatus())));
                viewHolder.txtOrderPhone.setText(String.format("Phone : %s", model.getPhone()));
                viewHolder.txtOrderAddress.setText(String.format("Address : %s", model.getAddress()));
                viewHolder.btnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showUpdateDialog(adapter.getRef(position).getKey(), adapter.getItem(position));

                    }
                });
                viewHolder.btnRemove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        deleteOrder(adapter.getRef(position).getKey());

                    }
                });
                viewHolder.btnDetail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(OrderStatus.this, orderDetail.class);
                        intent.putExtra("order_Id", adapter.getRef(position).getKey());
                        Common.currentRequset = model;
                        //Toast.makeText(OrderStatus.this, ""+Common.currentRequset.getFoods(), Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }
                });
            }
        };
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }


    private void deleteOrder(String key) {
        request.child(key).removeValue();
        adapter.notifyDataSetChanged();

    }

    private void showUpdateDialog(final String key, final Request item) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(OrderStatus.this);
        alertDialog.setTitle("Update Request");
        alertDialog.setMessage("Please Status");
        LayoutInflater inflater = this.getLayoutInflater();
        View updateStatus = inflater.inflate(R.layout.order_update_layout, null);
        spinner = updateStatus.findViewById(R.id.statusSpinner);
        spinner.setItems("Placed", "On My Way", "Shipped");

        alertDialog.setView(updateStatus);

        alertDialog.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                //update Info
                item.setStatus(String.valueOf(spinner.getSelectedIndex()));
                request.child(key).setValue(item);
                adapter.notifyDataSetChanged();
                sendOrderStatusToUser(key, item);

            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialog.show();
    }

    private void sendOrderStatusToUser(final String key, final Request item) {
        DatabaseReference reference = db.getReference("Tokens");
        reference.orderByKey().equalTo(item.getPhone())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot PostSnapshot : dataSnapshot.getChildren()) {
                            Token ServerToken = PostSnapshot.getValue(Token.class);
                            Notification notification = new Notification("Eat", "Your order" + key + "Was Updated");
                            Sender content = new Sender(ServerToken.getToken(), notification);
                            mService.sendNotification(content).enqueue(new Callback<MyResponse>() {
                                @Override
                                public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                                    if (response.code() == 200) {
                                        if (response.body().success == 1) {
                                            Toast.makeText(OrderStatus.this, "Thank you ,Order was updated", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(OrderStatus.this, "Order was updated but failed to send notification", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<MyResponse> call, Throwable t) {
                                    Log.i("ERROR", t.getMessage());
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
}

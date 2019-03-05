package com.example.mostafa.eatitserver.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mostafa.eatitserver.Common.Common;
import com.example.mostafa.eatitserver.Interface.ItemClicklistener;
import com.example.mostafa.eatitserver.R;


/**
 * Created by mostafa on 10/2/2017.
 */

public class OrderViewHolder extends RecyclerView.ViewHolder {
    public TextView txtOrderId, txtOrderStatus,txtOrderPhone,txtOrderAddress;
    public Button btnEdit,btnRemove,btnDetail;



    public OrderViewHolder(View itemView) {
        super(itemView);
        txtOrderId = (TextView) itemView.findViewById(R.id.Order_id);

        txtOrderStatus = (TextView) itemView.findViewById(R.id.Order_Status);
        txtOrderPhone = (TextView) itemView.findViewById(R.id.Order_Phone);
        txtOrderAddress = (TextView) itemView.findViewById(R.id.Order_Address);
        btnEdit = (Button) itemView.findViewById(R.id.btnEdit);
        btnRemove = (Button) itemView.findViewById(R.id.btnRemove);
        btnDetail = (Button) itemView.findViewById(R.id.btnDetail);

    }




}

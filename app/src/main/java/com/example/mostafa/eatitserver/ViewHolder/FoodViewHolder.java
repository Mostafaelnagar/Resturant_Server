package com.example.mostafa.eatitserver.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mostafa.eatitserver.Common.Common;
import com.example.mostafa.eatitserver.Interface.ItemClicklistener;
import com.example.mostafa.eatitserver.R;

/**
 * Created by mostafa on 9/29/2017.
 */

public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnCreateContextMenuListener{
    public TextView food_Name;
    public ImageView food_Image;
    private ItemClicklistener itemClicklistener;

    public FoodViewHolder(View itemView) {
        super(itemView);
        food_Name = (TextView) itemView.findViewById(R.id.food_name);
        food_Image = (ImageView) itemView.findViewById(R.id.food_image);
        itemView.setOnCreateContextMenuListener(this);
        itemView.setOnClickListener(this);

    }

    public void setItemClicklistener(ItemClicklistener itemClicklistener) {
        this.itemClicklistener = itemClicklistener;
    }

    @Override
    public void onClick(View view) {
        itemClicklistener.onClick(view, getAdapterPosition(), false);
    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        contextMenu.setHeaderTitle("choose action");
        contextMenu.add(0,0,getAdapterPosition(), Common.Update);
        contextMenu.add(0,1,getAdapterPosition(), Common.Delete);
    }
}

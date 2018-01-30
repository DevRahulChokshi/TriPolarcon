package com.example.ebc003.tripolarcon.adapter;


import android.content.Context;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.ebc003.tripolarcon.R;
import com.example.ebc003.tripolarcon.app.MyAdapterItem;
import com.example.ebc003.tripolarcon.model.NavigationDrawerItems;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by EBC003 on 12/8/2017.
 */

public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.MyViewHolder>{

    private List<NavigationDrawerItems> dataList= Collections.emptyList ();
    private Context context;
    private LayoutInflater layoutInflater;
    MyAdapterItem myAdapterItem;

    public NavigationDrawerAdapter (Context context,List<NavigationDrawerItems> data,MyAdapterItem myAdapterItem) {
        this.context=context;
        layoutInflater=LayoutInflater.from (context);
        this.dataList=data;
        this.myAdapterItem=myAdapterItem;
    }

    @Override
    public MyViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View view=layoutInflater.inflate (R.layout.nav_drawer_list_item,parent,false);

        MyViewHolder myViewHolder=new MyViewHolder (view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder (final MyViewHolder holder, int position) {
        NavigationDrawerItems navigationDrawerItems = dataList.get (position);
        holder.imgTitle.setText (navigationDrawerItems.getTitle ());
        holder.imgId.setImageResource (navigationDrawerItems.getImageId ());

        holder.itemView.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                String item= holder.imgTitle.getText ().toString ();
                myAdapterItem.getItem (item);

//                v.setBackgroundColor (context.getResources ().getColor (R.color.grey_300));
//                holder.imgTitle.setTextColor (context.getResources ().getColor (R.color.primaryColor));
            }
        });
    }

    @Override
    public int getItemCount () {
        return dataList.size ();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title)
        TextView imgTitle;
        @BindView(R.id.imgIcon)
        ImageView imgId;

        public MyViewHolder (View itemView) {
            super (itemView);
            ButterKnife.bind (this,itemView);
        }
    }
}

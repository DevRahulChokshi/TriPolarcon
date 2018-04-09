package com.example.ebc003.tripolarcon.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ebc003.tripolarcon.R;

import java.util.ArrayList;

/**
 * Created by EBC003 on 3/13/2018.
 */

public class CustomGridViewAdapter extends BaseAdapter {

    private Context mContext;
    private final ArrayList<String> gridViewStringCounterItem;
    private final String[] gridViewStringTitleItem;
    private final int[] gridViewImageId;

    public CustomGridViewAdapter (Context context, ArrayList<String> gridViewStringCounter, String[] gridViewString, int[] gridViewImageId) {
        mContext = context;
        this.gridViewImageId = gridViewImageId;
        this.gridViewStringCounterItem = gridViewStringCounter;
        this.gridViewStringTitleItem = gridViewString;
    }

    @Override
    public int getCount() {
        return gridViewStringCounterItem.size ();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View gridViewAndroid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            gridViewAndroid = new View(mContext);
            gridViewAndroid = inflater.inflate(R.layout.customize_gridview, null);

            TextView txtCounterItem = gridViewAndroid.findViewById(R.id.txtCounterItem);
            TextView txtTitleItem = gridViewAndroid.findViewById(R.id.txtTitleItem);
            TextView txtDetailsItem = gridViewAndroid.findViewById(R.id.txtDetailsItem);
            ImageView imageViewAndroid = gridViewAndroid.findViewById(R.id.imgItem);

            txtCounterItem.setText(gridViewStringCounterItem.get (i));
            txtTitleItem.setText(gridViewStringTitleItem[i]);
            imageViewAndroid.setImageResource(gridViewImageId[i]);

            Typeface customFontBold= Typeface.createFromAsset (mContext.getAssets (),"fonts/OpenSansCondensed-Bold.ttf");
            Typeface customFontLight= Typeface.createFromAsset (mContext.getAssets (),"fonts/OpenSansCondensed-Light.ttf");
            txtCounterItem.setTypeface (customFontBold);
            txtDetailsItem.setTypeface (customFontBold);
            txtTitleItem.setTypeface (customFontLight);

        } else {
            gridViewAndroid = convertView;
        }

        return gridViewAndroid;
    }
}
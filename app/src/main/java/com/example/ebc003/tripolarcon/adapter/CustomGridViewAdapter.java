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

/**
 * Created by EBC003 on 3/13/2018.
 */

public class CustomGridViewAdapter extends BaseAdapter {

    private Context mContext;
    private final String[] gridViewStringCounterItem;
    private final String[] gridViewStringTitleItem;
    private final int[] gridViewImageId;

    public CustomGridViewAdapter (Context context, String[] gridViewStringCounter, String[] gridViewString, int[] gridViewImageId) {
        mContext = context;
        this.gridViewImageId = gridViewImageId;
        this.gridViewStringCounterItem = gridViewStringCounter;
        this.gridViewStringTitleItem = gridViewString;
    }

    @Override
    public int getCount() {
        return gridViewStringCounterItem.length;
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

            TextView txtCounterItem = (TextView) gridViewAndroid.findViewById(R.id.txtCounterItem);
            TextView txtTitleItem = (TextView) gridViewAndroid.findViewById(R.id.txtTitleItem);
            TextView txtDetailsItem = (TextView) gridViewAndroid.findViewById(R.id.txtDetailsItem);
            ImageView imageViewAndroid = (ImageView) gridViewAndroid.findViewById(R.id.imgItem);

            txtCounterItem.setText(gridViewStringCounterItem[i]);
            txtTitleItem.setText(gridViewStringTitleItem[i]);
            imageViewAndroid.setImageResource(gridViewImageId[i]);

            Typeface customFontBold= Typeface.createFromAsset (mContext.getAssets (),"fonts/OpenSansCondensed-Bold.ttf");
            Typeface customFontLight= Typeface.createFromAsset (mContext.getAssets (),"fonts/OpenSansCondensed-Light.ttf");
            txtCounterItem.setTypeface (customFontBold);
            txtDetailsItem.setTypeface (customFontBold);
            txtTitleItem.setTypeface (customFontLight);

        } else {
            gridViewAndroid = (View) convertView;
        }

        return gridViewAndroid;
    }
}
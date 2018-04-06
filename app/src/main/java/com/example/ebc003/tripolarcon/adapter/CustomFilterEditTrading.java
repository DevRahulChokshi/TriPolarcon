package com.example.ebc003.tripolarcon.adapter;

import android.widget.Filter;

import com.example.ebc003.tripolarcon.model.LeadListData;

import java.util.ArrayList;

public class CustomFilterEditTrading extends Filter {

    LeadEditTradingAdapter adapter;
    ArrayList<LeadListData> filterList;


    public CustomFilterEditTrading (ArrayList<LeadListData> filterList, LeadEditTradingAdapter adapter)
    {
        this.adapter=adapter;
        this.filterList=filterList;

    }

    //FILTERING OCURS
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results=new FilterResults ();

        //CHECK CONSTRAINT VALIDITY
        if(constraint != null && constraint.length() > 0)
        {
            //CHANGE TO UPPER
            constraint=constraint.toString().toUpperCase();
            //STORE OUR FILTERED PLAYERS
            ArrayList<LeadListData> filteredPlayers=new ArrayList<> ();

            for (int i=0;i<filterList.size();i++)
            {
                //CHECK
                if(filterList.get(i).getTxtLeadTitle ().toUpperCase().contains(constraint))
                {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredPlayers.add(filterList.get(i));
                }
            }

            results.count=filteredPlayers.size();
            results.values=filteredPlayers;
        }else
        {
            results.count=filterList.size();
            results.values=filterList;

        }


        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

        adapter.players= (ArrayList<LeadListData>) results.values;

        //REFRESH
        adapter.notifyDataSetChanged();
    }
}
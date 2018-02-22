package com.example.ebc003.tripolarcon.model;
import com.example.ebc003.tripolarcon.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by EBC003 on 12/9/2017.
 */

public class NavigationDrawerItems {

    private String title;
    private int imageId;

    public String getTitle () {
        return title;
    }

    public void setTitle (String title) {
        this.title = title;
    }

    public int getImageId () {
        return imageId;
    }

    public void setImageId (int imageId) {
        this.imageId = imageId;
    }

    public static List<NavigationDrawerItems> getData(){
        List<NavigationDrawerItems> listData=new ArrayList<> ();

        int[] imgIds=getImages ();
        String[] strTitle=getTitles ();

        for (int i=0;i<strTitle.length;i++){
            NavigationDrawerItems navItems=new NavigationDrawerItems ();
            navItems.setTitle (strTitle[i]);
            navItems.setImageId (imgIds[i]);
            listData.add (navItems);
        }
        return listData;
    }


    private static int[] getImages(){

        return new int[]{
                R.drawable.ic_dashboard_black,
                R.drawable.ic_list_black,
                R.drawable.ic_add_black,
                R.drawable.ic_search_black,
                R.drawable.ic_chat_bubble_black
        };
    }

    private static String[] getTitles(){
        return new String[]{
            "Dashboard","Enquiry List","New Enquiry","Daily plan","Notification"
        };
    }
}

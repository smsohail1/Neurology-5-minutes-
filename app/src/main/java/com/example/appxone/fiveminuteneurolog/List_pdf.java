package com.example.appxone.fiveminuteneurolog;

/**
 * Created by APPXONE on 3/17/2016.
 */


import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class List_pdf extends BaseAdapter {

    // ArrayList<list_nodel> mStringFilterList;


    private Context context;


    private ArrayList<list_nodel> navDrawerItemssa;

    ArrayList<list_nodel> mStringFilterList;
    private ArrayList<list_nodel> arraylist;

    Typeface typeface;
    public Context cc;


    ArrayList<String> fav_list;
    Cursor c;


    public List_pdf(Context context, ArrayList<list_nodel> navDrawerItemsa) {
        this.context = context;
        this.navDrawerItemssa = navDrawerItemsa;
        this.arraylist = new ArrayList<list_nodel>();
        this.arraylist.addAll(navDrawerItemsa);
        // fav_list = new ArrayList<String>();


        Log.e("cunstructor", "cunstructor");
        //  mStringFilterList = navDrawerItemsa;
    }

    @Override
    public int getCount() {
        return navDrawerItemssa.size();
    }

    @Override
    public Object getItem(int position) {
        return navDrawerItemssa.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

//    public class ViewHolder {
//        public ImageView im;
//    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {

            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.viewpager_row, null);

            typeface = Typeface.createFromAsset(this.context.getAssets(), "fonts/Raleway-Bold.ttf");
        }


        //ImageView imgIcon = (ImageView) convertView.findViewById(R.id.calculator_img);
        TextView txtTitleindex = (TextView) convertView.findViewById(R.id.calculator_title);
        TextView page_no = (TextView) convertView.findViewById(R.id.pag);


        //txtTitleindex.setTypeface(typeface);
        // imgIcon.setImageResource(navDrawerItemssa.get(position).getImg());
        if (position == 2) {
            txtTitleindex.setText(navDrawerItemssa.get(position).getCalcuator_Title());
        } else if (position == 20) {
            txtTitleindex.setText(navDrawerItemssa.get(position).getCalcuator_Title());
        } else if (position == 32) {
            txtTitleindex.setText(navDrawerItemssa.get(position).getCalcuator_Title());
        } else {
            txtTitleindex.setText(navDrawerItemssa.get(position).getCalcuator_Title());
            txtTitleindex.setTypeface(typeface);
            page_no.setText(navDrawerItemssa.get(position).getOlder_page() + "");
            page_no.setTypeface(typeface);
        }
        // page_no.setText(navDrawerItemssa.get(position).getPage_no());

        return convertView;
    }


    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        navDrawerItemssa.clear();
        if (charText.length() == 0) {

            navDrawerItemssa.addAll(arraylist);
        } else {
            for (list_nodel wp : arraylist) {
                if (wp.getCalcuator_Title().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    navDrawerItemssa.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }


}

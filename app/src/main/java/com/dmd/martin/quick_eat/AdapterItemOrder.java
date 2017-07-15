package com.dmd.martin.quick_eat;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import entity.Food;

/**
 * Created by martin on 3/07/17.
 */

public class AdapterItemOrder extends BaseAdapter {

    protected Activity activity;
    protected ArrayList<Food> items;

    public AdapterItemOrder(Activity activity, ArrayList<Food> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View viewTemp = view;
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewTemp = layoutInflater.inflate(R.layout.activity_item_order, viewGroup, false);
        }
        Food food = items.get(i);
        TextView textViewNameOfPlate = (TextView) viewTemp.findViewById(R.id.textViewNameOrder);
        textViewNameOfPlate.setText(food.getName());
        TextView textViewPrice = (TextView) viewTemp.findViewById(R.id.textViewPriceOrder);
        textViewPrice.setText("$ " + String.valueOf(food.getPrice()));
        return viewTemp;
    }
}

package com.dmd.martin.quick_eat;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.util.ArrayList;

import entity.Food;

/**
 * Created by martin on 2/07/17.
 */

public class AdapterItemMenu extends BaseAdapter {

    protected Activity activity;
    protected ArrayList<Food> items;


    public AdapterItemMenu (Activity activity, ArrayList<Food> items) {
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
            viewTemp = layoutInflater.inflate(R.layout.activity_item_menu, viewGroup, false);
        }
        final Food food = items.get(i);
        TextView textViewNameOfPlate = (TextView) viewTemp.findViewById(R.id.textViewNameMenu);
        textViewNameOfPlate.setText(food.getName());
        TextView textViewDescription = (TextView) viewTemp.findViewById(R.id.textViewFeaturesMenu);
        textViewDescription.setText(food.getFeatures());
        TextView textViewPrice = (TextView) viewTemp.findViewById(R.id.textViewPriceMenu);
        textViewPrice.setText("$ " + String.valueOf(food.getPrice()));
        TextView textViewTime =  (TextView) viewTemp.findViewById(R.id.textViewTimeMenu);
        textViewTime.setText(String.valueOf(food.getPreparationTime()) + " Min");
        Button buttonAskPlate = viewTemp.findViewById(R.id.buttonAskPlateMenu);
        buttonAskPlate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MenuActivity.orderList.add(food);
                MenuActivity.orderAdapter.notifyDataSetChanged();
            }
        });
        return viewTemp;
    }
}

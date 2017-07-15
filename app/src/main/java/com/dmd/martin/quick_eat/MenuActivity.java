package com.dmd.martin.quick_eat;

import android.app.Activity;
import android.app.ActivityGroup;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import java.util.ArrayList;

import connection.RestOrderFood;
import connection.RestQuantityFood;
import entity.Category;
import entity.Food;
import entity.QuantityFood;

public class MenuActivity extends ActivityGroup {
    private TabHost tabHost;
    private Context context = this;
    private ArrayList<Food> tmpFoodList;
    public static ArrayList<Food> orderList;
    public static ArrayList<QuantityFood> quantityFoodsList;
    public static AdapterItemOrder orderAdapter;
    private ListView listViewOrder;
    private RestQuantityFood restQuantityFood;
    private RestOrderFood restOrderFood;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        quantityFoodsList = new ArrayList<>();
        orderList = new ArrayList<>();
        startListViewOrder();
        startTabHostMenu();
    }

    private void startListViewOrder() {
        listViewOrder  = findViewById(R.id.listViewOrderMenu);
        orderAdapter = new AdapterItemOrder(this, orderList);
        listViewOrder.setAdapter(orderAdapter);
        listViewOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                orderList.remove(i);
                orderAdapter.notifyDataSetChanged();
            }
        });
    }

    private void startTabHostMenu(){
        tabHost = (TabHost) findViewById(R.id.tabHostMenu);
        tabHost.setup(this.getLocalActivityManager());
        for (final Category category: WelcomeActivity.restCategory.getCategoryArrayList()){
            TabHost.TabSpec tabTemp = tabHost.newTabSpec("tmp"+ category.getName());
            tabTemp.setIndicator(category.getName());
            tabTemp.setContent(new TabHost.TabContentFactory() {
                @Override
                public View createTabContent(String s) {
                    ListView list  = new ListView(context);
                    initializeTmpFood(category.getId());
                    list.setAdapter(new AdapterItemMenu((Activity) context, tmpFoodList));
                    return list;
                }

                private void initializeTmpFood(Integer id) {
                    tmpFoodList = new ArrayList<>();
                    ArrayList<Food> Test = WelcomeActivity.restFood.getFoodArrayList();
                    for (Food food: WelcomeActivity.restFood.getFoodArrayList()){
                        if(food.getCategoryId() == id)
                            tmpFoodList.add(food);
                    }

                }
            });
            tabHost.addTab(tabTemp);
        }
    }

    public void confirmOrder(View view){
        countFoods();
        sendingQuantityOrder();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Toast.makeText(this, "Su pedido se ha confirmado", Toast.LENGTH_LONG).show();
    }

    private void countFoods(){
        int tempId = 0;
        int count = 0;
        ArrayList<Food> tempOrderList;
        while (orderList.size() != 0){
            tempOrderList = new ArrayList<>();
            tempId = orderList.get(0).getId();
            for (Food food: orderList){
                if(food.getId() == tempId)
                    count++;
                else
                    tempOrderList.add(food);
            }
            quantityFoodsList.add(new QuantityFood(count, tempId));
            count = 0;
            orderList = tempOrderList;
        }

    }

    private void sendingQuantityOrder(){
        for (QuantityFood quantityFood: quantityFoodsList) {
            restQuantityFood = new RestQuantityFood();
            restQuantityFood.setURl(WelcomeActivity.hostHeroku + "crearComidaCantidad/?cantidad=" +
                    quantityFood.getQuantity() + "&idComida=" + quantityFood.getFood_id());
            restQuantityFood.execute();
        }
    }


    public void sendOrder(View view){
        sendOrderLinkFood();
        Toast.makeText(this, "Su pedido se ha enviado", Toast.LENGTH_LONG).show();
    }

    private void sendOrderLinkFood() {
        for (Integer intTmp: WelcomeActivity.idsQuantityFood){
            restOrderFood = new RestOrderFood();
            restOrderFood.setURl(WelcomeActivity.hostHeroku + "crearPedidoCC/?pedidoId=" + WelcomeActivity.idOrder + "&comidaCId=" + intTmp);
            restOrderFood.execute();
        }
    }
}

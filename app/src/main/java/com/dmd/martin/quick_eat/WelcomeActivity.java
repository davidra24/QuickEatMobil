package com.dmd.martin.quick_eat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import connection.RestCategory;
import connection.RestFood;
import connection.RestOrder;

public class WelcomeActivity extends AppCompatActivity {
    public static final String hostHeroku = "https://quick-eat1.herokuapp.com/";
    //public static final String hostHeroku = "http://192.168.1.18:3000/";
    public static RestCategory restCategory;
    public static RestFood restFood;
    private RestOrder restOrder;
    private EditText editTextNameWelcome;
    public static int idOrder = 0;
    public static ArrayList<Integer> idsQuantityFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        idsQuantityFood = new ArrayList<>();
        if(restCategory == null && restFood == null){
            loadCategories();
            loadFood();
        }
    }

    private void loadCategories(){
        restCategory = new RestCategory(this);
        restCategory.setURl(WelcomeActivity.hostHeroku + "tablaCategorias");
        restCategory.execute();
    }

    private void loadFood(){
        restFood = new RestFood(this);
        restFood.setURl(WelcomeActivity.hostHeroku + "tablaComidas");
        restFood.execute();
    }

    public void showMenu(View view){
        if(validateNameText()) {
            createOrder();
            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
        }else
            Toast.makeText(this, "Ingrese su nombre por favor", Toast.LENGTH_SHORT).show();
    }

    private boolean validateNameText() {
        editTextNameWelcome = (EditText) findViewById(R.id.editTextNameWelcome);
        if(!editTextNameWelcome.getText().toString().isEmpty())
            return true;
        return false;
    }

    private void createOrder(){
        restOrder = new RestOrder(this);
        restOrder.setURl(WelcomeActivity.hostHeroku + "crearPedido/?entregado=false&nombre=%27"+
                editTextNameWelcome.getText() + "%27&cobrado=false&numeroMesa=1");
        restOrder.execute();
    }
}

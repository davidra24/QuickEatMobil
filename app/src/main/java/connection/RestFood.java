package connection;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import entity.Food;

/**
 * Created by martin on 2/07/17.
 */

public class RestFood extends AsyncTask<String, Void, String> {
    public URL url;
    private HttpURLConnection httpURLConnection;
    private ArrayList<Food> foodArrayList;
    private AppCompatActivity activity;
    private ProgressDialog progressDialog;

    public RestFood(AppCompatActivity activity){
        this.foodArrayList = new ArrayList<>();
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Cargando platos...");
        progressDialog.show();
    }

    public void setURl(String url){
        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String doInBackground(String... strings) {
        StringBuilder result = new StringBuilder();
        BufferedReader bufferedReader = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(10000);
            bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line).append("\n");
            }
        } catch (IOException e) {
            return "Error";
        } finally {
            httpURLConnection.disconnect();
            if(bufferedReader != null)
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return result.toString();
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        JSONObject jsonObj = null;
        try {
            jsonObj = new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        loadFoodList(jsonObj);
        if (progressDialog != null)
            progressDialog.dismiss();
    }


    private void loadFoodList(JSONObject jsonObj){
        JSONArray foodList = jsonObj.optJSONArray("Comidas");
        for (int i = 0; i < foodList.length(); i++) {
            try {
                addNewFood(foodList, i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void addNewFood(JSONArray foodList, int i) throws JSONException {
        JSONObject food = foodList.getJSONObject(i);
        foodArrayList.add(new Food(food.getInt("id"), food.getString("caracteristicas"),
                food.getString("nombre"), food.getBoolean("disponibilidad"),
                food.getInt("precio_base"), food.getInt("tiempo_preparacion"),
                food.getInt("categoria_id")));
    }

    public ArrayList<Food> getFoodArrayList() {
        return foodArrayList;
    }
}

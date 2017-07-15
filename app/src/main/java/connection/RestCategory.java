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

import entity.Category;
import entity.Food;

/**
 * Created by martin on 1/07/17.
 */

public class RestCategory extends AsyncTask <String, Void, String>{
    public  URL url;
    private HttpURLConnection httpURLConnection;
    private ArrayList<Category> categoryArrayList;
    private AppCompatActivity activity;
    private ProgressDialog progressDialog;

    public RestCategory(AppCompatActivity activity){
        this.categoryArrayList = new ArrayList<>();
        this.activity = activity;
    }

    public void setURl(String url){
        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Cargando categorias...");
        progressDialog.show();
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
            loadCategoriesList(jsonObj);
            if (progressDialog != null)
                progressDialog.dismiss();
    }

    private void loadCategoriesList(JSONObject jsonObj){
        JSONArray categoriesList = jsonObj.optJSONArray("Categorias");
        for (int i = 0; i < categoriesList.length(); i++) {
            try {
                addNewCategory(categoriesList, i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void addNewCategory(JSONArray categoriesList, int i) throws JSONException {
        JSONObject category = categoriesList.getJSONObject(i);
        categoryArrayList.add(new Category(category.getInt("id"),
                category.getString("nombre")));
    }


    public ArrayList<Category> getCategoryArrayList() {
        return categoryArrayList;
    }
}

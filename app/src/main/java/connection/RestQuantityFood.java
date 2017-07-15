package connection;

import android.os.AsyncTask;

import com.dmd.martin.quick_eat.WelcomeActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by martin on 5/07/17.
 */

public class RestQuantityFood extends AsyncTask<String, Void, String> {
    public URL url;
    private HttpURLConnection httpURLConnection;

    public RestQuantityFood (){
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
            JSONArray idOrderList = jsonObj.optJSONArray("ComidasCI");
            for (int i = 0; i < idOrderList.length(); i++) {
                JSONObject id = idOrderList.getJSONObject(i);
                try {
                    WelcomeActivity.idsQuantityFood.add(id.getInt("last_value"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

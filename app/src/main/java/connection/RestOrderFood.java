package connection;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by martin on 5/07/17.
 */

public class RestOrderFood extends AsyncTask<String, Void, String> {
    public URL url;
    private HttpURLConnection httpURLConnection;

    public RestOrderFood() {
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
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

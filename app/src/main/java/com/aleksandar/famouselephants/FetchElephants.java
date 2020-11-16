package com.aleksandar.famouselephants;

import android.content.Context;
import android.os.AsyncTask;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;


public class FetchElephants extends AsyncTask<Params,Void, Void> {
    String data;
    Boolean isFinished;
    ArrayList<JSONObject> list = new ArrayList<>();
    Context context;
    RecyclerView rv;
    @Override
    protected Void doInBackground(Params... Params) {
        try {
            URL url = new URL("https://elephant-api.herokuapp.com/elephants");
            HttpURLConnection connection = (HttpsURLConnection) url.openConnection();


            InputStream inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            data = reader.readLine();
            JSONArray json = new JSONArray(data);
            for (int i = 0; i < json.length(); i++){
                JSONObject object = json.getJSONObject(i);
                list.add(object);
            }
            isFinished = true;
            context = Params[0].context;
            rv = Params[0].rv;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            isFinished = false;

        } catch (IOException e) {
            e.printStackTrace();
            isFinished = false;

        } catch (JSONException e) {
            e.printStackTrace();
            isFinished = false;
        }
        return null;
    }
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Elephants.list = this.list;
        if(isFinished){
            Adapter adapter = new Adapter(context, list);
            rv.setAdapter(adapter);
            rv.setLayoutManager(new LinearLayoutManager(context));
        }

    }
}

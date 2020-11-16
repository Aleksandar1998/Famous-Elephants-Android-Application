package com.aleksandar.famouselephants;

import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;


import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;



public class SearchElephants extends AsyncTask<Params,Void,Void> {
    String data;
    Params obj;
    JSONObject json;

    @Override
    protected Void doInBackground(Params... params) {
        try {

            obj = params[0];
            URL url = new URL("https://elephant-api.herokuapp.com/elephants/name/"+obj.query);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            InputStream inputStream = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            data = reader.readLine();
            json = new JSONObject(data);


        }
        catch (Exception e){

        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if(json != null) {
            Intent intent = new Intent(obj.context, SingleElephant.class);
            intent.putExtra("object", json.toString());
            obj.context.startActivity(intent);
        }
        else {
            Toast.makeText(obj.context, "There is no elephant with that name", Toast.LENGTH_SHORT).show();
        }
    }
}

package com.aleksandar.famouselephants;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.aleksandar.famouselephants.R;

import org.json.JSONObject;

import java.util.ArrayList;

public class Elephants extends AppCompatActivity {
    RecyclerView rv;
    Button button;
    TextView search;
    Params params;
    public static ArrayList<JSONObject> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elephants);
        button = (Button) findViewById(R.id.buttonSearch);
        search = (TextView) findViewById(R.id.search);
        rv = findViewById(R.id.rv);

        FetchElephants process = new FetchElephants();
        params = new Params(this, rv,"");
        process.execute(params);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchElephants searchElephants = new SearchElephants();
                params.query = search.getText().toString();
                searchElephants.execute(params);
            }
        });
    }
}
class Params{
    Context context;
    RecyclerView rv;
    String query;
    public Params(Context context, RecyclerView rv, String query){
        this.context = context;
        this.rv = rv;
        this.query = query;

    }
}
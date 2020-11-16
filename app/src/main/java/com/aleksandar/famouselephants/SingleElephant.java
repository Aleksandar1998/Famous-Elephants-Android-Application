package com.aleksandar.famouselephants;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aleksandar.famouselephants.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class SingleElephant extends AppCompatActivity {
    JSONObject object;
    ImageView img;
    TextView name, note, sex, born, died;
    Button wiki;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_elephant);

        try {
            getData();
            setData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        wiki = (Button) findViewById(R.id.wiki);
        wiki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent browserIntent = null;
                try {
                    browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(object.getString("wikilink")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                SingleElephant.this.startActivity(browserIntent);
            }
        });
    }

    private void getData() throws JSONException {
        if(getIntent().hasExtra("object")){
            object = new JSONObject(getIntent().getStringExtra("object"));
        }
        else{
            Toast.makeText(this, "No data!", Toast.LENGTH_SHORT).show();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setData() throws JSONException {
        img = (ImageView) findViewById(R.id.img);
        name = (TextView) findViewById(R.id.nameSingle);
        note = (TextView) findViewById(R.id.noteSingle);
        sex = (TextView) findViewById(R.id.sex);
        born = (TextView) findViewById(R.id.born);
        died = (TextView) findViewById(R.id.died);


        Picasso.get().load(object.getString("image")).into(img);
        name.setText(object.getString("name"));
        note.setText(object.getString("note"));
        sex.setText(object.getString("sex"));
        born.setText(object.getString("dob"));
        died.setText(object.getString("dod"));




    }
}

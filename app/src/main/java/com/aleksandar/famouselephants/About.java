package com.aleksandar.famouselephants;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.aleksandar.famouselephants.R;

public class About extends AppCompatActivity {
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        btn = (Button) findViewById(R.id.api);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent browserIntent = null;
                try {
                    browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://elephant-api.herokuapp.com/"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                About.this.startActivity(browserIntent);
            }
        });
    }
}

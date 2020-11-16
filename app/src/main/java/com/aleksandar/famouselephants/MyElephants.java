package com.aleksandar.famouselephants;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.aleksandar.famouselephants.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
class Elephant{
    String name;
    String note;
    String sex;
    String img;
    String dob;
    String dod;
    String id;
    public Elephant(String name, String note, String sex, String img, String dob, String dod, String id){
        this.name = name;
        this.note = note;
        this.sex = sex;
        this.img = img;
        this.dob = dob;
        this.dod = dod;
        this.id = id;
    }
}
public class MyElephants extends AppCompatActivity {

    RecyclerView rv;
    FloatingActionButton addButton;
    Db db;
    ArrayList<Elephant> elephants;
    AddAdapter addAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_elephants);
        rv = findViewById(R.id.myrv);
        addButton = findViewById(R.id.addbtn);
        db = new Db(MyElephants.this);
        elephants = new ArrayList<>();

        storeDataFromDB();
        addAdapter = new AddAdapter(MyElephants.this, MyElephants.this, elephants);
        rv.setAdapter(addAdapter);
        rv.setLayoutManager(new LinearLayoutManager(MyElephants.this));

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyElephants.this, add_new_elephant.class);
                startActivityForResult(intent,1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        recreate();
    }

    void storeDataFromDB(){
        Cursor cursor = db.readAll();

        if(cursor.getCount() == 0){

        }else{
            while(cursor.moveToNext()){
                elephants.add(new Elephant(cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6), cursor.getString(0)));

            }
        }

    }
}

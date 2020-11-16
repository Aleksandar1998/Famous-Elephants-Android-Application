package com.aleksandar.famouselephants;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.aleksandar.famouselephants.R;

public class add_new_elephant extends AppCompatActivity {

    EditText name, note, dob, dod;

    ImageView imageView;
    Switch sv;
    Uri image;
    Button addImageButton, addElephantButton;
    Boolean clicked;
    String sex = "Male";
    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImageFromGallery();
                } else {
                    Toast.makeText(this, "Permission denied.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            image = data.getData();
            imageView.setImageURI(data.getData());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_elephant);
        name = findViewById(R.id.addname);
        note = findViewById(R.id.addnote);
        imageView = findViewById(R.id.addimageview);
        addImageButton = findViewById(R.id.addimage);
        addElephantButton = findViewById(R.id.addelephant);
        dob = findViewById(R.id.dob);
        dod = findViewById(R.id.dod);
        sv = findViewById(R.id.addswitch);
        clicked = false;
        sv.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sex = "Female";
                } else {
                    sex = "Male";
                }
            }
        });

        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permissions, PERMISSION_CODE);
                    } else {
                        pickImageFromGallery();
                    }
                } else {
                    pickImageFromGallery();
                }
            }
        });

        addElephantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Db myDB = new Db(add_new_elephant.this);

                if(name == null || note == null ||  image == null){
                    Toast.makeText(add_new_elephant.this, "Fill in all fields!", Toast.LENGTH_SHORT).show();

                }
                else {
                    if(dob.getText().toString().equals("")){
                        dob.setText("-");
                    }
                    if(dod.getText().toString().equals("")){
                        dod.setText("-");
                    }

                    myDB.add(name.getText().toString().trim(), note.getText().toString().trim(), sex, image.toString().trim(), dob.getText().toString().trim(), dod.getText().toString().trim());

                    clicked = true;
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (clicked) {
            setResult(1);
        }
    }
}

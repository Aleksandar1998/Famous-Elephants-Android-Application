package com.aleksandar.famouselephants;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.aleksandar.famouselephants.R;

public class UpdateElephant extends AppCompatActivity {


    EditText name,note,sex,dob,dod;
    ImageView imageView;
    Uri image;
    Button updateImageButton,updateElephantButton;
    ImageButton  deleteButton;
    String sName,sNote,sImage,sSex, sDob, sDod, sId;
    Boolean clicked, isImageChanged = false;

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case PERMISSION_CODE:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    pickImageFromGallery();
                }
                else{
                    Toast.makeText(this,"Permission denied.",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    private void pickImageFromGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_PICK_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            image = data.getData();
            isImageChanged = true;
            imageView.setImageURI(data.getData());
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_elephant);
        name = findViewById(R.id.updatename);
        note = findViewById(R.id.updatenote);
        sex = findViewById(R.id.updatesex);
        dob = findViewById(R.id.updatedob);
        dod = findViewById(R.id.updatedod);
        imageView = findViewById(R.id.imgupdateview);
        updateImageButton = findViewById(R.id.updateimage);
        updateElephantButton = findViewById(R.id.updateelephant);
        deleteButton =  findViewById(R.id.delete);
        clicked = false;
        getIntentData();
        setData();

        updateElephantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isImageChanged == false){
                    image = Uri.parse(sImage);
                }
                Db myDb = new Db(UpdateElephant.this);
                myDb.update(name.getText().toString(), note.getText().toString(), sex.getText().toString(), image.toString(), dob.getText().toString(), dod.getText().toString(), sId);
                clicked = true;
            }
        });
        updateImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permissions,PERMISSION_CODE);
                    }
                    else{
                        pickImageFromGallery();
                    }
                }
                else{
                    pickImageFromGallery();
                }
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();
            }
        });
    }



    void getIntentData(){
        if(getIntent().hasExtra("name") && getIntent().hasExtra("note") && getIntent().hasExtra("sex")
            && getIntent().hasExtra("img") && getIntent().hasExtra("dob")
            && getIntent().hasExtra("dod") && getIntent().hasExtra("id")){
            sName = getIntent().getStringExtra("name");
            sNote = getIntent().getStringExtra("note");
            sSex = getIntent().getStringExtra("sex");
            sDob = getIntent().getStringExtra("dob");
            sDod = getIntent().getStringExtra("dod");
            sImage = getIntent().getStringExtra("img");
            sId = getIntent().getStringExtra("id");
        }
        else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }
    void setData(){
        name.setText(sName);
        note.setText(sNote);
        sex.setText(sSex);
        dob.setText(sDob);
        dod.setText(sDod);
        imageView.setImageURI(Uri.parse(sImage));

    }
    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + sName +" ?");
        builder.setMessage("Are you sure you want to delete: " + sName + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Db myDb = new Db(UpdateElephant.this);
                myDb.delete(sId);
                finish();
            }
        });
        builder.setNegativeButton("Go back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }









    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(clicked){
            setResult(1);
        }
    }


}

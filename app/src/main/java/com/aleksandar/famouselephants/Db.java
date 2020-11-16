package com.aleksandar.famouselephants;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Db extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "Elephants.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "My_elephants";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_NOTE = "note";
    private static final String COLUMN_SEX = "sex";
    private static final String COLUMN_IMG = "img";
    private static final String COLUMN_DOB = "DOB";
    private static final String COLUMN_DOD = "DOD";

    public Db(@Nullable Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(@NotNull SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_NOTE + " TEXT, " +
                COLUMN_SEX  + " TEXT,"  +
                COLUMN_IMG  + " TEXT,"  +
                COLUMN_DOB  + " TEXT,"  +
                COLUMN_DOD + " TEXT)";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void add(String name, String note, String sex, String img, String dob, String dod){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME,name);
        cv.put(COLUMN_SEX,sex);
        cv.put(COLUMN_NOTE,note);
        cv.put(COLUMN_IMG,img);
        cv.put(COLUMN_DOB,dob);
        cv.put(COLUMN_DOD,dod);
        long result = db.insert(TABLE_NAME,null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed to insert data.", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Data added successfully!", Toast.LENGTH_SHORT).show();
        }

    }

    Cursor readAll(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
    void update(String name, String note, String sex, String img, String dob, String dod, String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME,name);
        cv.put(COLUMN_NOTE, note);
        cv.put(COLUMN_SEX,sex);
        cv.put(COLUMN_IMG,img);
        cv.put(COLUMN_DOB,dob);
        cv.put(COLUMN_DOD,dod);

        long result = db.update(TABLE_NAME, cv,"id=?",new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to update data.", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Updated successfully!", Toast.LENGTH_SHORT).show();
        }
    }
    void delete(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME,"id=?",new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to delete recipe.", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Deleted successfully!", Toast.LENGTH_SHORT).show();
        }
    }

}

package com.example.jessy.restaurant_revisited;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.TextView;

/**
 * Created by Jessy on 19-11-2017.
 */

public class TodoDatabase extends SQLiteOpenHelper {
    private static TodoDatabase instance;
    private static String DBName = "todos";
    private static int version  = 1;
    private Context applicationContext;

    private TodoDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public static TodoDatabase getInstance(Context context) {
        if (instance == null) {
            instance = new TodoDatabase(context.getApplicationContext(), DBName, null, version);
        }
        return instance;
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table todos (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, price INTEGER, amount INTEGER);");
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + "todos");
        onCreate(sqLiteDatabase);
    }

    public Cursor selectAll() {
        SQLiteDatabase dataBase = getReadableDatabase();
        Cursor cursor = dataBase.rawQuery("SELECT * FROM todos", null);
        return cursor;
    }

    public void insert(String name, int price) {
        SQLiteDatabase dataBase = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("name", name);
        values.put("price", price);
        values.put("amount", 1);

        dataBase.insert("todos",null,values);
    }

    public void update(long id, int checkBoxValue) {
        SQLiteDatabase dataBase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("completed", checkBoxValue);
        dataBase.update("todos", values, "_id=" + id, null);
    }

    public void delete(long id){
        SQLiteDatabase dataBase = getWritableDatabase();
        dataBase.delete("todos","_id="+id,null);

    }

    public void deleteAll(){
        SQLiteDatabase dataBase = getWritableDatabase();
        dataBase.delete("todos",null,null);
    }

}

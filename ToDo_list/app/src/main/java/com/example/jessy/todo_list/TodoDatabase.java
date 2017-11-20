package com.example.jessy.todo_list;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
        sqLiteDatabase.execSQL("create table todos (_id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, completed INTEGER);");
        //TodoDatabase db = TodoDatabase.getInstance(getApplicationContext());
        addToDoTest();
        }

    public void addToDoTest(){
        SQLiteDatabase TodoDatabase =getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title","Do the dishes");
        values.put("completed",0);
        TodoDatabase.insert("todos",null,values);
        values.put("title","Do the laundry");
        values.put("completed",1);
        TodoDatabase.insert("todos",null,values);
        values.put("title","Get food for tonight");
        values.put("completed",0);
        TodoDatabase.insert("todos",null,values);

    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + "todos");
        onCreate(sqLiteDatabase);
    }

    public Context getApplicationContext() {
        return applicationContext;
    }

    public Cursor selectAll() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM todos", null);
        return cursor;
    }
}

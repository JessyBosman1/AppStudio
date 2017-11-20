package com.example.jessy.todo_list;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TodoDatabase database;
    private TodoAdapter adapter;
    public TextView testTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testTextView = findViewById(R.id.testTextView);
        database = TodoDatabase.getInstance(getApplicationContext());

        Cursor overview = database.selectAll();
        testTextView.setText(overview.toString());
        adapter = new TodoAdapter( this,overview);
        /*
        ListView toDoListView = findViewById(R.id.toDoListView);
        toDoListView.setAdapter(adapter);
        */

    }
}

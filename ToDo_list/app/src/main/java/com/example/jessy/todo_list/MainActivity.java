package com.example.jessy.todo_list;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TodoDatabase database;
    private TodoAdapter adapter;
    public TextView testTextView;
    public TextView addItemInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testTextView = findViewById(R.id.testTextView);
        database = TodoDatabase.getInstance(getApplicationContext());

        Cursor Cursor = database.selectAll();

        SetAdapter(Cursor);


    }

    public void addItem(View view) {
        addItemInput = findViewById(R.id.addItemText);

        if (addItemInput.getText().toString().matches("")){
            Toast.makeText(this, "FAILED; empty input", Toast.LENGTH_SHORT).show();
        }
        else{
            database.insert(addItemInput.getText().toString(), 0);
            updateData();
            Toast.makeText(this, "item added", Toast.LENGTH_SHORT).show();
            addItemInput.setText("");
        }
        updateData();
    }

    public void updateData(){
        database = TodoDatabase.getInstance(getApplicationContext());
        ListView toDoListView = findViewById(R.id.toDoListView);
        Cursor Cursor = database.selectAll();
        adapter.swapCursor(Cursor);
        //SetAdapter(Cursor);
        toDoListView.setAdapter(adapter);
    }

    public void popUp(String Title, String Message){
        AlertDialog alertDialog = new AlertDialog.Builder(
                MainActivity.this).create();

        // Setting Dialog Title
        alertDialog.setTitle(Title);

        // Setting Dialog Message
        alertDialog.setMessage(Message);

        // Showing Alert Message
        alertDialog.show();

    }

    public void SetAdapter(Cursor Data) {
        ListView toDoListView = findViewById(R.id.toDoListView);
        testTextView = findViewById(R.id.testTextView);

        //toDoListView.setItemsCanFocus(false);

        testTextView.setText("setAdapter");
        adapter = new TodoAdapter( this,Data);
        toDoListView.setAdapter(adapter);

        toDoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                CheckBox complete = view.findViewById(R.id.toDoCheckbox);
                Cursor overview = database.selectAll();
                overview.move(position+1);

                int id = overview.getInt(overview.getColumnIndex("_id"));
                if (complete.isChecked()) {
                    database.update(id , 0);

                } else {
                    database.update(id, 1);
                }
                updateData();
            }

        });

        toDoListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor overview = database.selectAll();
                overview.move(position+1);

                int _id = overview.getInt(overview.getColumnIndex("_id"));
                database.delete(_id);
                updateData();
                return false;
            }

        });
    }

    }


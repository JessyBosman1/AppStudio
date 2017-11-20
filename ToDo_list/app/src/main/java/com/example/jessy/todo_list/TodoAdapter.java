package com.example.jessy.todo_list;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CursorAdapter;
import android.widget.TextView;


/**
 * Created by Jessy on 20-11-2017.
 */

public class TodoAdapter extends CursorAdapter {
    public TodoAdapter(Context context, Cursor cursor) {
        super(context, cursor, R.layout.row_todo);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.row_todo, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView todoTitle = view.findViewById(R.id.toDoTitle);
        CheckBox toDoCheckBox = view.findViewById(R.id.toDoCheckbox);

        todoTitle.setText(cursor.getString(cursor.getColumnIndex("title")));
        Integer completedValue = cursor.getInt(cursor.getColumnIndex("completed"));
        if (completedValue == 1){
            toDoCheckBox.setChecked(true);
        }
        else{
            toDoCheckBox.setChecked(false);
        }
    }
}

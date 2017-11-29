package com.example.jessy.restaurant_revisited;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CursorAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;


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
        TextView OrderPrice = view.findViewById(R.id.OrderPriceTextView);
        TextView OrderAmount = view.findViewById(R.id.textView);

        todoTitle.setText(cursor.getString(cursor.getColumnIndex("name")));
        OrderPrice.setText("€" + cursor.getString(cursor.getColumnIndex("price")) + ",-");
        OrderAmount.setText(cursor.getString(cursor.getColumnIndex("amount")));
    }
}

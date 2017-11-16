package com.example.jebo.restaurant;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

public class OrderActivity extends AppCompatActivity {
    public ArrayList<String> ArrayOrder = new ArrayList<String>();

    TextView headerTextView;
    ListView listViewOrders;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        getOrder();
    }

    public void getOrder(){
        headerTextView = findViewById(R.id.headerTextView);
        listViewOrders = findViewById(R.id.listViewOrders);
        SharedPreferences yourOrderPrefs = this.getSharedPreferences("orderSave", MODE_PRIVATE);
        /*
        String i = yourOrderPrefs.getString("Pesto Linguini", null);
        if (i != null){
            headerTextView.setText(i);

        }*/

        Map<String, ?> allEntries = yourOrderPrefs.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            //Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
            //headerTextView.setText(entry.getValue().toString());
            ArrayOrder.add(entry.getValue().toString());
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,  android.R.layout.simple_list_item_1, ArrayOrder);
        listViewOrders.setAdapter(arrayAdapter);

    }
}

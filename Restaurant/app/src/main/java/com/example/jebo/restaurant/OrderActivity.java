package com.example.jebo.restaurant;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

        listViewOrders.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                removeItemOrder(String.valueOf(adapterView.getItemAtPosition(i)));
            }
        });

    }

    public void clearOrder(View view) {
        //SharedPreferences yourOrderPrefs = this.getSharedPreferences("orderSave", this.MODE_PRIVATE);
        this.getSharedPreferences("orderSave", this.MODE_PRIVATE).edit().clear().commit();

        Intent intent = new Intent(this, OrderActivity.class);
        startActivity(intent);
        finish();

    }

    public void removeItemOrder(String Item){
        this.getSharedPreferences("orderSave", this.MODE_PRIVATE).edit().remove(Item).commit();
        Intent intent = new Intent(this, OrderActivity.class);
        startActivity(intent);
        finish();

    }
}

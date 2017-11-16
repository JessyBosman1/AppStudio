package com.example.jebo.restaurant;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.HeaderViewListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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

    public void getOrder() {
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
            String[] array = entry.getValue().toString().split(",");
            //ArrayOrder.add(entry.getValue().toString());
            //headerTextView.setText(array[2].toString());

            ArrayOrder.add(array[0] + "(" + array[3] + "x)" + "  " + array[1]);
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ArrayOrder);
        listViewOrders.setAdapter(arrayAdapter);

        listViewOrders.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String[] Item = String.valueOf(adapterView.getItemAtPosition(i)).split("\\(");
                removeItemOrder(Item[0]);
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

    public void removeItemOrder(String Item) {
        this.getSharedPreferences("orderSave", this.MODE_PRIVATE).edit().remove(Item).commit();
        Intent intent = new Intent(this, OrderActivity.class);
        startActivity(intent);
        finish();

    }

    public void Post(View view) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://resto.mprog.nl/order";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JSONArray menuArray;

                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            //ArrayList<String> listItems = new ArrayList<String>();
                            popUp(jsonObj.getString("preparation_time"));

                            } catch (JSONException e1) {
                            e1.printStackTrace();
                        }

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                headerTextView.setText(error.toString());
            }
        });
        queue.add(stringRequest);
    }

    public void popUp(String Item){
        AlertDialog alertDialog = new AlertDialog.Builder(
                OrderActivity.this).create();

        // Setting Dialog Title
        alertDialog.setTitle("Order is being processed");

        // Setting Dialog Message
        alertDialog.setMessage("Preparation time: " + Item + " minutes");

        // Showing Alert Message
        alertDialog.show();

    }
}

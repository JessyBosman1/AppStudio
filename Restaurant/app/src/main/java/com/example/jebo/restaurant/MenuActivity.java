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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.Objects;

/*
public class MainActivity extends AppCompatActivity {
    // Array of strings...
    ListView simpleList;
    String animalList[] = {"Lion","Tiger","Monkey","Elephant","Dog","Cat","Camel"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        simpleList = (ListView) findViewById(R.id.listView);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_main, R.id.text, animalList);
        simpleList.setAdapter(arrayAdapter);
    }
*/
public class MenuActivity extends AppCompatActivity {
    public ListView menuItemListView;
    public ListView menuItemPriceListView;

    String animalList[] = {"Lion","Tiger","Monkey","Elephant","Dog","Cat","Camel"};

    public ArrayList<String> listItems = new ArrayList<String>();
    public ArrayList<String> listItemsPrice = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        menuItemListView = (ListView) findViewById(R.id.listView);
        menuItemPriceListView = (ListView) findViewById(R.id.listViewPrices);
        final TextView mTextView = findViewById(R.id.text);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://resto.mprog.nl/menu";

        Intent intent = getIntent();
        final String selectedCategory = (String) intent.getStringExtra("SelectedCategory");
        mTextView.setText(selectedCategory);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //Log.d("my_test", response);
                        JSONArray menuArray;

                        try {
                            JSONObject newObject = (JSONObject) new JSONTokener(response).nextValue();
                            //ArrayList<String> listItems = new ArrayList<String>();

                            menuArray = newObject.getJSONArray("items");
                            for (int i = 0; i < menuArray.length(); i++) {
                                //mTextView.setText(menuArray.getJSONObject(i).getString("name"));
                                //listItems.add(menuArray.getJSONObject(i).getString("name"));
                                if(Objects.equals(menuArray.getJSONObject(i).getString("category"), selectedCategory)){
                                    addItemToArray(menuArray.getJSONObject(i).getString("name"));}

                                //addItemToArrayPrice(menuArray.getJSONObject(i).getString("price"));
                            }
                            SetAdapter();
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                        //mTextView.setText(listItems.toString());

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTextView.setText("That didn't work!");
            }
        });

        //mTextView.setText(listItems.toString());
        //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,  R.layout.activity_main, R.id.text, animalList);
        //simpleList.setAdapter(arrayAdapter);

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
    public void addItemToArray(String Item) {
        listItems.add(Item);

    }
    public void addItemToArrayPrice(String Item) {
        listItemsPrice.add(Item);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,  R.layout.activity_main, R.id.text, listItemsPrice);
        menuItemPriceListView.setAdapter(arrayAdapter);
    }

    public void SetAdapter() {
        final TextView mTextView = findViewById(R.id.text);

        //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,  R.layout.activity_main, R.id.text, listItems);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,  android.R.layout.simple_list_item_1, listItems);
        menuItemListView.setAdapter(arrayAdapter);

        menuItemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mTextView.setText(String.valueOf(adapterView.getItemAtPosition(i)));
                addToOrder(String.valueOf(adapterView.getItemAtPosition(i)));

            }
        });

    }
public void addToOrder(String Item){
    SharedPreferences yourOrderPrefs = this.getSharedPreferences("orderSave", this.MODE_PRIVATE);
    SharedPreferences.Editor prefsEditor = yourOrderPrefs.edit();
    prefsEditor.putString(Item, Item);
    //prefsEditor.putString("name", value);
    prefsEditor.commit();

}
    public void goToOrder(View view) {
        Intent intent = new Intent(this, OrderActivity.class);
        startActivity(intent);
    }

}



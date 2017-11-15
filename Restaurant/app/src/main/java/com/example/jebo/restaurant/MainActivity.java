package com.example.jebo.restaurant;

import android.content.Intent;
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
public class MainActivity extends AppCompatActivity {
    public ListView menuItemListView;

    public ArrayList<String> listItems = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        menuItemListView = (ListView) findViewById(R.id.listView);

        final TextView mTextView = findViewById(R.id.text);
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://resto.mprog.nl/categories";

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

                            menuArray = newObject.getJSONArray("categories");
                            //mTextView.setText(menuArray.toString());
                            for (int i = 0; i < menuArray.length(); i++) {
                                //mTextView.setText(menuArray.getJSONObject(i).getString("name"));

                                //mTextView.setText(menuArray.get(i).toString());
                                addItemToArray(menuArray.get(i).toString());

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

public void SetAdapter() {
    final TextView mTextView = findViewById(R.id.text);

    //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,  R.layout.activity_main, R.id.text, listItems);
    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,  android.R.layout.simple_list_item_1, listItems);

    menuItemListView = (ListView) findViewById(R.id.listView);
    menuItemListView.setAdapter(arrayAdapter);

    menuItemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            //mTextView.setText(String.valueOf(adapterView.getItemAtPosition(i)));
            toMenu(String.valueOf(adapterView.getItemAtPosition(i)));
        }
    });

}
public void toMenu(String SelectedCategory){
        Intent intent = new Intent(this, MenuActivity.class);
        intent.putExtra("SelectedCategory", SelectedCategory);

        startActivity(intent);
}

    public void goToOrder(View view) {
        Intent intent = new Intent(this, OrderActivity.class);
        startActivity(intent);
    }
}

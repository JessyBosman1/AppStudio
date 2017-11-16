package com.example.jebo.restaurant;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.Objects;

public class InfoActivity extends AppCompatActivity {
    public TextView ItemName;
    public TextView ItemDescription;
    public TextView ItemPrice;
    public TextView ItemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        ItemName = findViewById(R.id.ItemName);
        ItemDescription = findViewById(R.id.ItemDescription);
        ItemPrice = findViewById(R.id.ItemPrice);
        ItemId = findViewById(R.id.ItemId);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://resto.mprog.nl/menu";

        Intent intent = getIntent();
        final String selectedItem = (String) intent.getStringExtra("SelectedItem");
        ItemName.setText(selectedItem);

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
                                if (Objects.equals(menuArray.getJSONObject(i).getString("name"), selectedItem)) {
                                    ItemDescription.setText(menuArray.getJSONObject(i).getString("description"));
                                    addImage(menuArray.getJSONObject(i).getString("image_url"));
                                    ItemPrice.setText("$" + menuArray.getJSONObject(i).getString("price") + "0");
                                    //addItemToArrayPrice(menuArray.getJSONObject(i).getString("price"));
                                    ItemId.setText(menuArray.getJSONObject(i).getString("id"));
                                }
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                        //mTextView.setText(listItems.toString());

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ItemName.setText("That didn't work!");
            }
        });

        //mTextView.setText(listItems.toString());
        //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,  R.layout.activity_main, R.id.text, animalList);
        //simpleList.setAdapter(arrayAdapter);

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
    public void addImage(String imgUrl){
        ImageView
        ImageView = (ImageView) findViewById(R.id.ItemImage);
        Picasso.with(this).load(imgUrl).fit().into(ImageView);

    }

    public void addToOrder(View view) {
        String Item = ItemName.getText().toString();

        Spinner mySpinner= (Spinner) findViewById(R.id.spinner);
        String SpinnerText = mySpinner.getSelectedItem().toString();

        SharedPreferences yourOrderPrefs = this.getSharedPreferences("orderSave", this.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = yourOrderPrefs.edit();
        String Input = Item + "," + ItemPrice.getText().toString() + "," + ItemId.getText().toString() + "," + SpinnerText;
        prefsEditor.putString(Item, Input);

        //String[] array = y.split(",");
        //ItemName.setText(array[0].toString());
        //prefsEditor.putInt(Item + "Price", Integer.parseInt(ItemPrice.getText().toString()));
        //prefsEditor.putString("name", value);
        prefsEditor.commit();

        popUp(Item);
    }
    public void popUp(String Item){
        AlertDialog alertDialog = new AlertDialog.Builder(
                InfoActivity.this).create();

        // Setting Dialog Title
        alertDialog.setTitle("Added to Order:");

        // Setting Dialog Message
        alertDialog.setMessage(Item);

        // Showing Alert Message
        alertDialog.show();


    }
}


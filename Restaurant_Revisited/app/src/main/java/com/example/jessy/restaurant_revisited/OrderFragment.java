package com.example.jessy.restaurant_revisited;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends DialogFragment implements View.OnClickListener{
    private TodoAdapter adapter;
    private TodoDatabase db;
    public float totalPrice;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        setRetainInstance(true);

        ListView list = (ListView) view.findViewById(R.id.x);
        db = TodoDatabase.getInstance(getContext());

        Cursor cursor = db.selectAll();
        adapter = new TodoAdapter(getContext(), cursor);

        list.setAdapter(adapter);

        try {
            while (cursor.moveToNext()) {
                String Price = cursor.getString(cursor.getColumnIndex("price"));
                Float PriceInt = Float.parseFloat(Price);

                String Amount = cursor.getString(cursor.getColumnIndex("amount"));
                Integer AmountInt = Integer.parseInt(Amount);

                Float totalItemPrice = PriceInt * AmountInt;

                totalPrice += totalItemPrice;
            }
        } finally {
        }

        TextView TotalPriceTextView = view.findViewById(R.id.totalPriceTextView);
        TotalPriceTextView.setText(Float.toString(totalPrice));

        Button clearOrder = view.findViewById(R.id.clearOrder);
        clearOrder.setOnClickListener((View.OnClickListener) this);

        Button submitOrder = view.findViewById(R.id.submitOrder);
        submitOrder.setOnClickListener((View.OnClickListener) this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case(R.id.clearOrder):
                db = TodoDatabase.getInstance(getContext());
                db.deleteAll();
                ListView list = (ListView) getView().findViewById(R.id.x);

                Cursor emptyCursor = db.selectAll();
                adapter.swapCursor(emptyCursor);

                list.setAdapter(adapter);

                TextView TotalPriceTextView = getView().findViewById(R.id.totalPriceTextView);
                TotalPriceTextView.setText(" ");
                break;

            case(R.id.submitOrder):
                db = TodoDatabase.getInstance(getContext());
                db.deleteAll();

                ListView list2 = (ListView) getView().findViewById(R.id.x);
                Cursor emptyCursor2 = db.selectAll();
                adapter.swapCursor(emptyCursor2);

                list2.setAdapter(adapter);

                TextView TotalPriceTextView2 = getView().findViewById(R.id.totalPriceTextView);
                TotalPriceTextView2.setText(" ");
                Post(view);
                break;


        }
    }

    public void Update(){
        //Toast.makeText(getActivity(), "Reopen order to update",
                //Toast.LENGTH_LONG).show();
        //ListView list = (ListView) getView().findViewById(R.id.x);

        //list.setAdapter(null);
    }

    public void Post(View view) {
        RequestQueue queue = Volley.newRequestQueue(getContext());
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

            }
        });
        queue.add(stringRequest);
    }

    public void popUp(String Item){
        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();

        // Setting Dialog Title
        alertDialog.setTitle("Order is being processed");

        // Setting Dialog Message
        alertDialog.setMessage("Preparation time: " + Item + " minutes");

        // Showing Alert Message
        alertDialog.show();

    }

}

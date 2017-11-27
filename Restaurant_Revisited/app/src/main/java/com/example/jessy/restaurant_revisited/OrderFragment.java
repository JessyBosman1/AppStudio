package com.example.jessy.restaurant_revisited;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends DialogFragment {
    private TodoAdapter adapter;
    private TodoDatabase db;

    public OrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order, container, false);

        ListView list = (ListView) view.findViewById(R.id.x);
        db = TodoDatabase.getInstance(getContext());
        //db.insert("Italian Salad",5);
        Cursor overview = db.selectAll();
        adapter = new TodoAdapter(getContext(), overview);

        list.setAdapter(adapter);
        Toast.makeText(getContext(), "Total number of Items are:" + list.getAdapter().getCount() , Toast.LENGTH_LONG).show();

        return view;
    }

}

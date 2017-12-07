package com.example.jessy.famouscharactes;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class registerFragment extends Fragment implements View.OnClickListener{


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseRef = database.getReference();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        Button submitPerson = view.findViewById(R.id.submitPerson);
        submitPerson.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submitPerson:
                TextView inputName = getActivity().findViewById(R.id.textviewName);
                TextView inputImage = getActivity().findViewById(R.id.textviewImage);
                String name = inputName.getText().toString();
                String image = inputImage.getText().toString();
                Person aPerson = new Person(name, image);

                databaseRef.child("Person").child(name).setValue(aPerson);

                break;
        }
    }


    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }

}

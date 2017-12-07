package com.example.jessy.famouscharactes;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionFragment extends Fragment {
    public ArrayList totalInfo = new ArrayList();

    private StorageReference mStorageRef;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_question, container, false);

    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        getFromDatabase();
    }

    public void getFromDatabase() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //Log.d("dataSnapshot", "Value is: " + dataSnapshot.toString());
                Person aCharacter = dataSnapshot.child("Person").child("Albert Einstein").getValue(Person.class);
                //Log.d("dataSnapshot", "Value is: " + aCharacter.toString());

                placeContent(aCharacter.name.toString(), aCharacter.image.toString());

                // loop to get all person names
                for (DataSnapshot snapshot : dataSnapshot.child("Person").getChildren()) {
                    Person aPerson = snapshot.getValue(Person.class);
                    Log.d("dataSnap", aPerson.name);
                    addToInfo(aPerson.name);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("dbError", " ", error.toException());
            }
        });
    }

    public void addToInfo(String name){
        totalInfo.add(name);

    }

    public void placeContent(String name, String image){
        ImageView ImagePlaceholder = getActivity().findViewById(R.id.imageQuestionContainer);
        Picasso.with(getActivity()).load(image).fit().into(ImagePlaceholder);

        Button buttonOption1 = getActivity().findViewById(R.id.buttonOption1);
        Button buttonOption2 = getActivity().findViewById(R.id.buttonOption2);
        Button buttonOption3 = getActivity().findViewById(R.id.buttonOption3);
        Button buttonOption4 = getActivity().findViewById(R.id.buttonOption4);

        ArrayList alist = new ArrayList<String>();
        alist.add("Abraham Lincoln");
        alist.add("Albert Einstein");
        alist.add("Alexander VI Borgia");
        alist.add("Adolf Hitler");

        buttonOption1.setText(alist.get(0).toString());
        buttonOption2.setText(alist.get(1).toString());
        buttonOption3.setText(alist.get(2).toString());
        buttonOption4.setText(alist.get(3).toString());
    }
}
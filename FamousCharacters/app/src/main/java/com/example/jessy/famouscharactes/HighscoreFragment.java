package com.example.jessy.famouscharactes;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;


/**
 * A simple {@link Fragment} subclass.
 */
public class HighscoreFragment extends Fragment implements View.OnClickListener{
    public FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    public DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
    public ArrayList<String> sortedUsers  = new ArrayList<String>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_highscore, container, false);

        Button backButtonHighscore = view.findViewById(R.id.backButtonHighscore);
        backButtonHighscore.setOnClickListener(this);

        final ListView highscoreList = view.findViewById(R.id.highscoreListView);

        databaseRef.child("User").orderByChild("Highscore").addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot child : snapshot.getChildren()) {
                    User sortedUser = child.getValue(User.class);
                    sortedUsers.add(sortedUser.Email + "  -  " + sortedUser.Highscore + " points");
                }
                Log.d("sortedUsers",sortedUsers.toString());

                Collections.reverse(sortedUsers);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(),  R.layout.highscore_row, R.id.highscoreItem, sortedUsers);
                highscoreList.setAdapter(arrayAdapter);

                databaseRef.removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("dbError", " ", error.toException());
            }

            });

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backButtonHighscore:
                returnToStart();
        }
    }
    public void returnToStart(){
        FragmentManager fm = getActivity().getSupportFragmentManager();
        StartFragment fragment = new StartFragment();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, fragment, "StartFragment");
        ft.commit();
    }

}
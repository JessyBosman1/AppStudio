package com.example.jessy.famouscharactes;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Collections;


public class HighscoreFragment extends Fragment implements View.OnClickListener{
    // Get database reference.
    public DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
    // Create ArrayList to store users, sorted by firebase.
    public ArrayList<String> sortedUsers  = new ArrayList<String>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_highscore, container, false);

        // Find button and create onClick listener.
        Button backButtonHighscore = view.findViewById(R.id.backButtonHighscore);
        backButtonHighscore.setOnClickListener(this);

        // Find listView to display highscores.
        final ListView highscoreList = view.findViewById(R.id.highscoreListView);

        // Get all users, sorted on highscore, from Firebase.
        databaseRef.child("User").orderByChild("Highscore").addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot snapshot) {
                // For each sorted Child, add Email and Score to sorted list
                for (DataSnapshot child : snapshot.getChildren()) {
                    User sortedUser = child.getValue(User.class);
                    sortedUsers.add(sortedUser.Email + "  -  " + sortedUser.Highscore + " points");
                }
                // Reverse from descending to ascending a.k.a. high to low
                Collections.reverse(sortedUsers);
                // set Adapter to place sorted Highscores in listView with Custom Row Layout.
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(),
                                                                             R.layout.highscore_row,
                                                                             R.id.highscoreItem,
                                                                             sortedUsers);
                highscoreList.setAdapter(arrayAdapter);

                // Remove change listener from database.
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
    // Handle onclick events.
    public void onClick(View v) {
        switch (v.getId()) {
            // If backbutton clicked; return to start
            case R.id.backButtonHighscore:
                returnToStart();
        }
    }

    public void returnToStart(){
        // return to start fragment by replacing current fragment in fragment_containter.
        FragmentManager fm = getActivity().getSupportFragmentManager();
        StartFragment fragment = new StartFragment();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, fragment, "StartFragment");
        ft.commit();
    }

}
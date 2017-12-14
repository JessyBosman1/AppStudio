package com.example.jessy.famouscharactes;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class endGameFragment extends Fragment implements View.OnClickListener {
    // get Firebase user or null if not logged in.
    public FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    // get dataBase reference to be able to find the database.
    public DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();

    // Initialize variables used to store data from obtained from the game.
    public int finalScore;
    public int totalCorrect;
    public int totalTime;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get variables from bundle, to obtain scores from last game.
        Bundle bundle = this.getArguments();
        // Check if bundle exists.
        if (bundle != null) {
            finalScore = bundle.getInt("playerScore", 0);
            totalTime = bundle.getInt("totalTime", 0);
            totalCorrect = bundle.getInt("totalCorrect", 0);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_end_game, container, false);

        // Store viewID references from xml to accesable variables.
        TextView textScore = view.findViewById(R.id.textScore);
        TextView textCorrect = view.findViewById(R.id.textCorrect);
        TextView textTime = view.findViewById(R.id.textTime);

        // Set variables to TextViews, and give some extra context text.
        textScore.setText(String.valueOf(finalScore));
        textCorrect.setText(totalCorrect + "/10 correct");
        textTime.setText("In " + String.valueOf(totalTime) + " seconds");

        // Find oontinue button and set onclick listener.
        Button continueButtonEnd = view.findViewById(R.id.continueButtonEnd);
        continueButtonEnd.setOnClickListener(this);

        checkHighScore();

        return view;

    }

    public void checkHighScore(){
        if(user != null) {
            final String UserID = user.getUid();
            final String Email = user.getEmail();
            final Integer Highscore = finalScore;

            databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    try{
                        Integer oldHighScore = Integer.parseInt(dataSnapshot.child("User").child(UserID).child("Highscore").getValue().toString());
                        if (oldHighScore < Highscore) {
                            User aUser = new User(Email, Highscore);
                            databaseRef.child("User").child(UserID).setValue(aUser);
                            Toast.makeText(getActivity(),"New personal Highscore!",Toast.LENGTH_LONG).show();
                        }
                    }
                    catch (Exception e){
                        User aUser = new User(Email, Highscore);
                        databaseRef.child("User").child(UserID).setValue(aUser);
                        Toast.makeText(getActivity(),"New personal Highscore!",Toast.LENGTH_LONG).show();
                    }


                    databaseRef.removeEventListener(this);

                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("dbError", " ", error.toException());
                }
            });

        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.continueButtonEnd:
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

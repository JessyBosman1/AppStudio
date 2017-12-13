package com.example.jessy.famouscharactes;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class endGameFragment extends Fragment{
    public FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();

    public int finalScore;
    public int totalCorrect;
    public int totalTime;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get score from bundle
        Bundle bundle = this.getArguments();
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

        TextView textScore = view.findViewById(R.id.textScore);
        TextView textCorrect = view.findViewById(R.id.textCorrect);
        TextView textTime = view.findViewById(R.id.textTime);

        textScore.setText(String.valueOf(finalScore));
        textCorrect.setText(totalCorrect + "/10 correct");
        textTime.setText("In " + String.valueOf(totalTime) + " seconds");

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
                    if(dataSnapshot.child("User").child(UserID) != null) {
                        Integer oldHighScore = Integer.parseInt(dataSnapshot.child("User").child(UserID).child("Highscore").getValue().toString());
                        if (oldHighScore < Highscore) {
                            User aUser = new User(Email, Highscore);
                            databaseRef.child("User").child(UserID).setValue(aUser);
                            Toast.makeText(getActivity(),"New personal Highscore!",Toast.LENGTH_LONG).show();
                        }
                    }
                    else{
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

}

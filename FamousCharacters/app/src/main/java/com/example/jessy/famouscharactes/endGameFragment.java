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


public class endGameFragment extends Fragment{
    public int finalScore;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get score from bundle
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            int playerScore = bundle.getInt("playerScore", 0);
            Log.d("score", String.valueOf(playerScore));
            finalScore = playerScore;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_end_game, container, false);
        TextView textScore = view.findViewById(R.id.textScore);
        textScore.setText(String.valueOf(finalScore));
        return view;

    }

}

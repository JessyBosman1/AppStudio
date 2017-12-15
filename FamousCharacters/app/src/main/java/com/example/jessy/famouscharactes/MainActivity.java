package com.example.jessy.famouscharactes;

import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    // Handles back presses in the different fragments.
    public void onBackPressed() {
        // Get current fragment
        Fragment currentFragment;
        currentFragment = getSupportFragmentManager().findFragmentByTag("StartFragment");
        // If currentFragment is StartFragment.
        if (currentFragment != null && currentFragment.isVisible()) {
            Log.d("currentFragment", currentFragment.toString());
            // Handle backpress as normal, close app.
            super.onBackPressed();
        }

        currentFragment = getSupportFragmentManager().findFragmentByTag("QuestionFragment");
        // If currentFragment is QuestionFragment (if user is playing the quiz).
        if (currentFragment != null && currentFragment.isVisible()) {
            // Build Alert dialog asking if the user wants to exit the current game.
            buildAlert();
        }
        // For any other fragment, go to the Start menu.
        else{
            returnToStart();
        }
    }

    public void buildAlert(){
        // set alert popup options.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure you want to exit to Main Menu?")
                .setMessage("The data of your current activity will NOT be saved.")
                .setCancelable(false)
                .setPositiveButton("To Main Menu", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // return to start menu if "main menu" is clicked.
                        returnToStart();
                    }
                })
                .setNegativeButton("Stay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // close alert and continue playing.
                        dialog.cancel();
                    }
                });
        // create and show alert.
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void returnToStart(){
        // return to start fragment by replacing current fragment in fragment_containter.
        FragmentManager fm = getSupportFragmentManager();
        StartFragment fragment = new StartFragment();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, fragment, "StartFragment");
        ft.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // onCreate set StartFragment in fragment container.
        FragmentManager fm = getSupportFragmentManager();
        StartFragment fragment = new StartFragment();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, fragment, "StartFragment");
        ft.commit();
    }

    public void goToQuestions(View view) {
        // (ran when clicked on start game button).
        // Check if a user is logged in.
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            // if logged in, go to questions
            startQuestions();
        }
        else{
            // if not logged in; show alert. ( to start playing or go to Login).
            buildAlertNotLoggedIt();
        }
    }

    public void startLogin() {
        // Go to login fragment by replacing current fragment in fragment_containter.
        FragmentManager fm = getSupportFragmentManager();
        LoginFragment fragment = new LoginFragment();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, fragment, "LoginFragment").addToBackStack(null).commit();
    }

    public void startQuestions(){
        // Go to Quiz fragment by replacing current fragment in fragment_containter.
        FragmentManager fm = getSupportFragmentManager();
        QuestionFragment fragment = new QuestionFragment();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, fragment, "QuestionFragment").addToBackStack(null).commit();

    }

    public void buildAlertNotLoggedIt(){
        // set alert popup options.
        // prompted if the user is not logged in and wants to start a game.
        // Ask if the user wants to start playing or login first.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("WARNING: You are not logged in!")
                .setMessage("Your highscores will not be saved")
                .setCancelable(false)
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // If clicked on continue, start Quiz
                        startQuestions();
                    }
                })
                .setNegativeButton("Login", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // If clicked on login, go to login.
                        startLogin();
                    }
                })
                .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // If clicked on cancel, close dialog and remain on start screen.
                        dialog.cancel();
                    }
                });
        // create and show alert.
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void goToHighscore(View view) {
        // Go to Highscore fragment by replacing current fragment in fragment_containter.
        FragmentManager fm = getSupportFragmentManager();
        HighscoreFragment fragment = new HighscoreFragment();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, fragment, "HighscoreFragment").addToBackStack(null).commit();
    }
}


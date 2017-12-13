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
    public void onBackPressed() {
        Fragment currentFragment;
        currentFragment = getSupportFragmentManager().findFragmentByTag("StartFragment");
        if (currentFragment != null && currentFragment.isVisible()) {
            Log.d("currentFragment", currentFragment.toString());
            super.onBackPressed();
        }

        currentFragment = getSupportFragmentManager().findFragmentByTag("QuestionFragment");
        if (currentFragment != null && currentFragment.isVisible()) {
            Log.d("currentFragment", currentFragment.toString());
            buildAlert();
        }
        else{
            returnToStart();
        }

    }

    public void buildAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure you want to exit to Main Menu?")
                .setMessage("The data of your current activity will NOT be saved.")
                .setCancelable(false)
                .setPositiveButton("To Main Menu", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        returnToStart();
                    }
                })
                .setNegativeButton("Stay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void returnToStart(){
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


        FragmentManager fm = getSupportFragmentManager();
        StartFragment fragment = new StartFragment();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, fragment, "StartFragment");
        ft.commit();
    }

    public void goToQuestions(View view) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            startQuestions();
        }
        else{
            buildAlertNotLoggedIt();
        }


    }

    public void startLogin() {
        FragmentManager fm = getSupportFragmentManager();
        LoginFragment fragment = new LoginFragment();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, fragment, "LoginFragment").addToBackStack(null).commit();
    }

    public void startQuestions(){
        FragmentManager fm = getSupportFragmentManager();
        QuestionFragment fragment = new QuestionFragment();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, fragment, "QuestionFragment").addToBackStack(null).commit();

    }

    public void buildAlertNotLoggedIt(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("WARNING: You are not logged in!")
                .setMessage("Your highscores will not be saved")
                .setCancelable(false)
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        startQuestions();
                    }
                })
                .setNegativeButton("Login", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        startLogin();
                    }
                })
                .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

}


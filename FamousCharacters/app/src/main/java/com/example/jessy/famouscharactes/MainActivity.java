package com.example.jessy.famouscharactes;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
        FragmentManager fm = getSupportFragmentManager();
        QuestionFragment fragment = new QuestionFragment();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, fragment, "QuestionFragment").commit();
    }

    public void goToRegister(View view) {
        FragmentManager fm = getSupportFragmentManager();
        registerFragment fragment = new registerFragment();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, fragment, "RegisterFragment").commit();
    }


}


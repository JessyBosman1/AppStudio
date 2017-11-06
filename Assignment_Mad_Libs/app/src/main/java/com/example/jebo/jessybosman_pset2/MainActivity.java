package com.example.jebo.jessybosman_pset2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToQuestions(View view) {
       Intent intent = new Intent(this, QuestionActivity.class);
       startActivity(intent);
        finish();
    }
}

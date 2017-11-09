package com.example.jebo.jessybosman_pset2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;

import java.io.InputStream;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Story story;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToQuestions(View view) {
        Random rand = new Random();
        int n = rand.nextInt(5);
        if(n==0){
            InputStream stream = this.getResources().openRawResource(R.raw.madlib0_simple);
            story = new Story(stream);}
        if(n==1){
            InputStream stream = this.getResources().openRawResource(R.raw.madlib1_tarzan);
            story = new Story(stream);}
        if(n==3){
            InputStream stream = this.getResources().openRawResource(R.raw.madlib2_university) ;
            story = new Story(stream);}
        if(n==4){
            InputStream stream = this.getResources().openRawResource(R.raw.madlib4_dance);
            story = new Story(stream);}

       Intent intent = new Intent(this, QuestionActivity.class);
       intent.putExtra("Story", story);
       startActivity(intent);
    }
}

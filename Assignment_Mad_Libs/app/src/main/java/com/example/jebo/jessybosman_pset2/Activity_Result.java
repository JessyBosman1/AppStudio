package com.example.jebo.jessybosman_pset2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Activity_Result extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__result);

        Intent intent = getIntent();
        String textStory = (String) intent.getSerializableExtra("Story");

        TextView textView = findViewById(R.id.TextView_Result);
        textView.setText(textStory);
    }

    public void buttonMainMenu(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();

    }
}

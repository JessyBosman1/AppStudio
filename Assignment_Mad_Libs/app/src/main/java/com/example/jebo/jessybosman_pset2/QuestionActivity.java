package com.example.jebo.jessybosman_pset2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.InputStream;

public class QuestionActivity extends AppCompatActivity {
    Story story;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        InputStream stream = this.getResources().openRawResource(R.raw.madlib1_tarzan);
        story = new Story(stream);

        TextView editText = findViewById(R.id.editText);
        editText.setHint(story.getNextPlaceholder());

        TextView textviewWordsRemaining = findViewById(R.id.textviewWordsRemaining);
        textviewWordsRemaining.setText(story.getPlaceholderRemainingCount() + " words remaining");
    }

    public void buttonConfirm(View view) {

        TextView editText = findViewById(R.id.editText);
        story.fillInPlaceholder(editText.getText().toString());

        if(story.getPlaceholderRemainingCount() != 0) {
            editText.setHint(story.getNextPlaceholder());
            editText.setText("");

            TextView textviewWordsRemaining = findViewById(R.id.textviewWordsRemaining);
            textviewWordsRemaining.setText(story.getPlaceholderRemainingCount() + " words remaining");
        }
        else{
            Intent intent = new Intent(this, QuestionActivity.class);
            startActivity(intent);
            finish();
        }
    }
}

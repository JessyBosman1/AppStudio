package com.example.jebo.mr_potato_head;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void checkBoxClick(View view) {

        switch(view.getId()){
            case R.id.checkBoxHat:
                if(((CheckBox) view).isChecked()) {
                    findViewById(R.id.imageHat).setVisibility(View.VISIBLE);
                }
                else{
                    findViewById(R.id.imageHat).setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.checkBoxGlasses:
                if(((CheckBox) view).isChecked()) {
                    findViewById(R.id.imageGlasses).setVisibility(View.VISIBLE);
                }
                else{
                    findViewById(R.id.imageGlasses).setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.checkBoxEyes:
                if(((CheckBox) view).isChecked()) {
                    findViewById(R.id.imageEyes).setVisibility(View.VISIBLE);
                }
                else{
                    findViewById(R.id.imageEyes).setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.checkBoxArms:
                if(((CheckBox) view).isChecked()) {
                    findViewById(R.id.imageArms).setVisibility(View.VISIBLE);
                }
                else{
                    findViewById(R.id.imageArms).setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.checkBoxMouth:
                if(((CheckBox) view).isChecked()) {
                    findViewById(R.id.imageMouth).setVisibility(View.VISIBLE);
                }
                else{
                    findViewById(R.id.imageMouth).setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.checkBoxBrows:
                if(((CheckBox) view).isChecked()) {
                    findViewById(R.id.imageBrows).setVisibility(View.VISIBLE);
                }
                else{
                    findViewById(R.id.imageBrows).setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.checkBoxEars:
                if(((CheckBox) view).isChecked()) {
                    findViewById(R.id.imageEars).setVisibility(View.VISIBLE);
                }
                else{
                    findViewById(R.id.imageEars).setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.checkBoxNose:
                if(((CheckBox) view).isChecked()) {
                    findViewById(R.id.imageNose).setVisibility(View.VISIBLE);
                }
                else{
                    findViewById(R.id.imageNose).setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.checkBoxMoustache:
                if(((CheckBox) view).isChecked()) {
                    findViewById(R.id.imageMoustache).setVisibility(View.VISIBLE);
                }
                else{
                    findViewById(R.id.imageMoustache).setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.checkBoxShoes:
                if(((CheckBox) view).isChecked()) {
                    findViewById(R.id.imageShoes).setVisibility(View.VISIBLE);
                }
                else{
                    findViewById(R.id.imageShoes).setVisibility(View.INVISIBLE);
                }
                break;

        }

    }
}

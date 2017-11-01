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


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("BoolHat", findViewById(R.id.imageHat).getVisibility());
        outState.putInt("BoolGlasses", findViewById(R.id.imageGlasses).getVisibility());
        outState.putInt("BoolEyes", findViewById(R.id.imageEyes).getVisibility());
        outState.putInt("BoolArms", findViewById(R.id.imageArms).getVisibility());
        outState.putInt("BoolMouth", findViewById(R.id.imageMouth).getVisibility());
        outState.putInt("BoolEyebrows", findViewById(R.id.imageBrows).getVisibility());
        outState.putInt("BoolEars", findViewById(R.id.imageEars).getVisibility());
        outState.putInt("BoolNose", findViewById(R.id.imageNose).getVisibility());
        outState.putInt("BoolMoustache", findViewById(R.id.imageMoustache).getVisibility());
        outState.putInt("BoolShoes", findViewById(R.id.imageShoes).getVisibility());
    }


    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState.getInt("BoolHat") == 0){
            findViewById(R.id.imageHat).setVisibility(View.VISIBLE);
        }
        else{
            findViewById(R.id.imageHat).setVisibility(View.INVISIBLE);
        }

        if (savedInstanceState.getInt("BoolGlasses") == 0){
            findViewById(R.id.imageGlasses).setVisibility(View.VISIBLE);
        }
        else{
            findViewById(R.id.imageGlasses).setVisibility(View.INVISIBLE);
        }

        if (savedInstanceState.getInt("BoolEyes") == 0){
            findViewById(R.id.imageEyes).setVisibility(View.VISIBLE);
        }
        else{
            findViewById(R.id.imageEyes).setVisibility(View.INVISIBLE);
        }

        if (savedInstanceState.getInt("BoolArms") == 0){
            findViewById(R.id.imageArms).setVisibility(View.VISIBLE);
        }
        else{
            findViewById(R.id.imageArms).setVisibility(View.INVISIBLE);
        }

        if (savedInstanceState.getInt("BoolMouth") == 0){
            findViewById(R.id.imageMouth).setVisibility(View.VISIBLE);
        }
        else{
            findViewById(R.id.imageMouth).setVisibility(View.INVISIBLE);
        }

        if (savedInstanceState.getInt("BoolEyebrows") == 0){
            findViewById(R.id.imageBrows).setVisibility(View.VISIBLE);
        }
        else{
            findViewById(R.id.imageBrows).setVisibility(View.INVISIBLE);
        }

        if (savedInstanceState.getInt("BoolEars") == 0){
            findViewById(R.id.imageEars).setVisibility(View.VISIBLE);
        }
        else{
            findViewById(R.id.imageEars).setVisibility(View.INVISIBLE);
        }

        if (savedInstanceState.getInt("BoolNose") == 0){
            findViewById(R.id.imageNose).setVisibility(View.VISIBLE);
        }
        else{
            findViewById(R.id.imageNose).setVisibility(View.INVISIBLE);
        }

        if (savedInstanceState.getInt("BoolMoustache") == 0){
            findViewById(R.id.imageMoustache).setVisibility(View.VISIBLE);
        }
        else{
            findViewById(R.id.imageMoustache).setVisibility(View.INVISIBLE);
        }

        if (savedInstanceState.getInt("BoolShoes") == 0){
            findViewById(R.id.imageShoes).setVisibility(View.VISIBLE);
        }
        else{
            findViewById(R.id.imageShoes).setVisibility(View.INVISIBLE);
        }

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

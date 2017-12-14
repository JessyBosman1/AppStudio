package com.example.jessy.famouscharactes;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionFragment extends Fragment implements View.OnClickListener{
    public Integer playerScore = 0;
    public Integer totalTime = 0;
    public Integer totalCorrect = 0;

    public String currentPerson;

    public ArrayList<String> nameList = new ArrayList<String>();
    public ArrayList<Data> dataList = new ArrayList<Data>();
    public ArrayList<String> nameListAsked = new ArrayList<String>();

    //private StorageReference mStorageRef;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();


    // prepare timer
    CountDownTimer timer = new CountDownTimer(30000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            try{updateTimerText(millisUntilFinished);}
            catch (Exception error){Log.d("timerError", error.toString());}

        }

        @Override
        public void onFinish() {
            totalTime += 30;
            checkGameState();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_question, container, false);

        Button buttonOption1 = view.findViewById(R.id.buttonOption1);
        Button buttonOption2 = view.findViewById(R.id.buttonOption2);
        Button buttonOption3 = view.findViewById(R.id.buttonOption3);
        Button buttonOption4 = view.findViewById(R.id.buttonOption4);

        buttonOption1.setOnClickListener(this);
        buttonOption2.setOnClickListener(this);
        buttonOption3.setOnClickListener(this);
        buttonOption4.setOnClickListener(this);

        // set toolbar and options.
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_revert);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnToStart();
            }
        });


        return view;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        getFromDatabase();
    }

    @Override
    public void onDestroyView() {
        timer.cancel();
        super.onDestroyView();

    }

    public void getFromDatabase() {

        checkConnection();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                //Person aCharacter = dataSnapshot.child("Person").child("Steve Jobs").getValue(Person.class);

                // loop to get all person names
                for (DataSnapshot snapshot : dataSnapshot.child("Person").getChildren()) {
                    Person aPerson = snapshot.getValue(Person.class);
                    //Log.d("dataSnap", aPerson.name);
                    addToArrays(aPerson.name, aPerson.image);

                }

                myRef.removeEventListener(this);


                getRandomPerson();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("dbError", " ", error.toException());
            }
        }
        );

    }

    public void addToArrays(String name, String image){
        nameList.add(name);
        dataList.add(new Data(name, image));
    }

    public void getRandomPerson(){
        Collections.shuffle(dataList);

        while(nameListAsked.contains(dataList.get(0).name)){
            Collections.shuffle(dataList);
        }

        currentPerson = dataList.get(0).name;
        placeContent(dataList.get(0).name, dataList.get(0).image);
    }

    public void checkConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
        }
        else {
            Toast.makeText(getActivity(), "It appears you are not connected to the internet", Toast.LENGTH_LONG).show();
        }
    }

    public void placeContent(final String name, String image){
        nameListAsked.add(name);

        final LinearLayout linearContainer = getActivity().findViewById(R.id.linearContainer);
        final Button buttonOption1 = getActivity().findViewById(R.id.buttonOption1);
        final Button buttonOption2 = getActivity().findViewById(R.id.buttonOption2);
        final Button buttonOption3 = getActivity().findViewById(R.id.buttonOption3);
        final Button buttonOption4 = getActivity().findViewById(R.id.buttonOption4);

        Log.d("dataSnap", playerScore.toString());

        final ImageView ImagePlaceholder = getActivity().findViewById(R.id.imageQuestionContainer);
        final ProgressBar ImageProgressBar = getActivity().findViewById(R.id.ImageProgressBar);

        ImageProgressBar.setVisibility(View.VISIBLE);

        checkConnection();

        Glide.with(getActivity())
                .load(image)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        ImageProgressBar.setVisibility(View.GONE);
                        return false; // important to return false so the error placeholder can be placed
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        ImageProgressBar.setVisibility(View.GONE);
                        timer.start();

                        // add 1 to progress of progressbar
                        ProgressBar progressBar = getActivity().findViewById(R.id.progressBar);
                        progressBar.incrementProgressBy(1);

                        ArrayList<String> questionAnswers = new ArrayList<String>();
                        questionAnswers.add(name);

                        while (questionAnswers.size() != 4){
                            String randomAnswer = nameList.get((new Random()).nextInt(nameList.size()));

                            if (questionAnswers.contains(randomAnswer)){

                            }
                            else {
                                questionAnswers.add(randomAnswer);
                            }
                        }

                        buttonOption1.setBackgroundColor(0xFF5DB4EE);
                        buttonOption2.setBackgroundColor(0xFF5DB4EE);
                        buttonOption3.setBackgroundColor(0xFF5DB4EE);
                        buttonOption4.setBackgroundColor(0xFF5DB4EE);

                        Collections.shuffle(questionAnswers);
                        buttonOption1.setText(questionAnswers.get(0));
                        buttonOption2.setText(questionAnswers.get(1));
                        buttonOption3.setText(questionAnswers.get(2));
                        buttonOption4.setText(questionAnswers.get(3));

                        linearContainer.setVisibility(View.VISIBLE);
                        return false;
                    }
                })
                .into(ImagePlaceholder);

    }

    public void updateTimerText(long millisUntilFinished){
        TextView countDownText = getActivity().findViewById(R.id.countDownText);
        countDownText.setText("" + millisUntilFinished / 1000);
        if(millisUntilFinished / 1000 > 20){
            countDownText.setTextColor(0xff669900);
        }
        else if(millisUntilFinished / 1000 <= 20 && millisUntilFinished/1000 > 10){
            countDownText.setTextColor(0xffff8800);
        }
        else if(millisUntilFinished / 1000 <= 10){
            countDownText.setTextColor(0xffff4444);
        }
    }

    @Override
    // Button onclick methods
    public void onClick(View v) {
        TextView countDownText = getActivity().findViewById(R.id.countDownText);
        switch (v.getId()) {
            case R.id.buttonOption1:
                Button buttonOption1 = getActivity().findViewById(R.id.buttonOption1);


                if (buttonOption1.getText() == currentPerson){
                    buttonOption1.setBackgroundColor(0xff99cc00);
                    playerScore += (Integer.parseInt(countDownText.getText().toString()));
                    totalCorrect += 1;
                }
                else{
                    buttonOption1.setBackgroundColor(0xffff4444);
                }
                totalTime += (30 - Integer.parseInt(countDownText.getText().toString()));
                checkGameState();
                break;

            case R.id.buttonOption2:
                Button buttonOption2 = getActivity().findViewById(R.id.buttonOption2);

                if (buttonOption2.getText() == currentPerson){
                    buttonOption2.setBackgroundColor(0xff99cc00);
                    playerScore += (Integer.parseInt(countDownText.getText().toString()));
                    totalTime += (30 - Integer.parseInt(countDownText.getText().toString()));
                    totalCorrect += 1;
                }
                else{
                    buttonOption2.setBackgroundColor(0xffff4444);
                }
                totalTime += (30 - Integer.parseInt(countDownText.getText().toString()));
                checkGameState();
                break;

            case R.id.buttonOption3:
                Button buttonOption3 = getActivity().findViewById(R.id.buttonOption3);

                if (buttonOption3.getText() == currentPerson){
                    buttonOption3.setBackgroundColor(0xff99cc00);
                    playerScore += (Integer.parseInt(countDownText.getText().toString()));
                    totalCorrect += 1;
                }
                else{
                    buttonOption3.setBackgroundColor(0xffff4444);
                }
                totalTime += (30 - Integer.parseInt(countDownText.getText().toString()));
                checkGameState();
                break;

            case R.id.buttonOption4:
                Button buttonOption4 = getActivity().findViewById(R.id.buttonOption4);

                if (buttonOption4.getText() == currentPerson){
                    buttonOption4.setBackgroundColor(0xff99cc00);
                    playerScore += (Integer.parseInt(countDownText.getText().toString()));
                    totalCorrect += 1;
                }
                else{
                    buttonOption4.setBackgroundColor(0xffff4444);
                }
                totalTime += (30 - Integer.parseInt(countDownText.getText().toString()));
                checkGameState();
                break;
        }

    }

    public void checkGameState(){
        timer.cancel();
        ProgressBar progressBar = getActivity().findViewById(R.id.progressBar);

        // if the amount of questions is not yet 10, get new question
        if(progressBar.getProgress() < 10){
            getRandomPerson();
        }
        // else go to the endgame screen
        else{
            FragmentManager fm = getActivity().getSupportFragmentManager();

            // add playerscore to Bundle, so it is available in the end game fragment.
            Bundle arguments = new Bundle();
            arguments.putInt("playerScore", playerScore);
            arguments.putInt("totalTime", totalTime);
            arguments.putInt("totalCorrect", totalCorrect);

            endGameFragment fragment = new endGameFragment();
            fragment.setArguments(arguments);

            fm.beginTransaction().replace(R.id.fragment_container, fragment).commit();
        }


    }

    public void returnToStart() {
        timer.cancel();
        FragmentManager fm = getActivity().getSupportFragmentManager();
        StartFragment fragment = new StartFragment();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, fragment, "StartFragment");
        ft.commit();

    }
}


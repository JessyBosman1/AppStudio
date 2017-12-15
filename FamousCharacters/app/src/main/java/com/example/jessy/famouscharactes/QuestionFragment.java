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


public class QuestionFragment extends Fragment implements View.OnClickListener{
    // Set variables to keep track of total point, total time and total correct questions.
    public Integer playerScore = 0;
    public Integer totalTime = 0;
    public Integer totalCorrect = 0;

    // Variable used to store the Character that is the right answer to the question.
    public String currentPerson;

    // set lists to store data of Questions.
    // nameList: list of all available names in the dataset.
    public ArrayList<String> nameList = new ArrayList<String>();
    // dataList: list of objects; Name, corresponding image.
    public ArrayList<Data> dataList = new ArrayList<Data>();
    // nameListAsked: Already answered characters, used to avoid double questions.
    public ArrayList<String> nameListAsked = new ArrayList<String>();

    // Get database reference.
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    // Prepare timer; used to set the time limit per question and calculate corresponding score.
    CountDownTimer timer = new CountDownTimer(30000, 1000) {
        @Override
        // Every tick; update textView to visualize countdown, change color depending on time left.
        public void onTick(long millisUntilFinished) {
            try{updateTimerText(millisUntilFinished);}
            catch (Exception error){Log.d("timerError", error.toString());}

        }
        @Override
        // If timer gets to 0
        public void onFinish() {
            // Add time of 30 seconds indicating that the user did not answer the question in time.
            totalTime += 30;
            // Function: get next question or end game if 10 questions have been asked.
            // necessary to make sure that the game continues if nu answer is given.
            checkGameState();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_question, container, false);

        // Find buttons and set onClick listeners.
        Button buttonOption1 = view.findViewById(R.id.buttonOption1);
        Button buttonOption2 = view.findViewById(R.id.buttonOption2);
        Button buttonOption3 = view.findViewById(R.id.buttonOption3);
        Button buttonOption4 = view.findViewById(R.id.buttonOption4);
        buttonOption1.setOnClickListener(this);
        buttonOption2.setOnClickListener(this);
        buttonOption3.setOnClickListener(this);
        buttonOption4.setOnClickListener(this);

        return view;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        // onCreate get all objects (name; image pairs) from Firebase.
        getFromDatabase();
    }

    @Override
    public void onDestroyView() {
        // If fragment is terminated, stop timer to avoid null references,
        // because timer does not exist but still ticking.
        timer.cancel();
        super.onDestroyView();

    }

    public void getFromDatabase() {
        // Before accessing database; check if mobile internet of wifi is running,
        // else give notification.
        checkConnection();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {


                // loop and add to arrays to get all person names
                for (DataSnapshot snapshot : dataSnapshot.child("Person").getChildren()) {
                    Person aPerson = snapshot.getValue(Person.class);
                    addToArrays(aPerson.name, aPerson.image);

                }
                // remove event listener to stop updating for new entries while playing the game.
                myRef.removeEventListener(this);

                // Function: Load first question with a random person.
                getRandomPerson();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("dbError", " ", error.toException());
            }
        });

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

    public void addToArrays(String name, String image){
        // add name to list of names, used to construct wrong answers to a question.
        nameList.add(name);
        // add name and image to list, used to construct right answer and corresponding image.
        dataList.add(new Data(name, image));
    }

    public void getRandomPerson(){
        // Shuffle/ randomize list of possible Characters to be questioned.
        Collections.shuffle(dataList);

        // First entry is a new random Character.
        // If character was already asked, get a random new one,
        // until a not already answered Character is selected.
        while(nameListAsked.contains(dataList.get(0).name)){
            Collections.shuffle(dataList);
        }

        // Set random person als current Character being asked.
        currentPerson = dataList.get(0).name;
        // Function: set name and image, and other random answers.
        placeContent(dataList.get(0).name, dataList.get(0).image);
    }

    //set name and image, and other random answers.
    public void placeContent(final String name, String image){
        // Add Character to list of characters that were already right answers in the quiz.
        nameListAsked.add(name);

        // Get Container reference ( initially hidden, to hide loading, not yet filled containers)
        final LinearLayout linearContainer = getActivity().findViewById(R.id.linearContainer);
        // Get button references.
        final Button buttonOption1 = getActivity().findViewById(R.id.buttonOption1);
        final Button buttonOption2 = getActivity().findViewById(R.id.buttonOption2);
        final Button buttonOption3 = getActivity().findViewById(R.id.buttonOption3);
        final Button buttonOption4 = getActivity().findViewById(R.id.buttonOption4);

        // get image and progressbar references.
        final ImageView ImagePlaceholder = getActivity().findViewById(R.id.imageQuestionContainer);
        final ProgressBar ImageProgressBar = getActivity().findViewById(R.id.ImageProgressBar);

        // set Progressbar visible ( used to set loading icon for still loading images).
        ImageProgressBar.setVisibility(View.VISIBLE);

        // check for internet connection, else give notification.
        checkConnection();

        // Glide used to load image.
        Glide.with(getActivity())
                .load(image)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    // If error, Hide Progressbar and throw Exception.
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        ImageProgressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    // If image is loaded, hide loading icon and start timer for question.
                    public boolean onResourceReady(GlideDrawable resource,
                                                   String model,
                                                   Target<GlideDrawable> target,
                                                   boolean isFromMemoryCache,
                                                   boolean isFirstResource) {
                        ImageProgressBar.setVisibility(View.GONE);
                        timer.start();

                        // get bottom Progressbar, and add 1. (range 0-10)
                        // Used to keep track for the user of how many questions had been answered.
                        ProgressBar progressBar = getActivity().findViewById(R.id.progressBar);
                        progressBar.incrementProgressBy(1);

                        // Create list of possible answers to the current question,
                        // and add correct answer as possible answer.
                        ArrayList<String> questionAnswers = new ArrayList<String>();
                        questionAnswers.add(name);

                        // Add random names to possible questions,
                        // until 4 answers(including the correct one) are generated.
                        while (questionAnswers.size() != 4){
                            String randomAnswer = nameList.get((new Random()).nextInt(nameList.size()));
                            // If name is already in answers, skip name to avoid doubles,
                            // and get a new one in the next iteration.
                            if (questionAnswers.contains(randomAnswer)){}
                            else {questionAnswers.add(randomAnswer);}
                        }

                        // set the colors of the buttons back to standard blue after each question.
                        buttonOption1.setBackgroundColor(0xFF5DB4EE);
                        buttonOption2.setBackgroundColor(0xFF5DB4EE);
                        buttonOption3.setBackgroundColor(0xFF5DB4EE);
                        buttonOption4.setBackgroundColor(0xFF5DB4EE);

                        // Set answers in a random order, to avoid the first entry always being
                        // the correct answer. Then add the names to the buttons.
                        Collections.shuffle(questionAnswers);
                        buttonOption1.setText(questionAnswers.get(0));
                        buttonOption2.setText(questionAnswers.get(1));
                        buttonOption3.setText(questionAnswers.get(2));
                        buttonOption4.setText(questionAnswers.get(3));

                         // Show end result, with generated content:
                        // random image, with 4 random answers, one being the correct one.
                        linearContainer.setVisibility(View.VISIBLE);

                        // Return false to notify glide image is done loading.
                        return false;
                    }
                })
                .into(ImagePlaceholder);

    }
    // Function called every second when timer is active.
    public void updateTimerText(long millisUntilFinished){
        // Set remaining time to textbox, after converting from miliseconds to seconds.
        TextView countDownText = getActivity().findViewById(R.id.countDownText);
        countDownText.setText("" + millisUntilFinished / 1000);

        // Change Text color of countdown:
        // If more than 20 seconds remaining >> Green
        // If between 20 and 10 seconds remaining >> Orange
        // If 10 seconds or less remaining >> Red
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
        // Get countDown text reference, to get remaining time to calculate points.
        TextView countDownText = getActivity().findViewById(R.id.countDownText);
        switch (v.getId()) {
            case R.id.buttonOption1:
                Button buttonOption1 = getActivity().findViewById(R.id.buttonOption1);
                // if clicked on button; check if answer was correct.
                // If correct; turn button green and add 1 to correct answers.
                if (buttonOption1.getText() == currentPerson){
                    buttonOption1.setBackgroundColor(0xff99cc00);
                    playerScore += (Integer.parseInt(countDownText.getText().toString()));
                    totalCorrect += 1;
                }
                // If not correct; turn button red.
                else{
                    buttonOption1.setBackgroundColor(0xffff4444);
                }
                // Add time remaining to total time.
                totalTime += (30 - Integer.parseInt(countDownText.getText().toString()));
                // If 10 questions has been asked go to results, else ask new question.
                checkGameState();
                break;

            case R.id.buttonOption2:
                Button buttonOption2 = getActivity().findViewById(R.id.buttonOption2);
                // if clicked on button; check if answer was correct.
                // If correct; turn button green and add 1 to correct answers
                // and add time remaining to total time.
                if (buttonOption2.getText() == currentPerson){
                    buttonOption2.setBackgroundColor(0xff99cc00);
                    playerScore += (Integer.parseInt(countDownText.getText().toString()));
                    totalCorrect += 1;
                }
                // If not correct; turn button red.
                else{
                    buttonOption2.setBackgroundColor(0xffff4444);
                }
                // Add time remaining to total time.
                totalTime += (30 - Integer.parseInt(countDownText.getText().toString()));
                // If 10 questions has been asked go to results, else ask new question.
                checkGameState();
                break;

            case R.id.buttonOption3:
                Button buttonOption3 = getActivity().findViewById(R.id.buttonOption3);
                // if clicked on button; check if answer was correct.
                // If correct; turn button green and add 1 to correct answers.
                // and add time remaining to total time.
                if (buttonOption3.getText() == currentPerson){
                    buttonOption3.setBackgroundColor(0xff99cc00);
                    playerScore += (Integer.parseInt(countDownText.getText().toString()));
                    totalCorrect += 1;
                }
                // If not correct; turn button red.
                else{
                    buttonOption3.setBackgroundColor(0xffff4444);
                }
                // Add time remaining to total time.
                totalTime += (30 - Integer.parseInt(countDownText.getText().toString()));
                // If 10 questions has been asked go to results, else ask new question.
                checkGameState();
                break;

            case R.id.buttonOption4:
                Button buttonOption4 = getActivity().findViewById(R.id.buttonOption4);
                // if clicked on button; check if answer was correct.
                // If correct; turn button green and add 1 to correct answers.
                // and add time remaining to total time.
                if (buttonOption4.getText() == currentPerson){
                    buttonOption4.setBackgroundColor(0xff99cc00);
                    playerScore += (Integer.parseInt(countDownText.getText().toString()));
                    totalCorrect += 1;
                }
                // If not correct; turn button red.
                else{
                    buttonOption4.setBackgroundColor(0xffff4444);
                }
                // Add time remaining to total time.
                totalTime += (30 - Integer.parseInt(countDownText.getText().toString()));
                // If 10 questions has been asked go to results, else ask new question.
                checkGameState();
                break;
        }

    }

    // Function; If 10 questions has been asked go to results, else ask new question.
    public void checkGameState(){
        // Stop timer for current question.
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

            // go to endGame / results fragment.
            endGameFragment fragment = new endGameFragment();
            fragment.setArguments(arguments);

            fm.beginTransaction().replace(R.id.fragment_container, fragment).commit();
        }
    }

}


package com.example.jessy.famouscharactes;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginFragment extends Fragment implements View.OnClickListener{
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment.
        View view =  inflater.inflate(R.layout.fragment_login, container, false);

        // Get buttons and set onclick listeners.
        Button buttonLogin = view.findViewById(R.id.buttonLogin);
        Button buttonRegister = view.findViewById(R.id.buttonRegister);
        buttonLogin.setOnClickListener(this);
        buttonRegister.setOnClickListener(this);

        return view;
    }

    @Override
    // Detach handler
    public void onDetach() {
        super.onDetach();
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
    }

    // Function to ADD user to the database.
    public void createUser(final String email, final String password) {
        // Check if input is nog empty.
        if (email != " " || password != " ") {
            // create User in Firebase.
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Create is successful, show Toast.
                                Log.d("create user", email + " // " + password);
                                showMessage("create successful");

                            } else {
                                // Create failed, Toast Error.
                                Log.d("create user", "faled" + task.getException());
                                showMessage(task.getException().toString());
                            }

                        }
                    });
        }
        else {
            // If input is empty; Display empty field message.
            showMessage("Error; empty field");
        }
    }

    // Function to LOGIN user.
    public void loginUser(final String email, final String password){
        // Check if input is nog empty.
        if(email != " " || password != " ") {
            // Login user with input.
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                // Sign in success, Toast successful.
                                Log.d("login", "signInWithEmail:success");
                                showMessage("login successful");

                                // Send user back to main menu when succesfully logged in.
                                FragmentManager fm = getActivity().getSupportFragmentManager();
                                StartFragment fragment = new StartFragment();
                                FragmentTransaction ft = fm.beginTransaction();
                                ft.replace(R.id.fragment_container, fragment, "StartFragment");
                                ft.commit();

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.d("login", "signInWithEmail:failure", task.getException());
                                showMessage(task.getException().toString());
                            }

                        }
                    });
        }
        else{
            // If input is empty; Display empty field message.
            showMessage("Error; empty field");}
    }

    // Function to display a message.
    public void showMessage(String message){
            Toast.makeText(getActivity(),message,Toast.LENGTH_LONG).show();
    }

    @Override
    // Handle onClick events.
    public void onClick(View v) {
        // Find input fields.
        TextView textEmail = getActivity().findViewById(R.id.textEmail);
        TextView textPassword = getActivity().findViewById(R.id.textPassword);

        // Get text from input fields.
        String inputEmail = textEmail.getText().toString();
        String inputPassword = textPassword.getText().toString();

        switch (v.getId()) {
            // If clicked on login button
            case R.id.buttonLogin:
                // If a field is empty, send empty to trigger exception and messages.
                if(TextUtils.isEmpty(inputEmail) || TextUtils.isEmpty(inputPassword)) {
                    loginUser(" ", " ");
                    return;
                }
                // if both fields are filled, try to login user with input.
                else{
                    loginUser(inputEmail, inputPassword);
                }
                break;

            // If clicked on register button
            case R.id.buttonRegister:
                // If a field is empty, send empty to trigger exception and messages.
                if(TextUtils.isEmpty(inputEmail) || TextUtils.isEmpty(inputPassword)) {
                    createUser(" ", " ");
                    return;
                }
                else{
                    // if both fields are filled, try to register user with input.
                    createUser(inputEmail, inputPassword);
                }
                break;

        }
    }
}


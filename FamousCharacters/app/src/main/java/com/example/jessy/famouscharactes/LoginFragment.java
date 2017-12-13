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

import org.w3c.dom.Text;

import java.util.concurrent.Executor;

public class LoginFragment extends Fragment implements View.OnClickListener{
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view =  inflater.inflate(R.layout.fragment_login, container, false);

        Button buttonLogin = view.findViewById(R.id.buttonLogin);
        Button buttonRegister = view.findViewById(R.id.buttonRegister);

        buttonLogin.setOnClickListener(this);
        buttonRegister.setOnClickListener(this);

        return view;
    }

    @Override
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
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    public void createUser(final String email, final String password) {
        if (email != " " || password != " ") {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("create user", email + " // " + password);
                                FirebaseUser user = mAuth.getCurrentUser();
                                showMessage("create successful");

                            } else {
                                Log.d("create user", "faled" + task.getException());
                                showMessage(task.getException().toString());
                            }

                        }
                    });
        } else {
            showMessage("Error; empty field");
        }
    }
    public void loginUser(final String email, final String password){
        if(email != " " || password != " ") {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("login", "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                showMessage("login successful");

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

        } else{showMessage("Error; empty field");}
    }

public void showMessage(String message){
        Toast.makeText(getActivity(),message,Toast.LENGTH_LONG).show();

    }

    @Override
    public void onClick(View v) {
        TextView textEmail = getActivity().findViewById(R.id.textEmail);
        TextView textPassword = getActivity().findViewById(R.id.textPassword);

        String inputEmail = textEmail.getText().toString();
        String inputPassword = textPassword.getText().toString();

        switch (v.getId()) {
            case R.id.buttonLogin:
                if(TextUtils.isEmpty(inputEmail) || TextUtils.isEmpty(inputPassword)) {
                    loginUser(" ", " ");
                    return;
                }
                else{
                    loginUser(inputEmail, inputPassword);
                }
                break;

            case R.id.buttonRegister:
                if(TextUtils.isEmpty(inputEmail) || TextUtils.isEmpty(inputPassword)) {
                    createUser(" ", " ");
                    return;
                }
                else{
                    createUser(inputEmail, inputPassword);
                }
                break;

        }
    }
}


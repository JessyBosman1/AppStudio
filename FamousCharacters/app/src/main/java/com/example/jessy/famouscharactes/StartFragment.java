package com.example.jessy.famouscharactes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class StartFragment extends Fragment implements View.OnClickListener{
    public View view;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_start, container, false);

        // get references to textView and buttons.
        TextView loginText = view.findViewById(R.id.loginText);
        Button buttonLogin = view.findViewById(R.id.buttonLogin);
        Button buttonLogout = view.findViewById(R.id.buttonLogout);

        // Check if a user is logged in.
        if (user != null){
            // Display email of currently logged in user.
            loginText.setText("Logged in as " + user.getEmail());
            // Set login button invisible(because a user is logged in),
            // and show logout button.
            // Disable and enable corresponding clickability.
            buttonLogin.setVisibility(View.INVISIBLE);
            buttonLogin.setClickable(false);
            buttonLogout.setVisibility(View.VISIBLE);
            buttonLogout.setClickable(true);
        }
        else{
            // Set login button Visible(because no user is logged in),
            // and hide logout button.
            // Disable and enable corresponding clickability.
            buttonLogin.setVisibility(View.VISIBLE);
            buttonLogin.setClickable(true);
            buttonLogout.setVisibility(View.INVISIBLE);
            buttonLogout.setClickable(false);
        }
        // set onclick listener to buttons.
        buttonLogin.setOnClickListener(this);
        buttonLogout.setOnClickListener(this);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    // On button click handler
    public void onClick(View v) {
        // Get references.
        Button buttonLogin = getActivity().findViewById(R.id.buttonLogin);
        Button buttonLogout = getActivity().findViewById(R.id.buttonLogout);
        TextView loginText = view.findViewById(R.id.loginText);

        switch (v.getId()) {
            case R.id.buttonLogin:
                // If login button clicked, go to login screen.
                FragmentManager fm = getActivity().getSupportFragmentManager();
                LoginFragment fragment = new LoginFragment();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment_container, fragment, "LoginFragment");
                ft.commit();
                break;

            case R.id.buttonLogout:
                // If logout button clicked,
                // Sign user out in firebase and get null user.
                FirebaseAuth.getInstance().signOut();
                user = FirebaseAuth.getInstance().getCurrentUser();

                // Clear currently logged in text.
                loginText.setText("");
                // Hide Logout and show Login button.
                buttonLogin.setVisibility(View.VISIBLE);
                buttonLogin.setClickable(true);
                buttonLogout.setVisibility(View.INVISIBLE);
                buttonLogout.setClickable(false);
                break;
        }
    }


}

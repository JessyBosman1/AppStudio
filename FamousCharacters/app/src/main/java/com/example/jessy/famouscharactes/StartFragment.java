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
    //
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_start, container, false);

        TextView loginText = view.findViewById(R.id.loginText);

        Button buttonLogin = view.findViewById(R.id.buttonLogin);
        Button buttonLogout = view.findViewById(R.id.buttonLogout);

        if (user != null){
            loginText.setText("Logged in as " + user.getEmail());
            buttonLogin.setVisibility(View.INVISIBLE);
            buttonLogin.setClickable(false);
            buttonLogout.setVisibility(View.VISIBLE);
            buttonLogout.setClickable(true);
        }
        else{
            buttonLogin.setVisibility(View.VISIBLE);
            buttonLogin.setClickable(true);
            buttonLogout.setVisibility(View.INVISIBLE);
            buttonLogout.setClickable(false);
        }

        buttonLogin.setOnClickListener(this);
        buttonLogout.setOnClickListener(this);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onClick(View v) {
        Button buttonLogin = getActivity().findViewById(R.id.buttonLogin);
        Button buttonLogout = getActivity().findViewById(R.id.buttonLogout);
        TextView loginText = view.findViewById(R.id.loginText);

        switch (v.getId()) {
            case R.id.buttonLogin:
                FragmentManager fm = getActivity().getSupportFragmentManager();
                LoginFragment fragment = new LoginFragment();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment_container, fragment, "LoginFragment");
                ft.commit();
                break;

            case R.id.buttonLogout:
                FirebaseAuth.getInstance().signOut();
                user = FirebaseAuth.getInstance().getCurrentUser();

                loginText.setText("");
                buttonLogin.setVisibility(View.VISIBLE);
                buttonLogin.setClickable(true);
                buttonLogout.setVisibility(View.INVISIBLE);
                buttonLogout.setClickable(false);

                break;
        }
    }


}

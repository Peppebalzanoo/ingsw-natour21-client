package com.example.natour2.fragment.loginSignin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.natour2.R;
import com.example.natour2.controller.ControllerLoginSignin;
import com.example.natour2.utilities.PreferanceManager;

public class SignupFragment extends Fragment {


    // ############################################################# View Components
    private EditText usernameSignUp;        // Username
    private EditText emailSignUp;           // Email
    private EditText passwordSignUp;        // Password
    private EditText repeatPassSignUp;      // RepeatPassword
    private Button btnRegister;
    private TextView alreadyHaveAccount;
    private ProgressBar progressBar;
    // ############################################################# End View Components

    private final ControllerLoginSignin ctrl = ControllerLoginSignin.getInstance();

    public SignupFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctrl.setActivity(getActivity());
        ctrl.setContext(getActivity().getApplicationContext());
        ctrl.setFragmentManager(getActivity().getSupportFragmentManager());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_signup, container, false);
        initViewComponents(view);
        return view;
    }


    private void initViewComponents(View view){

        usernameSignUp = view.findViewById(R.id.usernameSignUp) ;
        emailSignUp = view.findViewById(R.id.emailSignUp);
        passwordSignUp = view.findViewById(R.id.passwordSignUp);
        repeatPassSignUp = view.findViewById(R.id.repeatPassSignUp);;
        btnRegister = view.findViewById(R.id.btnRegister);
        alreadyHaveAccount = view.findViewById(R.id.alreadyHaveAccount);
        progressBar = view.findViewById(R.id.progessBar);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(passwordSignUp.getText().toString().equals(repeatPassSignUp.getText().toString())){
                    ctrl.signup(usernameSignUp.getText().toString().replace(" ", ""),
                            emailSignUp.getText().toString().replace(" ", ""),
                            passwordSignUp.getText().toString());
                } else {
                    Toast.makeText(getActivity(), "Errore! Le password non coencidono",
                            Toast.LENGTH_LONG).show();
                }
            }
        });


        alreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ctrl.showLoginFragment();
            }
        });

    }
}
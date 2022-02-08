package com.example.natour2.fragment.loginSignin;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.natour2.R;
import com.example.natour2.controller.ControllerLoginSignin;
import com.example.natour2.utilities.Constants;
import com.example.natour2.utilities.PreferanceManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.messaging.FirebaseMessaging;


public class LoginFragment extends Fragment {

    private Button btnSignUp;
    private TextView txtForgetPass;
    private Button btnLogin;
    private EditText etUsername;
    private EditText etPassword;
    private ProgressBar progressBar;
    private TextView bntAccessoAdmin;

    private final ControllerLoginSignin ctrl = ControllerLoginSignin.getInstance();
    private FirebaseAnalytics analytics;
    private PreferanceManager preferanceManager;


    public LoginFragment() { }

    public LoginFragment(FirebaseAnalytics analytics){
        this.analytics = analytics;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferanceManager = new PreferanceManager(getActivity().getApplicationContext());
        ctrl.setActivity(getActivity());
        ctrl.setContext(getActivity().getApplicationContext());
        ctrl.setFragmentManager(getActivity().getSupportFragmentManager());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_login, container, false);

        initViewComponents(view);

        return view;
    }

    private void initViewComponents(View view){

        btnSignUp = view.findViewById(R.id.btnSignUp);
        txtForgetPass= view.findViewById(R.id.forgetPassword);
        btnLogin = view.findViewById(R.id.btnLogin);
        etUsername = view.findViewById(R.id.etUsernameLogin);
        etPassword = view.findViewById(R.id.etPasswordLogin);
        progressBar = view.findViewById(R.id.progessBarLogIn);
        bntAccessoAdmin = view.findViewById(R.id.buttonAdminLoginFragment);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }
                        preferanceManager.putString(Constants.KEY_FCM_TOKEN, task.getResult());
                    }
                });


        bntAccessoAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ctrl.showAdminFragment();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                ctrl.showSignupFragment();
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               ctrl.login(etUsername.getText().toString().replace(" ", ""), etPassword.getText().toString());
            }
        });

        txtForgetPass.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                ctrl.showForgetPasswordFragment();
            }
        });

    }
}
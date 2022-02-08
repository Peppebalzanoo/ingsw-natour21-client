package com.example.natour2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.natour2.controller.ControllerLoginSignin;
import com.example.natour2.utilities.Constants;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    private String Firebasetoken = "";
    private FirebaseAnalytics analytics;
    private final ControllerLoginSignin ctrl = ControllerLoginSignin.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        analytics = FirebaseAnalytics.getInstance(this);

        ControllerLoginSignin ctrl = ControllerLoginSignin.getInstance();
        ctrl.setAnalytics(analytics);
        ctrl.setFragmentManager(getSupportFragmentManager());
        ctrl.showLoginFragment();
    }

}
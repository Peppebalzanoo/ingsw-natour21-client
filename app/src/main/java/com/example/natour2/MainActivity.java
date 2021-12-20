package com.example.natour2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.example.natour2.controller.ControllerLoginSignin;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getSupportActionBar().hide();

        try {
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.configure(getApplicationContext());

        } catch (AmplifyException error) {
            System.out.println(error.toString());
            error.printStackTrace();
        }

        ControllerLoginSignin ctrl = ControllerLoginSignin.getInstance();
        ctrl.setFragmentManager(getSupportFragmentManager());
        ctrl.showLoginFragment();
    }



}
package com.example.natour2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.natour2.controller.ControllerLoginSignin;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getSupportActionBar().hide();

        ControllerLoginSignin ctrl = new ControllerLoginSignin();

        ctrl.showLoginFragment(getSupportFragmentManager());
    }



}
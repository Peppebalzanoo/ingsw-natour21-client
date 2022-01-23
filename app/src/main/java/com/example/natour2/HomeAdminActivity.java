package com.example.natour2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.natour2.controller.ControllerAdminActivity;


public class HomeAdminActivity extends AppCompatActivity {

    private final ControllerAdminActivity ctrl = ControllerAdminActivity.getInstance();

    public HomeAdminActivity() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);
        this.getSupportActionBar().hide();

        ctrl.showControlPanelFragment(getSupportFragmentManager());
    }


}
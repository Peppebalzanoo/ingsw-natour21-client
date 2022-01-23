package com.example.natour2.controller;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.natour2.fragment.AdminFragment;
import com.example.natour2.fragment_admin.ControlPanelFragment;
import com.example.natour2.R;
import com.example.natour2.fragment_admin.NewsLetterFragment;
import com.example.natour2.fragment_admin.SegnalazioneFragment;

public class ControllerAdminActivity {

    private static ControllerAdminActivity controllerInstance;

    private ControllerAdminActivity(){ }

    public static ControllerAdminActivity getInstance(){
        if(controllerInstance == null) {
            controllerInstance = new ControllerAdminActivity();
        }
        return controllerInstance;
    }


    public void showControlPanelFragment(FragmentManager fragmentManager){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout_admin, new ControlPanelFragment()); // give your fragment container id in first parameter
        transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
        transaction.commit();
    }

    public void showSegnalazioneFragment(FragmentManager fragmentManager, String string){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout_admin, new SegnalazioneFragment(string)); // give your fragment container id in first parameter
        transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
        transaction.commit();
    }

    public void showNewsLettersFragment(FragmentManager fragmentManager){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout_admin, new NewsLetterFragment()); // give your fragment container id in first parameter
        transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
        transaction.commit();
    }

    public void showAdminFrament(FragmentManager fragmentManager){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout_admin, new AdminFragment()); // give your fragment container id in first parameter
        transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
        transaction.commit();
    }



}

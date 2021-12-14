package com.example.natour2.controller;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.natour2.R;
import com.example.natour2.fragment.HomeFragment;
import com.example.natour2.fragment.NotificationFragment;
import com.example.natour2.fragment.ProfileFragment;
import com.example.natour2.fragment.SearchFragment;
import com.example.natour2.fragment.loginSignin.LoginFragment;
import com.example.natour2.fragment.loginSignin.SignupFragment;
import com.example.natour2.fragment.loginSignin.VerifyCodeFragment;

public class ControllerHomeAcrtivity {

    public ControllerHomeAcrtivity(){

    }

    public void showHomeFragment(FragmentManager fragmentManager){
        //activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new SignupFragment()).commit();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout_home, new HomeFragment()); // give your fragment container id in first parameter
        transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
        transaction.commit();
    }

    public void showProfileFragment(FragmentManager fragmentManager){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout_home, new ProfileFragment()); // give your fragment container id in first parameter
        transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
        transaction.commit();
    }

    public void showSearchFragment(FragmentManager fragmentManager){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout_home, new SearchFragment()); // give your fragment container id in first parameter
        transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
        transaction.commit();
    }

    public void showNotificationFragment(FragmentManager fragmentManager){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout_home, new NotificationFragment()); // give your fragment container id in first parameter
        transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
        transaction.commit();
    }


}

package com.example.natour2.controller;

import android.app.Activity;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.natour2.R;
import com.example.natour2.fragment.AdminFragment;
import com.example.natour2.fragment.BaseFragment;
import com.example.natour2.fragment.ChatFragment;
import com.example.natour2.fragment.HomeFragment;
import com.example.natour2.fragment.NotificationFragment;
import com.example.natour2.fragment.ProfileFragment;
import com.example.natour2.fragment.SearchFragment;
import com.example.natour2.fragment.SelectUsersFragment;
import com.example.natour2.fragment.UserFragment;
import com.example.natour2.fragment.loginSignin.LoginFragment;
import com.example.natour2.fragment.loginSignin.SignupFragment;
import com.example.natour2.fragment.loginSignin.VerifyCodeFragment;
import com.example.natour2.model.ChatMessage;
import com.example.natour2.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

import java.util.ArrayList;
import java.util.List;

public class ControllerHomeAcrtivity {
    private final ControllerLoginSignin controllerLoginSignin = ControllerLoginSignin.getInstance();


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
        transaction.replace(R.id.frame_layout_home,  new ProfileFragment()); // give your fragment container id in first parameter
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

    public void showUserFragment(FragmentManager fragmentManager){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout_home, new UserFragment()); // give your fragment container id in first parameter
        transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
        transaction.commit();
    }


    public void showSelectUserFragment(FragmentManager fragmentManager){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout_home, new SelectUsersFragment()); // give your fragment container id in first parameter
        transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
        transaction.commit();
    }

    public void showChatFragment(FragmentManager fragmentManager){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout_home, new ChatFragment()); // give your fragment container id in first parameter
        transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
        transaction.commit();
    }

    public void showChatFragment(FragmentManager fragmentManager, ChatFragment c){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout_home, c); // give your fragment container id in first parameter
        transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
        transaction.commit();
    }



    //Mi serve per passare uno user da SelectUserFragment a ChatFragment
    //Ovviamente è una soluzione momentanea
    public ChatFragment setUser(User u){
        ChatFragment c = new ChatFragment();
        c.pippo(u);
        return c;
    }

}

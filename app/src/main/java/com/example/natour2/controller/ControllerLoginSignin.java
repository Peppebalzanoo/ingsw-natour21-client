/*
 * Developed by Keivan Kiyanfar on 23/11/21, 15:44
 * Last modified 23/11/21, 15:44
 * Copyright (c) 2021. All rights reserved.
 */

package com.example.natour2.controller;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.natour2.HomeActivity2;
import com.example.natour2.fragment.loginSignin.LoginFragment;
import com.example.natour2.R;
import com.example.natour2.fragment.loginSignin.SignupFragment;
import com.example.natour2.fragment.loginSignin.VerifyCodeFragment;
import com.example.natour2.auth.Cognito;

public class ControllerLoginSignin {

    private static String username = null;
    private static FragmentManager fragmentManager = null;

    public ControllerLoginSignin(){

    }

    public void login(String username, String passowrd, Context context){
        Cognito authentication = new Cognito(context);
        authentication.userLogIn(username, passowrd);
    }

    public void signup(String username, String email, String password, Context context, FragmentManager fragmentManager){
        this.username = username;
        this.fragmentManager = fragmentManager;
        Cognito authentication = new Cognito(context);
        authentication.aggiungiAttributi("name", username);
        authentication.aggiungiAttributi("email", email);
        authentication.userSignUpInBackground(username, password);
    }

    public void verifyCode(String code, Context context, FragmentManager fragmentManager){
        this.fragmentManager = fragmentManager;
        Cognito authentication = new Cognito(context);
        authentication.confirmVerificationCodeUser(username, code);

    }

    public void showHomeActivity(Context context){
        Intent i = new Intent(context, HomeActivity2.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    public void showSignupFragment(FragmentManager fragmentManager){
        //activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new SignupFragment()).commit();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout, new SignupFragment() ); // give your fragment container id in first parameter
        transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
        transaction.commit();
    }

    public void showLoginFragment(FragmentManager fragmentManager){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout, new LoginFragment()); // give your fragment container id in first parameter
        transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
        transaction.commit();
    }

    public void showLoginFragment(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout, new LoginFragment()); // give your fragment container id in first parameter
        transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
        transaction.commit();
    }

    public void showVerifyCodeFragment(){

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout, new VerifyCodeFragment()); // give your fragment container id in first parameter
        transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
        transaction.commit();


    }

}

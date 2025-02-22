package com.example.natour2.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.natour2.HomeActivity;
import com.example.natour2.MainActivity;
import com.example.natour2.auth.Cognito;
import com.example.natour2.dao.UserDao;
import com.example.natour2.fragment.AdminFragment;
import com.example.natour2.HomeAdminActivity;
import com.example.natour2.fragment.loginSignin.ForgetPasswordFragment;
import com.example.natour2.fragment.loginSignin.LoginFragment;
import com.example.natour2.R;
import com.example.natour2.fragment.loginSignin.SignupFragment;
import com.example.natour2.fragment.loginSignin.VerifyCodeFragment;
import com.example.natour2.model.User;
import com.example.natour2.utilities.RetrofitInstance;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.io.IOException;

import retrofit2.Call;


public class ControllerLoginSignin {

    private Activity activity = null;
    private Context context = null;
    private FragmentManager fragmentManager = null;
    private String username;
    private String email;
    private String matricolaAdim;
    private String password;

    private FirebaseAnalytics analytics;


    private static ControllerLoginSignin ctrlInstance;

    private ControllerLoginSignin(){ }

    public static ControllerLoginSignin getInstance(){
        if(ctrlInstance == null) {
            ctrlInstance = new ControllerLoginSignin();
        }
        return ctrlInstance;
    }


    public void login(String username, String passowrd){
        Cognito authentication = new Cognito(context, activity);
        authentication.userLogIn(username, passowrd);

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.METHOD, "login");
        analytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle);
    }


    public void signup(String username, String email, String password){

        this.username = username;
        this.email = email;
        this.password = password;

        Cognito authentication = new Cognito(context, activity);
        authentication.addAttributes("name", username);
        authentication.addAttributes("email", email);
        authentication.userSignUpInBackground(username, password);

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.METHOD, "signup");
        analytics.logEvent(FirebaseAnalytics.Event.SIGN_UP, bundle);
    }


    public void verifyCode(String code){
        Cognito authentication = new Cognito(context, activity);
        authentication.confirmVerificationCodeUser(username, code);
        Bundle bundle = new Bundle();
        analytics.logEvent("verify_code", bundle);

    }

    public void forgetPassword(String userID, String vecchiaPassword, String nuovaPassword){
        Cognito authentication = new Cognito(context, activity);
        authentication.forgetPasswordCognito(userID, vecchiaPassword, nuovaPassword);

        Bundle bundle = new Bundle();
        analytics.logEvent("forgot_password", bundle);

    }

    public void showHomeActivity(Context c){
        Intent i = new Intent(c, HomeActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        c.startActivity(i);
    }

    public void showHomeAdminActivity(Context c){
        Intent i = new Intent(c, HomeAdminActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
        c.startActivity(i);
    }


    public void showSignupFragment(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout, new SignupFragment() );
        transaction.addToBackStack(null);
        transaction.commit();
    }


    public void showLoginFragment(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout, new LoginFragment(analytics));
        transaction.addToBackStack(null);
        transaction.commit();
    }


    public void showAdminFragment(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout, new AdminFragment()); // give your fragment container id in first parameter
        transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
        transaction.commit();
    }

    public void showVerifyCodeFragment(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout, new VerifyCodeFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void showForgetPasswordFragment(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout, new ForgetPasswordFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }


    public void loginAdmin(String matricolaAdmin, String passwordAdmin) {
        signupAdmin(matricolaAdmin, passwordAdmin);
        login(matricolaAdmin, passwordAdmin);
    }

    public void signupAdmin(String username, String password){
        this.matricolaAdim = username;
    }

    public void setActivity(Activity activity){
        this.activity = activity;
    }


    public void setContext(Context context){
        this.context = context;
    }


    public void setFragmentManager(FragmentManager fragmentManager){
        this.fragmentManager = fragmentManager;
    }


    public void setAnalytics(FirebaseAnalytics analytics) {
        this.analytics = analytics;
    }


    public void firstLogin() {
        Cognito authentication = new Cognito(context, activity);
        authentication.firstUserLogIn(username, password);

    }
}



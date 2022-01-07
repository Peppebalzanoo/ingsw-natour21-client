package com.example.natour2.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.natour2.HomeActivity;
import com.example.natour2.amplify.AuthAmplify;
import com.example.natour2.fragment.ChatFragment;
import com.example.natour2.fragment.loginSignin.LoginFragment;
import com.example.natour2.R;
import com.example.natour2.fragment.loginSignin.SignupFragment;
import com.example.natour2.fragment.loginSignin.VerifyCodeFragment;


public class ControllerLoginSignin {

    private static ControllerLoginSignin ctrlInstance;
    private Activity activity = null;
    private Context context = null;
    private FragmentManager fragmentManager = null;
    private String email = null;
    private AuthAmplify authAmplify = new AuthAmplify();

    private ControllerLoginSignin(){ }

    public static ControllerLoginSignin getInstance(){
        if(ctrlInstance == null)
            ctrlInstance = new ControllerLoginSignin();

        return ctrlInstance;
    }


    public void signup(String username, String email, String password){
        /*
        this.username = username;
        this.fragmentManager = fragmentManager;
        Cognito authentication = new Cognito(context);
        authentication.aggiungiAttributi("name", username);
        authentication.aggiungiAttributi("email", email);
        authentication.userSignUpInBackground(username, password);
         */
        this.email = email;
        authAmplify.signUp(username, email, password);

    }


    public void verifyCode(String code){
        //Cognito authentication = new Cognito(context);
        //authentication.confirmVerificationCodeUser(username, code);
        authAmplify.confirmSignUp(this.email, code);

    }


    public void login(String username, String passowrd){
        //Cognito authentication = new Cognito(context);
        //authentication.userLogIn(username, passowrd);
        authAmplify.signIn(username, passowrd);

    }


    public void printToast(String str){
        activity.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(activity, str, Toast.LENGTH_LONG).show();
            }
        });
    }


    public void showHomeActivity(){
        Intent i = new Intent(context, HomeActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    public void showHomeActivity(Context c){
        Intent i = new Intent(c, HomeActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
        transaction.replace(R.id.frame_layout, new LoginFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }


    public void showVerifyCodeFragment(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout, new VerifyCodeFragment());
        transaction.addToBackStack(null);
        transaction.commit();
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

}



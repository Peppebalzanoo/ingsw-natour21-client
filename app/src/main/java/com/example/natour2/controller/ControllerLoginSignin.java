package com.example.natour2.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.natour2.HomeActivity;
import com.example.natour2.auth.Cognito;
import com.example.natour2.fragment.AdminFragment;
import com.example.natour2.HomeAdmin;
import com.example.natour2.fragment.loginSignin.ForgetPasswordFragment;
import com.example.natour2.fragment.loginSignin.LoginFragment;
import com.example.natour2.R;
import com.example.natour2.fragment.loginSignin.SignupFragment;
import com.example.natour2.fragment.loginSignin.VerifyCodeFragment;


public class ControllerLoginSignin {

    private static ControllerLoginSignin ctrlInstance;
    private Activity activity = null;
    private Context context = null;
    private FragmentManager fragmentManager = null;
    private String username;
    private String email;
    private String matricolaAdim;

    //private AuthAmplify authAmplify = new AuthAmplify();

    private ControllerLoginSignin(){ }

    public static ControllerLoginSignin getInstance(){
        if(ctrlInstance == null)
            ctrlInstance = new ControllerLoginSignin();
        return ctrlInstance;
    }


    public void login(String username, String passowrd){
        Cognito authentication = new Cognito(context);
        authentication.userLogIn(username, passowrd);

        //authAmplify.signIn(username, passowrd);
    }


    public void signup(String username, String email, String password){

        this.username = username;
        this.email = email;

        Cognito authentication = new Cognito(context);
        authentication.addAttributes("name", username);
        authentication.addAttributes("email", email);
        authentication.userSignUpInBackground(username, password);

        //authAmplify.signUp(username, email, password);
    }


    public void verifyCode(String code){
        Cognito authentication = new Cognito(context);
        authentication.confirmVerificationCodeUser(username, code);

        //authAmplify.confirmSignUp(this.email, code);

    }

    public void forgetPassword(String userID, String vecchiaPassword, String nuovaPassword){
        Cognito authentication = new Cognito(context);
        authentication.forgetPasswordCognito(userID, vecchiaPassword, nuovaPassword);
    }

    public void printToast(String str){
        activity.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(activity, str, Toast.LENGTH_LONG).show();
            }
        });
    }

    /*public void showHomeActivity(){
        Intent i = new Intent(context, HomeActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }*/

    public void showHomeActivity(Context c){
        Intent i = new Intent(c, HomeActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        c.startActivity(i);
    }

    public void showHomeAdminActivity(Context c){
        Intent i = new Intent(c, HomeAdmin.class);
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
        //signupAdmin(matricolaAdmin, passwordAdmin);
        Cognito authentication = new Cognito(context, "admin");
        authentication.adminLogIn(matricolaAdmin, passwordAdmin);
    }


    public void signupAdmin(String username, String password){
        this.matricolaAdim = username;
        Cognito authentication = new Cognito(context, "admin");
        authentication.addAttributesAdmin("name", username);
        authentication.adminSignUpInBackground(username, password);
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



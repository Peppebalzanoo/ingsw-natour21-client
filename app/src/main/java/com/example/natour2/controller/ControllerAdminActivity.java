package com.example.natour2.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.natour2.HomeAdminActivity;
import com.example.natour2.MainActivity;
import com.example.natour2.fragment.AdminFragment;
import com.example.natour2.fragment_admin.ControlPanelFragment;
import com.example.natour2.R;
import com.example.natour2.fragment_admin.NewsLetterFragment;
import com.example.natour2.fragment_admin.SegnalazioneFragment;
import com.example.natour2.model.Report;

import java.util.Objects;

public class ControllerAdminActivity {

    private static ControllerAdminActivity controllerInstance;
    private FragmentManager fragmentManager;
    private Context context;
    private Activity activity;

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

    public void showSegnalazioneFragment(FragmentManager fragmentManager, Report report){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout_admin, new SegnalazioneFragment(report)); // give your fragment container id in first parameter
        transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
        transaction.commit();
    }

    public void showNewsLettersFragment(FragmentManager fragmentManager){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout_admin, new NewsLetterFragment()); // give your fragment container id in first parameter
        transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
        transaction.commit();
    }

    public void showMainActivityAndClearBackStack(FragmentManager fragmentManager, Context c){
        clearBackStack(fragmentManager);
        Intent i = new Intent(c, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        c.startActivity(i);
    }

    public void clearBackStack(FragmentManager fragmentManager){
        for(int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
            fragmentManager.popBackStack();
        }
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

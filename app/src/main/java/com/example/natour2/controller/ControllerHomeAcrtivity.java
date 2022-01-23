package com.example.natour2.controller;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.natour2.R;
import com.example.natour2.dao.UserDao;
import com.example.natour2.fragment.AddItinerarioFragment;
import com.example.natour2.fragment.ChatFragment;
import com.example.natour2.fragment.HomeFragment;
import com.example.natour2.fragment.NotificationFragment;
import com.example.natour2.fragment.ProfileFragment;
import com.example.natour2.fragment.SearchFragment;
import com.example.natour2.fragment.SelectUsersFragment;
import com.example.natour2.fragment.UserFragment;

import com.example.natour2.model.User;
import com.example.natour2.utilities.RetrofitInstance;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ControllerHomeAcrtivity {

    private static ControllerHomeAcrtivity ctrlInstance;
    private Activity activity = null;
    private Context context = null;
    private FragmentManager fragmentManager = null;

    private String token;

    private ControllerHomeAcrtivity(){
    }

    public static ControllerHomeAcrtivity getInstance(){
        if(ctrlInstance == null) {
            ctrlInstance = new ControllerHomeAcrtivity();
        }
        return ctrlInstance;
    }


    public void showHomeFragment(){
        //activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new SignupFragment()).commit();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout_home, new HomeFragment()); // give your fragment container id in first parameter
        transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
        transaction.commit();
    }

    public void showProfileFragment(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout_home,  new ProfileFragment()); // give your fragment container id in first parameter
        transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
        transaction.commit();
    }

    public void showSearchFragment(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout_home, new SearchFragment()); // give your fragment container id in first parameter
        transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
        transaction.commit();
    }

    public void showNotificationFragment(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout_home, new NotificationFragment()); // give your fragment container id in first parameter
        transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
        transaction.commit();
    }

    public void showUserFragment(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout_home, new UserFragment()); // give your fragment container id in first parameter
        transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
        transaction.commit();
    }


    public void showSelectUserFragment(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout_home, new SelectUsersFragment()); // give your fragment container id in first parameter
        transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
        transaction.commit();
    }

    public void showChatFragment(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout_home, new ChatFragment()); // give your fragment container id in first parameter
        transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
        transaction.commit();
    }

    public void showAddItinerarioFragment(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout_home, new AddItinerarioFragment()); // give your fragment container id in first parameter
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


    public void setActivity(Activity activity){
        this.activity = activity;
    }


    public void setContext(Context context){
        this.context = context;
    }


    public void setFragmentManager(FragmentManager fragmentManager){
        this.fragmentManager = fragmentManager;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private final UserDao userDAO = RetrofitInstance.getRetrofitInstance().create(UserDao.class);


    public void pippo(){

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                User result;
                Call<User> call = userDAO.getUserByUsername(token);
                try {
                    result = call.execute().body();
                    System.out.println("*************************************** username: "+ result.toString());
                } catch (IOException e) {
                    System.out.println("*************************************** errore");
                    e.printStackTrace();
                }
            }
        });
       t1.start();


    }

}

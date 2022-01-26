package com.example.natour2.controller;

import android.app.Activity;
import android.content.Context;

import com.example.natour2.dao.UserDao;
import com.example.natour2.model.Itinerary;
import com.example.natour2.model.User;
import com.example.natour2.utilities.Constants;
import com.example.natour2.utilities.PreferanceManager;
import com.example.natour2.utilities.RetrofitInstance;
import com.example.natour2.utilities.SharedPreferencesUtil;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;

public class ControllerUser {

    private static ControllerUser ctrlInstance;
    private Activity activity;
    private Context context;
    private List<User> listUsers = null;
    private final UserDao userDAO = RetrofitInstance.getRetrofitInstance().create(UserDao.class);

    private ControllerUser(){
    }

    public static ControllerUser getInstance(){
        if(ctrlInstance == null) {
            ctrlInstance = new ControllerUser();
        }
        return ctrlInstance;
    }



    public User getActiveUser(){
        final User[] result = new User[1];
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {

                Call<User> call = userDAO.getUserByUsername(SharedPreferencesUtil.getStringPreference(ctrlInstance.activity, "IDTOKEN"));
                try {
                    result[0] = call.execute().body();
                } catch (IOException e) {
                    System.out.println("*************************************** errore");
                    e.printStackTrace();
                }
            }
        });
        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("*************************************** username: "+ result[0].toString());
        return result[0];
    }


    public void setUser(String token){

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                User result;
                Call<User> call = userDAO.setUser(token);
                try {
                    result = call.execute().body();
                    SharedPreferencesUtil.setStringPreference(activity, "USERNAME", result.getUsername());

                    PreferanceManager preferanceManager = new PreferanceManager(context);
                    preferanceManager.putString(Constants.KEY_USER_ID, result.getUsername());
                    preferanceManager.putString(Constants.KEY_NAME, result.getUsername());
                } catch (IOException e) {
                    System.out.println("*************************************** errore!!!!");
                    e.printStackTrace();
                }
            }
        });
        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public List<User> getAllUsers(){
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {

                Call<List<User>> call = userDAO.getAllUsers(SharedPreferencesUtil.getStringPreference(ctrlInstance.activity, "IDTOKEN"));
                try {
                    listUsers = call.execute().body();
                } catch (IOException e) {
                    System.out.println("*************************************** errore");
                    e.printStackTrace();
                }
            }
        });
        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return listUsers;
    }


    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}


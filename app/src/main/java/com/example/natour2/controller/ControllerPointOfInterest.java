package com.example.natour2.controller;

import android.app.Activity;
import android.content.Context;

import com.example.natour2.dao.PointOfInterestDao;
import com.example.natour2.dao.UserDao;
import com.example.natour2.model.Itinerary;
import com.example.natour2.model.PointOfInterest;
import com.example.natour2.model.User;
import com.example.natour2.utilities.RetrofitInstance;
import com.example.natour2.utilities.SharedPreferencesUtil;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ControllerPointOfInterest {

    private static ControllerPointOfInterest  ctrlInstance;
    private Activity activity;
    private Context context;

    private ControllerPointOfInterest (){
    }

    private final PointOfInterestDao pointOfInterestDao = RetrofitInstance.getRetrofitInstance().create(PointOfInterestDao.class);

    public static ControllerPointOfInterest getInstance(){
        if(ctrlInstance == null) {
            ctrlInstance = new ControllerPointOfInterest ();
        }
        return ctrlInstance;
    }



    public void uploadPointOfInterest(Itinerary itr, String type, Double coordY, Double coordX){
        Itinerary itinerary = new Itinerary(itr.getId(), itr.getName(), itr.getDuration(), itr.getDifficulty(), itr.getDescription(), itr.getGpx(), itr.getDisabledAccess(), new User(itr.getAuthor().getId(), itr.getAuthor().getUsername(), itr.getAuthor().getEmail(), itr.getAuthor().getProfileImagePath(), itr.getAuthor().getFCMToken()));
        Call<PointOfInterest> call = pointOfInterestDao.uploadPointOfInterest(itinerary, coordY, coordX, type, SharedPreferencesUtil.getStringPreference(ctrlInstance.activity, "IDTOKEN"));

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    call.execute().body();
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

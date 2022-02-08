package com.example.natour2.controller;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.example.natour2.dao.PointOfInterestDao;
import com.example.natour2.dao.UserDao;
import com.example.natour2.model.Itinerary;
import com.example.natour2.model.PointOfInterest;
import com.example.natour2.model.User;
import com.example.natour2.utilities.POITypeMapper;
import com.example.natour2.utilities.RetrofitInstance;
import com.example.natour2.utilities.SharedPreferencesUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ControllerPointOfInterest {

    private static ControllerPointOfInterest  ctrlInstance;
    private Activity activity;
    private Context context;
    private List<POITypeMapper> poiTypeMappers;

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
                    printToast("Oops! Impossibile contattare il server.");
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


    public List<POITypeMapper> getAllType(){
        Call<List<POITypeMapper>> call = pointOfInterestDao.getAllType(SharedPreferencesUtil.getStringPreference(ctrlInstance.activity, "IDTOKEN"));

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                   poiTypeMappers = call.execute().body();

                } catch (IOException e) {
                    printToast("Oops! Impossibile contattare il server.");
                }
            }
        });
        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
         return poiTypeMappers;
    }

    public void printToast(String str){
        activity.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(activity, str, Toast.LENGTH_LONG).show();
            }
        });
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

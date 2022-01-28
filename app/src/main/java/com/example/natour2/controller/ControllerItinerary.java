package com.example.natour2.controller;

import android.app.Activity;
import android.content.Context;
import android.os.ConditionVariable;
import android.text.InputType;
import android.widget.Toast;

import com.example.natour2.dao.ItineraryDao;
import com.example.natour2.model.Itinerary;
import com.example.natour2.model.User;
import com.example.natour2.utilities.RetrofitInstance;
import com.example.natour2.utilities.SharedPreferencesUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ControllerItinerary {

    private static ControllerItinerary ctrlInstance;
    private Activity activity;
    private Context context;
    List<Itinerary> listItineraries = null;

    private final ItineraryDao itineraryDao = RetrofitInstance.getRetrofitInstance().create(ItineraryDao.class);


    private ControllerItinerary(){
    }

    public static ControllerItinerary getInstance(){
        if(ctrlInstance == null) {
            ctrlInstance = new ControllerItinerary();
        }
        return ctrlInstance;
    }

    public List<Itinerary> getActiveUserItineraries(){

        Call<List<Itinerary>> call = itineraryDao.getActiveUserItineraries(SharedPreferencesUtil.getStringPreference(ctrlInstance.activity, "IDTOKEN"));
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    listItineraries = call.execute().body();
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
        return listItineraries;
    }

    public void uploadItinerary(Itinerary itinerary){
        Call<Itinerary> call = itineraryDao.uploadItinerary(itinerary, SharedPreferencesUtil.getStringPreference(ctrlInstance.activity, "IDTOKEN"));
        call.enqueue(new Callback<Itinerary>() {
            @Override
            public void onResponse(Call<Itinerary> call, Response<Itinerary> response) {
                System.out.println("°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°° ok: " +  response.code());

            }
            @Override
            public void onFailure(Call<Itinerary> call, Throwable t) {
                System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++ error");

            }
        });
    }


    public List<Itinerary> getAllItineraries(){

        Call<List<Itinerary>> call = itineraryDao.getAllItineraries(null, null, null, null, null, null, null, null, null, SharedPreferencesUtil.getStringPreference(ctrlInstance.activity, "IDTOKEN"));
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    listItineraries = call.execute().body();
                } catch (IOException e) {
                    System.out.println("*************************************** errore getItinerary");
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
        return listItineraries;
    }

    public List<Itinerary> getAllItinerariesByFilters(String name, Integer duration, Integer durationLessThan, Integer durationGreaterThan, Integer difficulty, Integer difficultyLessThan, Integer difficultyGreaterThan, Boolean disabledAccess, Integer sort){

        Call<List<Itinerary>> call = itineraryDao.getAllItineraries(name, duration, durationLessThan, durationGreaterThan, difficulty, difficultyLessThan, difficultyGreaterThan, disabledAccess, sort, SharedPreferencesUtil.getStringPreference(ctrlInstance.activity, "IDTOKEN"));
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    listItineraries = call.execute().body();
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
        return listItineraries;
    }

    public void deleteItinerary(Long id){
        System.out.println("°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°° ok: " + id);
        Call<Void> call = itineraryDao.deleteItinerary(id, SharedPreferencesUtil.getStringPreference(ctrlInstance.activity, "IDTOKEN"));
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                System.out.println("°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°° ok: " +  response.code());

            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++ error");

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

package com.example.natour2.controller;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.natour2.adapter.ItinerarioAdapter;
import com.example.natour2.adapter.NotificationAdapter;
import com.example.natour2.dao.ItineraryDao;
import com.example.natour2.fragment.AddItinerarioFragment;
import com.example.natour2.fragment.SearchFragment;
import com.example.natour2.model.Itinerary;
import com.example.natour2.utilities.RetrofitInstance;
import com.example.natour2.utilities.SharedPreferencesUtil;
import java.io.IOException;
import java.util.List;

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
        return listItineraries;
    }

    public void getActiveUserItineraries1(ItinerarioAdapter adapter){

        Call<List<Itinerary>> call = itineraryDao.getActiveUserItineraries(SharedPreferencesUtil.getStringPreference(ctrlInstance.activity, "IDTOKEN"));
        call.enqueue(new Callback<List<Itinerary>>() {
            @Override
            public void onResponse(Call<List<Itinerary>> call, Response<List<Itinerary>> response) {
                List<Itinerary> itineraryList = response.body();

                if(itineraryList == null){
                    return;
                }

                adapter.setItineraryList(itineraryList);
            }

            @Override
            public void onFailure(Call<List<Itinerary>> call, Throwable t) {

            }
        });
    }


    public void getActiveUserItineraries2(NotificationAdapter notificationAdapter) {
        Call<List<Itinerary>> call = itineraryDao.getActiveUserItineraries(SharedPreferencesUtil.getStringPreference(ctrlInstance.activity, "IDTOKEN"));
        call.enqueue(new Callback<List<Itinerary>>() {
            @Override
            public void onResponse(Call<List<Itinerary>> call, Response<List<Itinerary>> response) {
                List<Itinerary> itineraryList = response.body();

                if(itineraryList == null){
                    return;
                }

                notificationAdapter.setReportListFromItineraryList(itineraryList);
            }

            @Override
            public void onFailure(Call<List<Itinerary>> call, Throwable t) {

            }
        });

    }

    public Boolean uploadItinerary(Itinerary itinerary){
        Call<Itinerary> call = itineraryDao.uploadItinerary(itinerary, SharedPreferencesUtil.getStringPreference(ctrlInstance.activity, "IDTOKEN"));
        final Boolean[] ret = new Boolean[1];
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if(call.execute().code() == 200){
                        ret[0] = true;
                    } else {
                        ret[0] = false;
                    }

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
        return ret[0];
    }

    public void uploadItinerary1(AddItinerarioFragment fragment, String name, Integer duration, Integer difficulty, String readedTexFromUri, Boolean disabledAcces, String description) {
        Itinerary itinerary = new Itinerary(name, duration, difficulty, description, readedTexFromUri, disabledAcces, null);
        Call<Itinerary> call = itineraryDao.uploadItinerary(itinerary, SharedPreferencesUtil.getStringPreference(ctrlInstance.activity, "IDTOKEN"));
        call.enqueue(new Callback<Itinerary>() {
            @Override
            public void onResponse(Call<Itinerary> call, Response<Itinerary> response) {
                fragment.callback();
            }

            @Override
            public void onFailure(Call<Itinerary> call, Throwable t) {

            }
        });
    }


    public void getAllItineraries1(ItinerarioAdapter adapter){
        Call<List<Itinerary>> call = itineraryDao.getAllItineraries(null, null, null, null, null, null, null, null, null, SharedPreferencesUtil.getStringPreference(ctrlInstance.activity, "IDTOKEN"));
        call.enqueue(new Callback<List<Itinerary>>() {
            @Override
            public void onResponse(Call<List<Itinerary>> call, Response<List<Itinerary>> response) {

                Log.d("ciao", "onResponse: " + response.body());

                List<Itinerary> itineraryList = response.body();

                if(itineraryList == null){
                    return;
                }

                adapter.setItineraryList(itineraryList);
            }

            @Override
            public void onFailure(Call<List<Itinerary>> call, Throwable t) {

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
        return listItineraries;

    }

    public void getAllItinerariesByFilters1(SearchFragment fragment, ItinerarioAdapter adapter, String name, Integer duration, Integer durationLessThan, Integer durationGreaterThan, Integer difficulty, Integer difficultyLessThan, Integer difficultyGreaterThan, Boolean disabledAccess, Integer sort){

        Call<List<Itinerary>> call = itineraryDao.getAllItineraries(name, duration, durationLessThan, durationGreaterThan, difficulty, difficultyLessThan, difficultyGreaterThan, disabledAccess, sort, SharedPreferencesUtil.getStringPreference(ctrlInstance.activity, "IDTOKEN"));
        call.enqueue(new Callback<List<Itinerary>>() {
            @Override
            public void onResponse(Call<List<Itinerary>> call, Response<List<Itinerary>> response) {

                if(response.body() == null){
                    fragment.noResult();
                } else {
                    adapter.setItineraryList(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Itinerary>> call, Throwable t) {

            }
        });
    }


    public List<Itinerary> getAllItinerariesByFilters(String name, Integer duration, Integer durationLessThan, Integer durationGreaterThan, Integer difficulty, Integer difficultyLessThan, Integer difficultyGreaterThan, Boolean disabledAccess, Integer sort){

        Call<List<Itinerary>> call = itineraryDao.getAllItineraries(name, duration, durationLessThan, durationGreaterThan, difficulty, difficultyLessThan, difficultyGreaterThan, disabledAccess, sort, SharedPreferencesUtil.getStringPreference(ctrlInstance.activity, "IDTOKEN"));
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    listItineraries = call.execute().body();
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
        return listItineraries;
    }

    public void deleteItinerary(Long id){
        //System.out.println("°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°° ok: " + id);
        Call<Void> call = itineraryDao.deleteItinerary(SharedPreferencesUtil.getStringPreference(ctrlInstance.activity, "IDTOKEN"), id);
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

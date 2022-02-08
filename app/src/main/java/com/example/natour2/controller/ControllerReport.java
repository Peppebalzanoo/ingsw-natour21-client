package com.example.natour2.controller;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.example.natour2.dao.ReportDao;
import com.example.natour2.model.Itinerary;
import com.example.natour2.model.Report;
import com.example.natour2.utilities.RetrofitInstance;
import com.example.natour2.utilities.SharedPreferencesUtil;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ControllerReport {

    private static ControllerReport ctrlInstance;
    private Activity activity;
    private Context context;
    private List<Report> listReports;
    private final ReportDao reportDAO = RetrofitInstance.getRetrofitInstance().create(ReportDao.class);

    private ControllerReport(){
    }

    public static ControllerReport getInstance(){
        if(ctrlInstance == null) {
            ctrlInstance = new ControllerReport();
        }
        return ctrlInstance;
    }


    public List<Report> getAllReport(){
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                Call<List<Report>> call = reportDAO.getAllReport(SharedPreferencesUtil.getStringPreference(ctrlInstance.activity, "IDTOKEN"));
                try {
                    listReports = call.execute().body();

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
        return listReports;
    }

    public void setReport(Itinerary itinerary, String title, String motivationReport ){
        Itinerary itr = new Itinerary(itinerary.getId(), itinerary.getName(), itinerary.getDuration(), itinerary.getDifficulty(), itinerary.getDescription(), itinerary.getGpx(), itinerary.getDisabledAccess(), itinerary.getReports(), itinerary.getPointsOfInterest());
        Call<Report> call = reportDAO.setReport(itr, title, motivationReport, SharedPreferencesUtil.getStringPreference(ctrlInstance.activity, "IDTOKEN"));
        call.enqueue(new Callback<Report>() {
            @Override
            public void onResponse(Call<Report> call, Response<Report> response) {
                printToast("Segnalazione inviata con successo.");
            }
            @Override
            public void onFailure(Call<Report> call, Throwable t) {
                printToast("Oops! Impossibile contattare il server.");
            }
        });

    }

    public void deleteReport(Long id){
        Call<Void> call = reportDAO.deleteReport(SharedPreferencesUtil.getStringPreference(ctrlInstance.activity, "IDTOKEN"), id);
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

    public void provideExplanation(Report report,String explanation){
        Call<Void> call = reportDAO.provideExplanation(SharedPreferencesUtil.getStringPreference(ctrlInstance.activity, "IDTOKEN"), report,explanation);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                printToast("Risposta inviata con successo");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                printToast("Oops! Impossibile contattare il server.");
            }
        });

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

package com.example.natour2.controller;

import android.app.Activity;
import android.content.Context;

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
                System.out.println("*************************************************************** idToken: " + SharedPreferencesUtil.getStringPreference(ctrlInstance.activity, "IDTOKEN"));
                try {
                    listReports = call.execute().body();

                        //System.out.println("*************************************** size: " + listReports.size());

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
        return listReports;
    }

    public void setReport(Itinerary itinerary, String title, String motivationReport ){
        System.out.println("°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°° itinerary: " +  itinerary.toString());
        System.out.println("°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°° title: " +  title);
        System.out.println("°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°° motivation: " +  motivationReport);
        Itinerary itr = new Itinerary(itinerary.getId(), itinerary.getName(), itinerary.getDuration(), itinerary.getDifficulty(), itinerary.getDescription(), itinerary.getGpx(), itinerary.getDisabledAccess(), itinerary.getReports(), itinerary.getPointsOfInterest());
        Call<Report> call = reportDAO.setReport(itr, title, motivationReport, SharedPreferencesUtil.getStringPreference(ctrlInstance.activity, "IDTOKEN"));
        call.enqueue(new Callback<Report>() {
            @Override
            public void onResponse(Call<Report> call, Response<Report> response) {
                System.out.println("°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°° ok: " +  response.code());

            }
            @Override
            public void onFailure(Call<Report> call, Throwable t) {
                System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++ error : " + t.toString());
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

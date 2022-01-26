package com.example.natour2.controller;

import android.app.Activity;
import android.content.Context;

import com.example.natour2.dao.ReportDao;
import com.example.natour2.dao.UserDao;
import com.example.natour2.model.Report;
import com.example.natour2.model.User;
import com.example.natour2.utilities.RetrofitInstance;
import com.example.natour2.utilities.SharedPreferencesUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

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

                        System.out.println("*************************************** size: " + listReports.size());

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

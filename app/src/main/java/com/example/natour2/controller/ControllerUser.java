package com.example.natour2.controller;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.example.natour2.adapter.UserAdapter;
import com.example.natour2.dao.UserDao;
import com.example.natour2.fragment.ProfileFragment;
import com.example.natour2.fragment.SelectUsersFragment;
import com.example.natour2.model.User;
import com.example.natour2.utilities.Constants;
import com.example.natour2.utilities.PreferanceManager;
import com.example.natour2.utilities.RetrofitInstance;
import com.example.natour2.utilities.SharedPreferencesUtil;

import java.io.IOException;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    public void getActiveUser(ProfileFragment fragment){
        Call<User> call = userDAO.getActiveUser(SharedPreferencesUtil.getStringPreference(ctrlInstance.activity, "IDTOKEN"));
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User u = response.body();
                fragment.setUserInformation(u.getUsername(), u.getEmail(), u.getProfileImagePath());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                printToast("Oops! Impossibile contattare il server.");
            }
        });
    }



    public void userSignUp(){
        Call<User> call = userDAO.signUp(SharedPreferencesUtil.getStringPreference(ctrlInstance.activity, "IDTOKEN"));
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                User result;
                try {
                    result = call.execute().body();
                    SharedPreferencesUtil.setStringPreference(activity, "USERNAME", result.getUsername());
                    PreferanceManager preferanceManager = new PreferanceManager(context);
                    preferanceManager.putString(Constants.KEY_USER_ID, result.getUsername());
                    preferanceManager.putString(Constants.KEY_NAME, result.getUsername());
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


    public void setUser(String token){
        Call<User> call = userDAO.setUser(token, new PreferanceManager(context).getString(Constants.KEY_FCM_TOKEN));
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                User result;
                try {
                    result = call.execute().body();
                    SharedPreferencesUtil.setStringPreference(activity, "USERNAME", result.getUsername());

                    PreferanceManager preferanceManager = new PreferanceManager(context);
                    preferanceManager.putString(Constants.KEY_USER_ID, result.getUsername());
                    preferanceManager.putString(Constants.KEY_NAME, result.getUsername());
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




    public void getAllUsers1(UserAdapter adapter, SelectUsersFragment selectUsersFragment){
        Call<List<User>> call = userDAO.getAllUsers(SharedPreferencesUtil.getStringPreference(ctrlInstance.activity, "IDTOKEN"));
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                adapter.setUsers(response.body());
                selectUsersFragment.loading(false);
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                printToast("Oops! Impossibile contattare il server.");

            }
        });
    }



    public List<User> getAllUsers(){
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                Call<List<User>> call = userDAO.getAllUsers(SharedPreferencesUtil.getStringPreference(ctrlInstance.activity, "IDTOKEN"));
                try {
                    listUsers = call.execute().body();
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
        return listUsers;
    }


    public void updateProfileImage(MultipartBody.Part image){
        Call<Void> call = userDAO.updateProfilePic(SharedPreferencesUtil.getStringPreference(ctrlInstance.activity, "IDTOKEN"), image);

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


    public void setSubscribe(){
        Call<Void> call = userDAO.setSubscribe(SharedPreferencesUtil.getStringPreference(ctrlInstance.activity, "IDTOKEN"));
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
               //System.out.println("************************************************** code : " + response.code());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                printToast("Oops! Impossibile contattare il server.");
            }
        });
    }


    public void sendEmailToAll(String subject, String message){
        Call<Void> call = userDAO.sendEmailToAll(subject, message, SharedPreferencesUtil.getStringPreference(ctrlInstance.activity, "IDTOKEN"));
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                printToast("Mails inviate con successo.");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                printToast("Oops! Impossibile contattare il server.");
            }
        });
    }


    public User getUserByUsername(String username){
        Call<User> call = userDAO.getUserByUsername(SharedPreferencesUtil.getStringPreference(ctrlInstance.activity, "IDTOKEN"), username);
        final User[] user = new User[1];
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    user[0] = call.execute().body();
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
        return user[0];
    }

    public User getActiveUser2(){
        final User[] result = new User[1];
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {

                Call<User> call = userDAO.getActiveUser(SharedPreferencesUtil.getStringPreference(ctrlInstance.activity, "IDTOKEN"));
                try {
                    result[0] = call.execute().body();
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
        return result[0];
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


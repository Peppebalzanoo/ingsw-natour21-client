package com.example.natour2.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtil {

    public static String getStringPreference(Activity activity, String key) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(key, Context.MODE_PRIVATE);
        String value = sharedPreferences.getString(key, "");

        return value;
    }

    public static String getStringPreference(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        String value = sharedPreferences.getString(key, "");

        return value;
    }

    public static void setStringPreference(Activity activity, String key, String value) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(key, Context.MODE_PRIVATE);
        SharedPreferences.Editor tokenShared = sharedPreferences.edit();
        tokenShared.putString(key, value);
        tokenShared.apply();
    }

    public static void clearAll(Activity activity) {
        activity.getSharedPreferences("TOKEN", Context.MODE_PRIVATE).edit().clear().apply();
        activity.getSharedPreferences("IDTOKEN", Context.MODE_PRIVATE).edit().clear().apply();
        activity.getSharedPreferences("REFRESHTOKEN", Context.MODE_PRIVATE).edit().clear().apply();
        activity.getSharedPreferences("USERNAME", Context.MODE_PRIVATE).edit().clear().apply();
    }

}

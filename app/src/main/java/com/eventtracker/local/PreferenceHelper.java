package com.eventtracker.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.eventtracker.utils.Constants;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class PreferenceHelper {

    private SharedPreferences mSharedPreferences;

    private static PreferenceHelper instance;

    private PreferenceHelper(Context context){
        mSharedPreferences = context.getSharedPreferences("pref_file",Context.MODE_PRIVATE);
    }

    public static PreferenceHelper getInstance(Context context){
        if(instance == null){
            instance = new PreferenceHelper(context);
        }
        return instance;
    }

    public void setLastEventUpdatedAt(){
        mSharedPreferences.edit().putString(Constants.LAST_EVENT_LIST_UPDATED_AT,new SimpleDateFormat("dd-MMM-yyyy HH:mm", Locale.ENGLISH).
                format(Calendar.getInstance().getTime())).apply();
    }

    public String getLastEventUpdatedAt(){
        return mSharedPreferences.getString(Constants.LAST_EVENT_LIST_UPDATED_AT,"");
    }

}


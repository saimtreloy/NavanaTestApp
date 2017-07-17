package com.moodybugs.saim.navanatestapp.Utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Android on 1/16/2017.
 */
public class SharedPrefDatabase {
    public static final String KEY_FUTURE_PROJECT = "KEY_FUTURE_PROJECT";
    public static final String KEY_OTHER_PROJECT = "KEY_OTHER_PROJECT";
    public static final String KEY_MAP_PROJECT = "KEY_MAP_PROJECT";
    public static final String KEY_LAT = "KEY_LAT";
    public static final String KEY_LON = "KEY_LON";

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;
    public SharedPrefDatabase(Context ctx) {
        this.context = ctx;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        editor = sharedPreferences.edit();
    }

    public void StoreFutureProject(String data){
        editor.putString(KEY_FUTURE_PROJECT, data);
        editor.commit();
    }
    public String RetriveFutureProject(){
        String text = sharedPreferences.getString(KEY_FUTURE_PROJECT, null);
        return text;
    }


    public void StoreOtherProject(String data){
        editor.putString(KEY_OTHER_PROJECT, data);
        editor.commit();
    }
    public String RetriveOtherProject(){
        String text = sharedPreferences.getString(KEY_OTHER_PROJECT, null);
        return text;
    }


    public void StoreMapProject(String data){
        editor.putString(KEY_MAP_PROJECT, data);
        editor.commit();
    }
    public String RetriveMapProject(){
        String text = sharedPreferences.getString(KEY_MAP_PROJECT, null);
        return text;
    }

    public void StoreLat(String data){
        editor.putString(KEY_LAT, data);
        editor.commit();
    }
    public String RetriveLat(){
        String text = sharedPreferences.getString(KEY_LAT, null);
        return text;
    }

    public void StoreLon(String data){
        editor.putString(KEY_LON, data);
        editor.commit();
    }
    public String RetriveLon(){
        String text = sharedPreferences.getString(KEY_LON, null);
        return text;
    }

}

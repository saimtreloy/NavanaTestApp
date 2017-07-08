package com.moodybugs.saim.navanatestapp.Utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Android on 1/16/2017.
 */
public class SharedPrefDatabase {
    public static final String PREFS_KEY_SONG = "AOP_PREFS_SONGS";

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;
    public SharedPrefDatabase(Context ctx) {
        this.context = ctx;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        editor = sharedPreferences.edit();
    }

    public void StoreSong(String data){
        editor.putString(PREFS_KEY_SONG, data);
        editor.commit();
    }
    public String RetriveSong(){
        String text = sharedPreferences.getString(PREFS_KEY_SONG, null);
        return text;
    }

}

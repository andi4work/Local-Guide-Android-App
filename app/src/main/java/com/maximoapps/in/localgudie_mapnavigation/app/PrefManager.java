package com.maximoapps.in.localgudie_mapnavigation.app;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ${ChandraMohanReddy} on 1/13/2017.
 */

public class PrefManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "Listo-welcome";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String IS_LOGED_IN = "IsLogedIn";

    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void setLogedIn(boolean isFirstTime) {
        editor.putBoolean(IS_LOGED_IN, isFirstTime);
        editor.commit();
    }

    public boolean isLogedIn() {
        return pref.getBoolean(IS_LOGED_IN, true);
    }

}
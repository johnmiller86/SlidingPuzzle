package com.ist_311.slidingpuzzle.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SessionManager {

    // Shared Preferences
    private final SharedPreferences pref;
    private final Editor editor;

    // Shared preference tags
    private static final String PREFS = "prefs";
    private static final String ROWS = "rows";
    private static final String COLS = "cols";

    // Constructor
    public SessionManager(Context context){
        int PRIVATE_MODE = 0;
        pref = context.getSharedPreferences(PREFS, PRIVATE_MODE);
        editor = pref.edit();
        editor.apply();
    }

    /**
     * Sets the columns preference.
     * @param cols the columns.
     */
    public void setCols (int cols){
        editor.putInt(COLS, cols);
        editor.commit();
    }

    /**
     * Sets the rows preference.
     * @param rows the rows.
     */
    public void  setRows(int rows){
        editor.putInt(ROWS, rows);
        editor.commit();
    }

    /**
     * Gets the columns preference.
     * @return the columns.
     */
    public int getCols(){
        return pref.getInt(COLS, 3);
    }

    /**
     * Gets the rows preference.
     * @return the rows.
     */
    public int getRows(){
        return pref.getInt(ROWS, 3);
    }
}
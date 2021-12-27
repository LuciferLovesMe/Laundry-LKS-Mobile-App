package com.abim.laundrylks;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {
    private SharedPreferences prefs;

    public Session(Context ctx){
        prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public void setEmployee(int id, String name, String email){
        prefs.edit().putInt("id", id).commit();
        prefs.edit().putString("name", name).commit();
        prefs.edit().putString("email", email).commit();
    }

    public int getId(){
        return prefs.getInt("id", 0);
    }

    public String getName(){
        return prefs.getString("name", "");
    }

    public String getEmail(){
        return prefs.getString("email", "");
    }
}

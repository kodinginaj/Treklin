package com.example.treklin.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {
    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;

    public Session(Context context)
    {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setId(String id)
    {
        sharedPreferences.edit().putString("id",id).apply();
    }

    public void setNama(String nama)
    {
        sharedPreferences.edit().putString("nama",nama).apply();
    }

    public void setEmail(String email) { sharedPreferences.edit().putString("email",email).apply(); }

    public String getId()
    {
        return sharedPreferences.getString("id",null);
    }

    public String getEmail()
    {
        return sharedPreferences.getString("email",null);
    }

    public String getNama()
    {
        return sharedPreferences.getString("nama",null);
    }


    public void logout()
    {
        editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        editor.commit();
    }
}

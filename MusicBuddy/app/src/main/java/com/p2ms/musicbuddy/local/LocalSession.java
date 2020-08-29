package com.p2ms.musicbuddy.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.constraintlayout.widget.ConstraintLayout;

public class LocalSession {
    private SharedPreferences preferences;
    private SharedPreferences.Editor prefEditor;
    private Context context;

    public LocalSession(Context context) {
        this.context = context;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }
    public void setData(String key,String  value){
        prefEditor=preferences.edit();
        prefEditor.putString(key,value);
        prefEditor.commit();
    }
    public String getData(String key){
        return preferences.getString(key,"");
    }
    public void cleanData(){
        prefEditor=preferences.edit();
        prefEditor.clear();
        prefEditor.commit();
    }
}

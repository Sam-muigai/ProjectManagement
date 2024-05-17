package com.samkt.projectmanagement.data.preferences;

import android.content.SharedPreferences;

import com.samkt.projectmanagement.utils.Constants;

public class ProjectPreferences {
    private final SharedPreferences sharedPreferences;

    public ProjectPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public void saveUserToken(String token){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.TOKEN_KEY,token);
        editor.apply();
    }


    public String getUserToken(){
        return sharedPreferences.getString(Constants.TOKEN_KEY,null);
    }
}

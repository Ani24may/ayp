package com.example.loginview;

import android.content.Context;
import android.content.SharedPreferences;

public class ProfilePhotoSessionMnanager {
    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    private static final String PREF_NAME="PHOTO_INFO";
    int PRIVATE_MODE=0;

    private static final String SERVERPATH="SERVERPATH";
    private static final String IMAGENAME="IMAGENAME";
    private static final String INTERNALPATH="INTERNALPATH";



    public ProfilePhotoSessionMnanager(Context context)
    {
        this.context=context;
        sharedPreferences=context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor=sharedPreferences.edit();
    }


    public void CreateProfilePhotoSession(String serverpath,String imagename,String internalpath)
    {
        editor.putString(SERVERPATH,serverpath);
        editor.putString(IMAGENAME,imagename);
        editor.putString(INTERNALPATH,internalpath);
        editor.apply();
    }
}

package com.example.loginview;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.tech.NfcA;

import java.util.HashMap;

public class SessionManager {
    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE=0;
    private static final String PREF_NAME="LOGIN";
    private static final String LOGIN="IS_LOGIN";
    private static final String PHONE="PHONE";
    private static final String PASSWORD="PASSWORD";
    private static final String REGID="REGID";
    private static  final String NAME="NAME";
    private static final String EMAIL="EMAIL";
    private static final String CATEGORY="CATEGORY";

    public SessionManager(Context context)
    {
        this.context=context;
        sharedPreferences=context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor=sharedPreferences.edit();
    }

    public void CreateSession(String phone,String password,int RegId,String name,String email,String category)
    {
        editor.putBoolean(LOGIN,true);
        editor.putString(PHONE,phone);
        editor.putString(PASSWORD,password);
        editor.putInt(REGID,RegId);
        editor.putString(NAME,name);
        editor.putString(EMAIL,email);
        editor.putString(CATEGORY,category);
        editor.apply();
    }

    public boolean isLogIn()
    {
        return sharedPreferences.getBoolean(LOGIN,false);
    }

    public void checkLogin()
    {
        if(!this.isLogIn())
        {
            Intent i=new Intent(context,MainActivity.class);
            context.startActivity(i);
            ((Login_form)context).finish();

        }
    }

    public HashMap<String,String> getUserDetails()
    {
        HashMap<String,String> user=new HashMap<>();
        user.put(PHONE,sharedPreferences.getString(PHONE,null));
        user.put(PASSWORD,sharedPreferences.getString(PASSWORD,null));
        return user;
    }


    public HashMap<String,Integer> getRegIdFromSharedpreference() {
        HashMap<String,Integer> user=new HashMap<>();
        user.put(REGID, sharedPreferences.getInt(REGID, 0));
        return user;
    }

//    public void logout()
//    {
//        editor.clear();
//        editor.commit();
//        Intent i=new Intent(context,MainActivity.class);
//        context.startActivity(i);
//
//    }
}

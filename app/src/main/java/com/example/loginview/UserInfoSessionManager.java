package com.example.loginview;

import android.content.Context;
import android.content.SharedPreferences;

public class UserInfoSessionManager {
    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    private static final String PREF_NAME="USER_INFO";
    int PRIVATE_MODE=0;
    private static final String FATHERNAME="FATHERNAME";
    private static final String GOTRA="GOTRA";
    private static final String MOBILENO2="MOBILENO2";
    private static final String LANDLINENO1="LANDLINENO1";
    private static final String LANDLINENO2="LANDLINENO2";
    private static final String ADD1="ADD1";
    private static final String ADD2="ADD2";
    private static final String ADD3="ADD3";
    private static final String CITY="CITY";
    private static final String PIN="PIN";
    private static final String STATE="STATE";
    private static final String BLOODGROUP="BLOODGROUP";
    private static final String DOB="DOB";
    private static final String DOM="DOM";



    public UserInfoSessionManager(Context context)
    {
        this.context=context;
        sharedPreferences=context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor=sharedPreferences.edit();
    }

    public void CreateUserInfoSession(String fatherName, String gotra, String mobileNo2, String landLineNo1,
                                      String landLineNo2, String add1, String add2, String add3, String city,
                                      String pin, String state, String bloodGroup, String dob, String dom) {
        editor.putString(FATHERNAME,fatherName);
        editor.putString(GOTRA,gotra);
        editor.putString(MOBILENO2,mobileNo2);
        editor.putString(LANDLINENO1,landLineNo1);
        editor.putString(LANDLINENO2,landLineNo2);
        editor.putString(ADD1,add1);
        editor.putString(ADD2,add2);
        editor.putString(ADD3,add3);
        editor.putString(CITY,city);
        editor.putString(PIN,pin);
        editor.putString(STATE,state);
        editor.putString(BLOODGROUP,bloodGroup);
        editor.putString(DOB,dob);
        editor.putString(DOM,dom);
        editor.apply();
    }
}

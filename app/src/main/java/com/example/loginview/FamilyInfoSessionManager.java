package com.example.loginview;

import android.content.Context;
import android.content.SharedPreferences;

public class FamilyInfoSessionManager {
    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    private static final String PREF_NAME="FAMILY_INFO";
    int PRIVATE_MODE=0;

    private static final String SPOUCE="SPOUCE";
    private static final String DOBSPOUCE="DOBSPOUCE";
    private static final String CHILD1="CHILD1";
    private static final String DOBCHILD1="DOBCHILD1";
    private static final String CHILD2="CHILD2";
    private static final String DOBCHILD2="DOBCHILD2";
    private static final String CHILD3="CHILD3";
    private static final String DOBCHILD3="ADD3";

    public FamilyInfoSessionManager(Context context)
    {
        this.context=context;
        sharedPreferences=context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor=sharedPreferences.edit();
    }

    public void CreateFamilyInfoSession(String spouce,String dobspouce,String child1,String dobchild1,String child2,String dobchild2,String child3,String dobchild3)
    {
        editor.putString(SPOUCE,spouce);
        editor.putString(DOBSPOUCE,dobspouce);
        editor.putString(CHILD1,child1);
        editor.putString(DOBCHILD1,dobchild1);
        editor.putString(CHILD2,child2);
        editor.putString(DOBCHILD2,dobchild2);
        editor.putString(CHILD3,child3);
        editor.putString(DOBCHILD3,dobchild3);
        editor.apply();
    }
}

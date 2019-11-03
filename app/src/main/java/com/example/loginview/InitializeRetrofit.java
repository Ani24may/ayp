package com.example.loginview;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InitializeRetrofit {

    public static final String baseUrl="http://b1170a04.ngrok.io/";
    public static Retrofit retrofit=null;

    public static Retrofit getRetrofit()
    {
        if(retrofit == null)
        {
            retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
            return retrofit;
        }
        return  retrofit;
    }
}

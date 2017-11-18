package com.example.wedad.tasktry.apicalls;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wedad on 11/17/2017.
 */

public class ApiClient {

   public  static final String BASE_URL="http://www.sab99r.com/demos/api/";
   public  static  Retrofit retrfitInstance=null;

    public static Retrofit getRetrfitInstance() {
        if(retrfitInstance == null)
        {

            retrfitInstance=new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrfitInstance;
    }
}

package com.example.natour2.network;

import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiClient {

    private static Retrofit retrofit = null;

    public static Retrofit getInstance(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                            .baseUrl("")
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .build();
        }
        return retrofit;
    }
}

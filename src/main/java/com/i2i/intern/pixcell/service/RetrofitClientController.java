package com.i2i.intern.pixcell.service;

import com.i2i.intern.pixcell.interfaces.IApiCallBalanceRemaining;
import com.i2i.intern.pixcell.interfaces.IApiCallPackageList;
import com.i2i.intern.pixcell.interfaces.IApiCallPackageInfo;
import com.i2i.intern.pixcell.interfaces.IApiCallSignIn;
import com.i2i.intern.pixcell.interfaces.IApiCallSignUp;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.logging.HttpLoggingInterceptor;

public class RetrofitClientController {

    private static Retrofit getRetrofitClient(){

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();

        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                // 1. get the baseURL
                .baseUrl("")
                .client(okHttpClient)
                .build();
    }
    public static IApiCallSignIn getUser(){
        return getRetrofitClient().create(IApiCallSignIn.class);
    }

    public static IApiCallSignUp postUser(){
        return getRetrofitClient().create(IApiCallSignUp.class);
    }

    public static IApiCallPackageList getPackageList(){
        return getRetrofitClient().create(IApiCallPackageList.class);
    }

    public static IApiCallPackageInfo getPackageInfo(){
        return getRetrofitClient().create(IApiCallPackageInfo.class);
    }

    public static IApiCallBalanceRemaining getRemainingInfo(){
        return getRetrofitClient().create(IApiCallBalanceRemaining.class);
    }
}

package com.i2i.intern.pixcell.service;

import com.i2i.intern.pixcell.interfaces.IApiCallBalanceRemaining;
import com.i2i.intern.pixcell.interfaces.IApiCallCustomerInfo;
import com.i2i.intern.pixcell.interfaces.IApiCallForgotPassword;
import com.i2i.intern.pixcell.interfaces.IApiCallPackageInfo;
import com.i2i.intern.pixcell.interfaces.IApiCallPackageList;
import com.i2i.intern.pixcell.interfaces.IApiCallSignIn;
import com.i2i.intern.pixcell.interfaces.IApiCallSignUp;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {

        private static Retrofit retrofit;
        private static final String BASE_URL = "http://34.172.128.173";

        private static Retrofit getRetrofit() {
            if (retrofit == null) {
                HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
                httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                OkHttpClient okHttpClient = new OkHttpClient.Builder()
                        .addInterceptor(httpLoggingInterceptor)
                        .build();

                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(okHttpClient)
                        .build();
            }
            return retrofit;
        }

        public static IApiCallSignIn getUser() {
            return getRetrofit().create(IApiCallSignIn.class);
        }

        public static IApiCallForgotPassword getUser2() {
            return getRetrofit().create(IApiCallForgotPassword.class);
        }

        public static IApiCallSignUp postUser() {
            return getRetrofit().create(IApiCallSignUp.class);
        }

        public static IApiCallPackageList getPackageList() {
            return getRetrofit().create(IApiCallPackageList.class);
        }

        public static IApiCallBalanceRemaining getRemainingInfo() {
            return getRetrofit().create(IApiCallBalanceRemaining.class);
        }

        public static IApiCallCustomerInfo getCustomerInfo() {
            return getRetrofit().create(IApiCallCustomerInfo.class);
        }

        public static IApiCallPackageInfo getPackageInfo() {
            return getRetrofit().create(IApiCallPackageInfo.class);
        }
}
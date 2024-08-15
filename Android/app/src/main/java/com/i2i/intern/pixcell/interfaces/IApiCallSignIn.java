package com.i2i.intern.pixcell.interfaces;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IApiCallSignIn {

    @GET("/api/auth/login/{msisdn}/{password}")
    Call<ResponseBody> signInWithPassword(@Path("msisdn") String msisdn, @Path("password") String password);
}

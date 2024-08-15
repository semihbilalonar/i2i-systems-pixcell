package com.i2i.intern.pixcell.interfaces;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface IApiCallSignUp {
    @POST("/api/auth/registerWithPackage")
    Call<ResponseBody> signUp(@Body RequestBody signUpRequest);
}

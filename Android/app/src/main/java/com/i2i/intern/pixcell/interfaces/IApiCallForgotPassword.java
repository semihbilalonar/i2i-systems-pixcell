package com.i2i.intern.pixcell.interfaces;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IApiCallForgotPassword {
    @GET("/api/auth/forgotPassword/{msisdn}/{securityKey}")
    Call<ResponseBody> signInWithSecurityKey(@Path("msisdn") String msisdn, @Path("securityKey") String securityKey);
}

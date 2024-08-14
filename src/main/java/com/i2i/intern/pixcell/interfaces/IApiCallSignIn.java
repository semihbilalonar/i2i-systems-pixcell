package com.i2i.intern.pixcell.interfaces;

import com.i2i.intern.pixcell.data_model.SignInResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IApiCallSignIn {
    // Code like below
    @GET("{phone_number}/{password}")
    Call<SignInResponse> signInWithPassword(@Path("phone_number") String phone_number, @Path("password") String password);
    @GET("{phone_number}/{security_key}")
    Call<SignInResponse> signInWithSecurityKey(@Path("phone_number") String phone_number, @Path("security_key") String security_key);
}

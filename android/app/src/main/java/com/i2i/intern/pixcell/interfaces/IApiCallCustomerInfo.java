package com.i2i.intern.pixcell.interfaces;

import com.i2i.intern.pixcell.data_model.CustomerInfoRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IApiCallCustomerInfo {
    @GET("/api/customer/getCustomerByMsisdn/{msisdn}")
    Call<List<CustomerInfoRequest>> getCustomerById(@Path("msisdn") String msisdn);
}

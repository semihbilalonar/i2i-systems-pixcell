package com.i2i.intern.pixcell.interfaces;

import com.i2i.intern.pixcell.data_model.BalanceRemainingRequest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IApiCallBalanceRemaining {
    // Code blocks
    @GET("/api/balance/getRemainingCustomerBalanceByMsisdn/{MSISDN}")
    Call<BalanceRemainingRequest> getPackageBalanceRemaining(@Path("MSISDN") String msisdn);
}

package com.i2i.intern.pixcell.interfaces;

import com.i2i.intern.pixcell.data_model.PackageInfoRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IApiCallPackageInfo {
    @GET("/api/package/getPackageDetailsByMsisdn/{msisdn}")
    Call<List<PackageInfoRequest>> getPackageByMsisdn(@Path("msisdn") String msisdn);
}

package com.i2i.intern.pixcell.interfaces;

import com.i2i.intern.pixcell.data_model.PackageListRequest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IApiCallPackageList {
    @GET("/api/package/getPackageById/{packageId}")
    Call<PackageListRequest> getPackageById(@Path("packageId") int packageId);
}

package com.i2i.intern.pixcell.data_model;

public class PackageListRequest {

    private int packageId;
    private String packageName;
    private int packagePrice;
    private int packageMinutes;
    private int packageData;
    private int packageSms;
    private int packagePeriod;

    public PackageListRequest(int packageId, String packageName, int packagePrice, int packageMinutes, int packageData, int packageSms, int packagePeriod) {
        this.packageId = packageId;
        this.packageName = packageName;
        this.packagePrice = packagePrice;
        this.packageMinutes = packageMinutes;
        this.packageData = packageData;
        this.packageSms = packageSms;
        this.packagePeriod = packagePeriod;
    }

    public String getPackageName() {
        return packageName;
    }

}

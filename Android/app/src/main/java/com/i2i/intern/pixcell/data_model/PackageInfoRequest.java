package com.i2i.intern.pixcell.data_model;

public class PackageInfoRequest {
    private int packageId;
    private String packageName;
    private int amountMinutes;
    private int price;
    private int amountData;
    private int amountSms;
    private int period;

    public PackageInfoRequest(int packageId, String packageName, int amountMinutes, int price, int amountData, int amountSms, int period) {
        this.packageId = packageId;
        this.packageName = packageName;
        this.amountMinutes = amountMinutes;
        this.price = price;
        this.amountData = amountData;
        this.amountSms = amountSms;
        this.period = period;
    }

    public int getPeriod() {
        return period;
    }

    public int getAmountSms() {
        return amountSms;
    }

    public int getAmountData() {
        return amountData;
    }

    public int getPrice() {
        return price;
    }

    public int getAmountMinutes() {
        return amountMinutes;
    }

    public String getPackageName() {
        return packageName;
    }

}

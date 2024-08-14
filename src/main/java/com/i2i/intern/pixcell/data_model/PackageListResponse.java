package com.i2i.intern.pixcell.data_model;

public class PackageListResponse {

    private int packageId;
    private String packageName;
    private int amountMinutes;
    private double price;
    private int amountData;
    private int amountSms;
    private int period;

    public PackageListResponse(int packageId, String packageName, int amountMinutes, double price, int amountData, int amountSms, int period) {
        this.packageId = packageId;
        this.packageName = packageName;
        this.amountMinutes = amountMinutes;
        this.price = price;
        this.amountData = amountData;
        this.amountSms = amountSms;
        this.period = period;
    }

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public int getAmountMinutes() {
        return amountMinutes;
    }

    public void setAmountMinutes(int amountMinutes) {
        this.amountMinutes = amountMinutes;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAmountData() {
        return amountData;
    }

    public void setAmountData(int amountData) {
        this.amountData = amountData;
    }

    public int getAmountSms() {
        return amountSms;
    }

    public void setAmountSms(int amountSms) {
        this.amountSms = amountSms;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }
}

package com.i2iintern.AOM_demo.Model;

public class PackageBalance {
    private Integer packageId;
    private String packageName;
    private Integer amountMinutes;
    private double price;
    private Integer amountData;
    private Integer amountSms;
    private Integer period;

    public PackageBalance() {
    }

    public PackageBalance(Integer packageId) {
        this.packageId = packageId;
    }
    public PackageBalance(Integer packageId, String packageName, Integer amountMinutes, double price, Integer amountData, Integer amountSms, Integer period) {
        this.packageId = packageId;
        this.packageName = packageName;
        this.amountMinutes = amountMinutes;
        this.price = price;
        this.amountData = amountData;
        this.amountSms = amountSms;
        this.period = period;
    }

    public Integer getPackageId() {
        return packageId;
    }

    public void setPackageId(Integer packageId) {
        this.packageId = packageId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Integer getAmountMinutes() {
        return amountMinutes;
    }

    public void setAmountMinutes(Integer amountMinutes) {
        this.amountMinutes = amountMinutes;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Integer getAmountData() {
        return amountData;
    }

    public void setAmountData(Integer amountData) {
        this.amountData = amountData;
    }

    public Integer getAmountSms() {
        return amountSms;
    }

    public void setAmountSms(Integer amountSms) {
        this.amountSms = amountSms;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }
}

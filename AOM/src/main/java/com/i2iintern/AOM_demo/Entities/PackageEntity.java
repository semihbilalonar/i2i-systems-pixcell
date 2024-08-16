package com.i2iintern.AOM_demo.Entities;

public class PackageEntity {

    private long package_id;
    private long cust_id;
    private String package_name;
    private double price;
    private long amount_minutes;
    private long amount_data;
    private long amount_sms;
    private long period;

    public PackageEntity(long packageId, String packageName, double price, long amountMinutes, long amountData, long amountSms, long period){

    }
    public PackageEntity(long package_id, long cust_id, String package_name, double price, long amount_minutes, long amount_data, long amount_sms, long period) {
        this.package_id = package_id;
        this.cust_id = cust_id;
        this.package_name = package_name;
        this.price = price;
        this.amount_minutes = amount_minutes;
        this.amount_data = amount_data;
        this.amount_sms = amount_sms;
        this.period = period;
    }

    public long getPackage_id() {
        return package_id;
    }

    public void setPackage_id(long package_id) {
        this.package_id = package_id;
    }

    public long getCust_id() {
        return cust_id;
    }

    public void setCust_id(long cust_id) {
        this.cust_id = cust_id;
    }

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getAmount_minutes() {
        return amount_minutes;
    }

    public void setAmount_minutes(long amount_minutes) {
        this.amount_minutes = amount_minutes;
    }

    public long getAmount_data() {
        return amount_data;
    }

    public void setAmount_data(long amount_data) {
        this.amount_data = amount_data;
    }

    public long getAmount_sms() {
        return amount_sms;
    }

    public void setAmount_sms(long amount_sms) {
        this.amount_sms = amount_sms;
    }

    public long getPeriod() {
        return period;
    }

    public void setPeriod(long period) {
        this.period = period;
    }


}

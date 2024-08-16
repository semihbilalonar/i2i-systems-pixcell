package com.i2iintern.AOM_demo.Entities;

import java.math.BigDecimal;

public class UserBalanceEntity {
    private String MSISDN;
    private String PackageName;
    private String EDate;
    private BigDecimal data;
    private BigDecimal sms;
    private BigDecimal minute;
    private String username;
    private long packageData;
    private long packageMinute;
    private long packageSms;
    private long AmountMoney;
    private long price;

    public UserBalanceEntity(String MSISDN, String packageName, String EDate, BigDecimal data, BigDecimal sms, BigDecimal minute, String username, long packageData, long packageMinute, long packageSms, long amountMoney, long price) {
        this.MSISDN = MSISDN;
        PackageName = packageName;
        this.EDate = EDate;
        this.data = data;
        this.sms = sms;
        this.minute = minute;
        this.username = username;
        this.packageData = packageData;
        this.packageMinute = packageMinute;
        this.packageSms = packageSms;
        AmountMoney = amountMoney;
        this.price = price;
    }
    public String getMSISDN() {
        return MSISDN;
    }

    public void setMSISDN(String MSISDN) {
        this.MSISDN = MSISDN;
    }

    public String getPackageName() {
        return PackageName;
    }

    public void setPackageName(String packageName) {
        PackageName = packageName;
    }

    public String getEDate() {
        return EDate;
    }

    public void setEDate(String EDate) {
        this.EDate = EDate;
    }

    public BigDecimal getData() {
        return data;
    }

    public void setData(BigDecimal data) {
        this.data = data;
    }

    public BigDecimal getSms() {
        return sms;
    }

    public void setSms(BigDecimal sms) {
        this.sms = sms;
    }

    public BigDecimal getMinute() {
        return minute;
    }

    public void setMinute(BigDecimal minute) {
        this.minute = minute;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getPackageData() {
        return packageData;
    }

    public void setPackageData(long packageData) {
        this.packageData = packageData;
    }

    public long getPackageMinute() {
        return packageMinute;
    }

    public void setPackageMinute(long packageMinute) {
        this.packageMinute = packageMinute;
    }

    public long getPackageSms() {
        return packageSms;
    }

    public void setPackageSms(long packageSms) {
        this.packageSms = packageSms;
    }

    public long getAmountMoney() {
        return AmountMoney;
    }

    public void setAmountMoney(long amountMoney) {
        AmountMoney = amountMoney;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }


}

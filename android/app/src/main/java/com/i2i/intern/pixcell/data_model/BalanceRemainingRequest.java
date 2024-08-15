package com.i2i.intern.pixcell.data_model;

public class BalanceRemainingRequest {

    String msisdn;
    int balanceData;
    int balanceSms;
    int balanceMinutes;

    public BalanceRemainingRequest(String msisdn, int balanceData, int balanceSms, int balanceMinutes) {
        this.msisdn = msisdn;
        this.balanceData = balanceData;
        this.balanceSms = balanceSms;
        this.balanceMinutes = balanceMinutes;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public int getBalanceData() {
        return balanceData;
    }

    public int getBalanceSms() {
        return balanceSms;
    }

    public int getBalanceMinutes() {
        return balanceMinutes;
    }

}

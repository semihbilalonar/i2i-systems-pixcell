package com.i2i.intern.pixcell.data_model;

public class BalanceRemainingResponse {

    double internet;
    double sms;
    double call;

    public BalanceRemainingResponse(double internet, double sms, double call) {
        this.internet = internet;
        this.sms = sms;
        this.call = call;
    }

    public double getInternet() {
        return internet;
    }

    public void setInternet(double internet) {
        this.internet = internet;
    }

    public double getSms() {
        return sms;
    }

    public void setSms(double sms) {
        this.sms = sms;
    }

    public double getCall() {
        return call;
    }

    public void setCall(double call) {
        this.call = call;
    }
}

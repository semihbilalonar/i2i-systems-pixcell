package com.i2i.intern.pixcell.data_model;

public class SignInRequest {

    private String msisdn;
    private String password;

    public SignInRequest(String msisdn, String password) {
        this.msisdn = msisdn;
        this.password = password;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

}

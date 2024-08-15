package com.i2i.intern.pixcell.data_model;

public class ForgotPasswordRequest {
    private String msisdn;
    private String security_key;

    public ForgotPasswordRequest(String msisdn, String security_key) {
        this.msisdn = msisdn;
        this.security_key = security_key;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

}

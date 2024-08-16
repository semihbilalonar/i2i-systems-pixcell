package com.i2iintern.AOM_demo.Entities;

public class ForgotPassword {
    private String MSISDN;
    private String EMAIL;
    private String SECURITY_KEY;

    public ForgotPassword(String msisdn,String email, String securityKey) {
        this.MSISDN = msisdn;
        this.EMAIL = email;
        this.SECURITY_KEY = securityKey;
    }

    public String getMSISDN() {
        return MSISDN;
    }

    public void setMSISDN(String MSISDN) {
        this.MSISDN = MSISDN;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getSECURITY_KEY() {
        return SECURITY_KEY;
    }

    public void setSECURITY_KEY(String SECURITY_KEY) {
        this.SECURITY_KEY = SECURITY_KEY;
    }


}
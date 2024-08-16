package com.i2iintern.AOM_demo.Entities;

public class Login {
    private String MSISDN;
    private String password;

    public Login(String MSISDN, String password) {
        this.MSISDN = MSISDN;
        this.password = password;
    }

    public String getMSISDN() {
        return MSISDN;
    }

    public Login() {
    }

    public void setMSISDN(String MSISDN) {
        this.MSISDN = MSISDN;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
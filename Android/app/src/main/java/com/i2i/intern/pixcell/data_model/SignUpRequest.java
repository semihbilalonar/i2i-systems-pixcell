package com.i2i.intern.pixcell.data_model;

public class SignUpRequest {

    private String msisdn;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String security_key;
    private int package_id;

    public SignUpRequest(String msisdn, String name, String surname, String email, String password, String security_key, int package_id) {
        this.msisdn = msisdn;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.security_key = security_key;
        this.package_id = package_id;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
package com.i2iintern.AOM_demo.Entities;

import java.util.Date;

public class CustomerEntity {

    private long cust_id;
    private long msisdn;
    private String name;
    private String surname;
    private String email;
    private String password;
    private Date str_date;
    private String status;
    private String security_key;

    private Long packageId;



    public CustomerEntity(long cust_id, long msisdn, String name, String surname, String email, String password, String status, String security_key) {
        this.cust_id = cust_id;
        this.msisdn = msisdn;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
       // this.str_date = str_date;
        this.status = status;
        this.security_key = security_key;
    }

    public CustomerEntity(long cust_id, long msisdn, String name, String surname, String email, String password,Date str_date, String status, String security_key,long packageId) {
        this.cust_id = cust_id;
        this.msisdn = msisdn;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.str_date = str_date;
        this.status = status;
        this.security_key = security_key;
        this.packageId=packageId;
    }
    public CustomerEntity(long cust_id,long packageId) {
        this.cust_id = cust_id;
        this.packageId=packageId;
    }

    public CustomerEntity(){

    }

    public CustomerEntity(long cust_id, long msisdn, String name, String surname, String email, String password, java.sql.Date str_date, String status, String security_key) {

        this.cust_id = cust_id;
        this.msisdn = msisdn;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.str_date = str_date;
        this.status = status;
        this.security_key = security_key;
        this.packageId=packageId;

    }

    public long getCust_id() {
        return cust_id;
    }

    public void setCust_id(long cust_id) {
        this.cust_id = cust_id;
    }

    public long getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(long msisdn) {
        this.msisdn = msisdn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getStr_date() {
        return str_date;
    }

    public void setStr_date(Date str_date) {
        this.str_date = str_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSecurity_key() {
        return security_key;
    }

    public void setSecurity_key(String security_key) {
        this.security_key = security_key;
    }
    public Long getPackageId() {
        return packageId;
    }

    public void setPackageId(Long packageId) {
        this.packageId = packageId;
    }

}

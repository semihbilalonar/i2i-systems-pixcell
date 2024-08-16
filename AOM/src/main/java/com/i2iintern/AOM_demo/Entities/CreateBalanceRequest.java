package com.i2iintern.AOM_demo.Entities;

public class CreateBalanceRequest {


    long cust_id;
    long package_id;

    public CreateBalanceRequest(long cust_id, long package_id) {

        this.cust_id = cust_id;
        this.package_id = package_id;
    }


    public long getCust_id() {
        return cust_id;
    }

    public void setCust_id(long cust_id) {
        this.cust_id = cust_id;
    }

    public long getPackage_id() {
        return package_id;
    }

    public void setPackage_id(long package_id) {
        this.package_id = package_id;
    }

}

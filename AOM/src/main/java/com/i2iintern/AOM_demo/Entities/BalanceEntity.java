package com.i2iintern.AOM_demo.Entities;

import java.sql.Timestamp;
import java.util.Date;

public class BalanceEntity {

    private long balance_id;
    private long package_id;
    private long cust_id;
    private long partition_id;
    private int bal_lvl_minutes;
    private int bal_lvl_sms;
    private int bal_lvl_data;
    private int bal_lvl_money;
    private Timestamp sdate;
    private Timestamp edate;
    private long price;

    public BalanceEntity(){}
    public BalanceEntity(long balance_id, long package_id, long cust_id,long partition_id, int bal_lvl_minutes, int bal_lvl_sms, int bal_lvl_data, int bal_lvl_money, Timestamp sdate, Timestamp edate, long price) {
        this.balance_id = balance_id;
        this.package_id = package_id;
        this.cust_id = cust_id;
        this.partition_id=partition_id;
        this.bal_lvl_minutes = bal_lvl_minutes;
        this.bal_lvl_sms = bal_lvl_sms;
        this.bal_lvl_data = bal_lvl_data;
        this.bal_lvl_money = bal_lvl_money;
        this.sdate = sdate;
        this.edate = edate;
        this.price = price;
    }
    public BalanceEntity(long package_id, long cust_id) {
        this.package_id = package_id;
        this.cust_id = cust_id;
    }
    public long getBalance_id() {
        return balance_id;
    }

    public void setBalance_id(long balance_id) {
        this.balance_id = balance_id;
    }

    public long getPackage_id() {
        return package_id;
    }

    public void setPackage_id(long package_id) {
        this.package_id = package_id;
    }

    public long getCust_id() {
        return cust_id;
    }

    public void setCust_id(long cust_id) {
        this.cust_id = cust_id;
    }

    public long getPartition_id() {
        return partition_id;
    }

    public void setPartition_id(long partition_id) {
        this.partition_id = partition_id;
    }

    public int getBal_lvl_minutes() {
        return bal_lvl_minutes;
    }

    public void setBal_lvl_minutes(int bal_lvl_minutes) {
        this.bal_lvl_minutes = bal_lvl_minutes;
    }

    public int getBal_lvl_sms() {
        return bal_lvl_sms;
    }

    public void setBal_lvl_sms(int bal_lvl_sms) {
        this.bal_lvl_sms = bal_lvl_sms;
    }

    public int getBal_lvl_data() {
        return bal_lvl_data;
    }

    public void setBal_lvl_data(int bal_lvl_data) {
        this.bal_lvl_data = bal_lvl_data;
    }

    public int getBal_lvl_money() {
        return bal_lvl_money;
    }

    public void setBal_lvl_money(int bal_lvl_money) {
        this.bal_lvl_money = bal_lvl_money;
    }

    public Timestamp getSdate() {
        return (Timestamp) sdate;
    }

    public void setSdate(Date sdate) {
        this.sdate = (Timestamp) sdate;
    }

    public Timestamp getEdate() {
        return (Timestamp) edate;
    }

    public void setEdate(Date edate) {
        this.edate = (Timestamp) edate;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }



}

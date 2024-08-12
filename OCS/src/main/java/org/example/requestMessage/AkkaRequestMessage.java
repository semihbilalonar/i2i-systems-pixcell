package org.example.requestMessage;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class AkkaRequestMessage {

    private double unitPrice;

    private double totalUsagePrice;

    public void calculateTotalPrice(){
        if(Objects.equals(this.getType(), "data")){this.totalUsagePrice = ((double) this.getUsageAmount() / 1000 ) * this.getUnitPrice();}
        else{this.totalUsagePrice = this.getUsageAmount() * this.getUnitPrice();}
    }

    @JsonProperty("Type")
    protected String Type;

    @JsonProperty("Date")
    protected String Date;

    @JsonProperty("Location")
    protected int Location;

    @JsonProperty("UsageAmount")
    protected Integer UsageAmount;

    @JsonProperty("SenderMSISDN")
    protected String SenderMSISDN;

    @JsonProperty("ReceiverMSISDN")
    protected String ReceiverMSISDN;

    @JsonProperty("ratingNumber")
    protected int ratingNumber;

    @JsonProperty("PartitionKey")
    protected int PartitionKey;


    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }



    public double getTotalUsagePrice() {
        return totalUsagePrice;
    }

    public void setTotalUsagePrice(double totalUsagePrice) {
        this.totalUsagePrice = totalUsagePrice;
    }



    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }



    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }



    public int getLocation() {
        return Location;
    }

    public void setLocation(int location) {
        Location = location;
    }



    public Integer getUsageAmount() {
        return UsageAmount;
    }

    public void setUsageAmount(Integer usageAmount) {
        UsageAmount = usageAmount;
    }



    public String getSenderMSISDN() {
        return SenderMSISDN;
    }

    public void setSenderMSISDN(String senderMSISDN) {
        SenderMSISDN = senderMSISDN;
    }



    public String getReceiverMSISDN() {
        return ReceiverMSISDN;
    }

    public void setReceiverMSISDN(String receiverMSISDN) {
        ReceiverMSISDN = receiverMSISDN;
    }



    public int getRatingNumber() {
        return ratingNumber;
    }

    public void setRatingNumber(int ratingNumber) {
        this.ratingNumber = ratingNumber;
    }



    public int getPartitionKey() {
        return PartitionKey;
    }

    public void setPartitionKey(int partitionKey) {
        PartitionKey = partitionKey;
    }

}
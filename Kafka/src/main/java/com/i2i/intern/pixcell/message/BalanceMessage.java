package com.i2i.intern.pixcell.message;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



@JsonIgnoreProperties(ignoreUnknown = true)
public class BalanceMessage implements Message {
    private BalanceType type;
    private Long msisdn;
    private Integer remain;
    private Integer amount;

    public BalanceMessage(BalanceType type, Long msisdn, Integer remain,Integer amount) {
        this.type = type;
        this.msisdn = msisdn;
        this.remain = remain;
        this.amount=amount;
    }

    public BalanceMessage() {
    }
    public Long getMsisdn() {
        return msisdn;
    }
    public void setMsisdn(String msisdn) {
        this.msisdn = Long.valueOf(msisdn);
    }

    public int getRemain() {
        return remain;
    }
    public void setRemain(Integer remain) {
        this.remain = remain;
    }
    public BalanceType getType() {
        return type;
    }
    public void setType(BalanceType type) {
        this.type = type;
    }
    public int getAmount() {
        return amount;
    }
}

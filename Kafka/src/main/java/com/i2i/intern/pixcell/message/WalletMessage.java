package com.i2i.intern.pixcell.message;

public class WalletMessage {
    private BalanceType type;
    private Long msisdn;
    private Integer remain;


    public WalletMessage(BalanceType type, Long msisdn, Integer remain) {
        this.type = type;
        this.msisdn = msisdn;
        this.remain = remain;

    }


    public BalanceType getType() {
        return type;
    }

    public Long getMsisdn() {
        return msisdn;
    }

    public Integer getRemain() {
        return remain;
    }



}

package com.i2i.intern.pixcell.message;

import java.sql.Timestamp;

public class UsageRecordMessage implements Message {
    private BalanceType usageType;
    private Long msisdn;
    private Integer remain ;



    public UsageRecordMessage(BalanceType usageType, Long msisdn, Integer remain) {
        this.usageType = usageType;
        this.msisdn = msisdn;
        this.remain = remain;
    }
    public BalanceType getUsageType() {
        return usageType;
    }
    public Long getMsisdn() {
        return msisdn;
    }
    public Integer getRemain() {
        return remain;
    }
}



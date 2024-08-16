package com.i2i.intern.pixcell.message;

public class NotificationMessage implements Message {
    private BalanceType type;
    private Long msisdn;
    private Integer remain;
    private String name;
    private String surname;
    private String email;
    private Integer packageBalance;


    public NotificationMessage() {
    }


    public NotificationMessage(BalanceType type, Long msisdn, Integer remain, String name, String surname, String email, Integer packageBalance) {
        this.type = type;
        this.msisdn = msisdn;
        this.remain = remain;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.packageBalance = packageBalance;
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


    public String getName() {
        return name;
    }



    public String getSurname() {
        return surname;
    }



    public String getEmail() {
        return email;
    }


    public Integer getPackageBalance() {
        return packageBalance;
    }


}



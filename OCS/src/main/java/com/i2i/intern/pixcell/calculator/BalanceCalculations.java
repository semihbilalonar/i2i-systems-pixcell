package com.i2i.intern.pixcell.calculator;

import com.i2i.intern.pixcell.kafka.KafkaOperator;
import com.i2i.intern.pixcell.requestMessage.AkkaRequestMessage;
import com.i2i.intern.pixcell.voltdb.VoltDbOperation;

import java.util.Date;

public class BalanceCalculations {

    private final VoltDbOperation voltOperation = new VoltDbOperation();
    private final KafkaOperator kafkaOperator = new KafkaOperator();

    public void calculateVoiceRequest(AkkaRequestMessage requestMessage) {
        System.out.println("VOICE Request Calculating ...");

        int requestUsageAmount = requestMessage.getUsageAmount();
        double totalPrice = requestMessage.getTotalUsagePrice();
        long msisdn = Long.parseLong(requestMessage.getSenderMSISDN());

        int userVoiceBalance = voltOperation.getMinutesBalance(msisdn);
        int userWalletBalance = voltOperation.getMoneyBalance(msisdn);
        int uID = voltOperation.getUserID(msisdn);

        System.out.println("User ID: " + uID);
        System.out.println("Request Amount: " + requestUsageAmount);
        System.out.println("Total Price: " + totalPrice);
        System.out.println("Voice Balance: " + userVoiceBalance);
        System.out.println("Wallet Balance: " + userWalletBalance);

        if (userVoiceBalance <= 0) {
            if (userWalletBalance <= 0) {
                System.out.println("No Sufficient VOICE and WALLET Balance");
            } else if (userWalletBalance >= totalPrice) {
                System.out.println("VOICE Request * WALLET * Condition");
                voltOperation.updateMoneyBalance(msisdn, -((int) totalPrice));
                System.out.println("*** DB SENT ***");
                 kafkaOperator.sendKafkaWalletMessage(requestMessage.getType(), msisdn,  voltOperation.getMoneyBalance(msisdn));
                System.out.println("*** KAFKA SENT ***");
            } else {
                System.out.println("No Sufficient WALLET Balance");
            }
        } else if (userVoiceBalance >= requestUsageAmount) {
            System.out.println("VOICE Request * NORMAL * Condition");
            voltOperation.updateVoiceBalance(msisdn, -requestUsageAmount);
            System.out.println("*** DB SENT ***");
             kafkaOperator.sendKafkaVoiceUsageMessage(requestMessage.getType(), msisdn, uID, requestUsageAmount,voltOperation.getMinutesBalance(msisdn),voltOperation.getUserName(msisdn),voltOperation.getUserSurname(msisdn),voltOperation.getUserMail(msisdn));
             kafkaOperator.sendKafkaUsageVoiceMessage(requestMessage.getType(),msisdn,voltOperation.getMinutesBalance(msisdn),requestUsageAmount);
             kafkaOperator.sendKafkaBalanceVoiceMessage(requestMessage.getType(),msisdn,voltOperation.getMinutesBalance(msisdn));
             kafkaOperator.sendKafkaNotificationVoiceMessage(requestMessage.getType(),msisdn,voltOperation.getMinutesBalance(msisdn),voltOperation.getUserName(msisdn),voltOperation.getUserSurname(msisdn),voltOperation.getUserMail(msisdn), voltOperation.getCustomerPackageMinutes(msisdn));
            System.out.println("*** KAFKA SENT ***");
        } else {
            // Handle case where voice balance is insufficient
            int remainingUsage = requestUsageAmount - userVoiceBalance;
            requestMessage.setUsageAmount(remainingUsage);
            requestMessage.calculateTotalPrice();
            voltOperation.updateMoneyBalance(msisdn, -((int) requestMessage.getTotalUsagePrice()));
            voltOperation.updateVoiceBalance(msisdn, 0); // all available minutes used
            System.out.println("*** DB SENT ***");
            kafkaOperator.sendKafkaWalletMessage(requestMessage.getType(), msisdn,  voltOperation.getMoneyBalance(msisdn));
             kafkaOperator.sendKafkaVoiceUsageMessage(requestMessage.getType(), msisdn, uID, userVoiceBalance,voltOperation.getMinutesBalance(msisdn),voltOperation.getUserName(msisdn),voltOperation.getUserSurname(msisdn),voltOperation.getUserMail(msisdn));
             kafkaOperator.sendKafkaUsageVoiceMessage(requestMessage.getType(),msisdn,voltOperation.getMinutesBalance(msisdn),requestUsageAmount);
             kafkaOperator.sendKafkaBalanceVoiceMessage(requestMessage.getType(),msisdn,voltOperation.getMinutesBalance(msisdn));
             kafkaOperator.sendKafkaNotificationVoiceMessage(requestMessage.getType(),msisdn,voltOperation.getMinutesBalance(msisdn),voltOperation.getUserName(msisdn),voltOperation.getUserSurname(msisdn),voltOperation.getUserMail(msisdn),voltOperation.getCustomerPackageMinutes(msisdn));
            System.out.println("*** KAFKA SENT ***");
        }
    }

    public void calculateSMSRequest(AkkaRequestMessage requestMessage) {
        System.out.println("SMS Request Calculating ...");

        int requestUsageAmount = requestMessage.getUsageAmount();
        double totalPrice = requestMessage.getTotalUsagePrice();
        long msisdn = Long.parseLong(requestMessage.getSenderMSISDN());

        int userSMSBalance = voltOperation.getSmsBalance(msisdn);
        int userWalletBalance = voltOperation.getMoneyBalance(msisdn);
        int uID = voltOperation.getUserID(msisdn);

        System.out.println("User ID: " + uID);
        System.out.println("Request Amount: " + requestUsageAmount);
        System.out.println("Total Price: " + totalPrice);
        System.out.println("SMS Balance: " + userSMSBalance);
        System.out.println("Wallet Balance: " + userWalletBalance);

        if (userSMSBalance <= 0) {
            if (userWalletBalance <= 0) {
                System.out.println("No Sufficient WALLET and SMS Balance");
            } else if (userWalletBalance >= totalPrice) {
                System.out.println("SMS Request * WALLET * Condition");
                voltOperation.updateMoneyBalance(msisdn, -((int) totalPrice));
                System.out.println("*** DB SENT ***");
                kafkaOperator.sendKafkaWalletMessage(requestMessage.getType(), msisdn,  voltOperation.getMoneyBalance(msisdn));
                System.out.println("*** KAFKA SENT ***");
            }
        } else {
            System.out.println("SMS Request * NORMAL * Condition");
            voltOperation.updateSmsBalance(msisdn, -requestUsageAmount);
            System.out.println("*** DB SENT ***");
             kafkaOperator.sendKafkaSmsUsageMessage(requestMessage.getType(), msisdn, uID, requestUsageAmount,voltOperation.getSmsBalance(msisdn),voltOperation.getUserName(msisdn),voltOperation.getUserSurname(msisdn),voltOperation.getUserMail(msisdn));
             kafkaOperator.sendKafkaUsageSmsMessage(requestMessage.getType(),msisdn,voltOperation.getSmsBalance(msisdn),requestUsageAmount);
             kafkaOperator.sendKafkaBalanceSmsMessage(requestMessage.getType(),msisdn,voltOperation.getSmsBalance(msisdn));
             kafkaOperator.sendKafkaNotificationSmsMessage(requestMessage.getType(),msisdn,voltOperation.getSmsBalance(msisdn),voltOperation.getUserName(msisdn),voltOperation.getUserSurname(msisdn),voltOperation.getUserMail(msisdn), voltOperation.getCustomerPackageSms(msisdn));
            System.out.println("*** KAFKA SENT ***");
        }
    }

    public void calculateDataRequest(AkkaRequestMessage requestMessage) {
        System.out.println("DATA Request Calculating ...");

        int requestUsageAmount = requestMessage.getUsageAmount();
        double totalPrice = requestMessage.getTotalUsagePrice();
        long msisdn = Long.parseLong(requestMessage.getSenderMSISDN());

        int userDataBalance = voltOperation.getInternetBalance(msisdn);
        int userWalletBalance = voltOperation.getMoneyBalance(msisdn);
        int uID = voltOperation.getUserID(msisdn);

        System.out.println("User ID: " + uID);
        System.out.println("Request Amount: " + requestUsageAmount);
        System.out.println("Total Price: " + totalPrice);
        System.out.println("DATA Balance: " + userDataBalance);
        System.out.println("Wallet Balance: " + userWalletBalance);

        if (userDataBalance <= 0) {
            if (userWalletBalance <= 0) {
                System.out.println("No Sufficient DATA and WALLET Balance");
            } else if (userWalletBalance >= totalPrice) {
                System.out.println("DATA Request * WALLET * Condition");
                voltOperation.updateMoneyBalance(msisdn, -((int) totalPrice));
                System.out.println("*** DB SENT ***");
                kafkaOperator.sendKafkaWalletMessage(requestMessage.getType(), msisdn,  voltOperation.getMoneyBalance(msisdn));                System.out.println("*** KAFKA SENT ***");
            } else {
                System.out.println("No Sufficient WALLET Balance");
            }
        } else if (userDataBalance >= requestUsageAmount) {
            System.out.println("DATA Request * NORMAL * Condition");
            voltOperation.updateDataBalance(msisdn, -requestUsageAmount);
            System.out.println("*** DB SENT ***");
             checkUsageThreshold(requestMessage.getType(),msisdn);
             kafkaOperator.sendKafkaDataUsageMessage(requestMessage.getType(), msisdn, uID, requestUsageAmount,voltOperation.getInternetBalance(msisdn),voltOperation.getUserName(msisdn),voltOperation.getUserSurname(msisdn),voltOperation.getUserMail(msisdn));
             kafkaOperator.sendKafkaUsageDataMessage(requestMessage.getType(),msisdn,voltOperation.getInternetBalance(msisdn),requestUsageAmount);
             kafkaOperator.sendKafkaBalanceDataMessage(requestMessage.getType(),msisdn,voltOperation.getInternetBalance(msisdn));
             kafkaOperator.sendKafkaNotificationDataMessage(requestMessage.getType(),msisdn,voltOperation.getInternetBalance(msisdn),voltOperation.getUserName(msisdn),voltOperation.getUserSurname(msisdn),voltOperation.getUserMail(msisdn), voltOperation.getCustomerPackageData(msisdn));
            System.out.println("*** KAFKA SENT ***");
        } else {
            int remainingUsage = requestUsageAmount - userDataBalance;
            requestMessage.setUsageAmount(remainingUsage);
            requestMessage.calculateTotalPrice();
            voltOperation.updateMoneyBalance(msisdn, -((int) requestMessage.getTotalUsagePrice()));
            voltOperation.updateDataBalance(msisdn, 0); // all available data used
            System.out.println("*** DB SENT ***");
            kafkaOperator.sendKafkaWalletMessage(requestMessage.getType(), msisdn,  voltOperation.getMoneyBalance(msisdn));             kafkaOperator.sendKafkaDataUsageMessage(requestMessage.getType(), msisdn, uID, userDataBalance,voltOperation.getInternetBalance(msisdn),voltOperation.getUserName(msisdn),voltOperation.getUserSurname(msisdn),voltOperation.getUserMail(msisdn));
             kafkaOperator.sendKafkaUsageDataMessage(requestMessage.getType(),msisdn,voltOperation.getInternetBalance(msisdn),requestUsageAmount);
             kafkaOperator.sendKafkaBalanceDataMessage(requestMessage.getType(),msisdn,voltOperation.getInternetBalance(msisdn));
             kafkaOperator.sendKafkaNotificationDataMessage(requestMessage.getType(),msisdn,voltOperation.getInternetBalance(msisdn),voltOperation.getUserName(msisdn),voltOperation.getUserSurname(msisdn),voltOperation.getUserMail(msisdn), voltOperation.getCustomerPackageData(msisdn));
            System.out.println("*** KAFKA SENT ***");
        }
    }

    public void checkUsageThreshold(String type, Long msisdn) {


        int threshold80;
        int threshold100 = 0;
        switch (type) {
            case "data":
                threshold80 = (int) (voltOperation.getCustomerPackageMinutes(msisdn) * 0.20);
                break;
            case "sms":
                threshold80 = (int) (voltOperation.getCustomerPackageSms(msisdn) * 0.20);
                break;
            case "voice":
                threshold80 = (int) (voltOperation.getCustomerPackageData(msisdn) * 0.20);
                break;
            default:
                return;
        }
        if (voltOperation.getMinutesBalance(msisdn) <= threshold100) {
            kafkaOperator.sendKafkaUsageThresholdMessage(type, msisdn, ("100%"));
        } else if (voltOperation.getMinutesBalance(msisdn) <= threshold80) {
            kafkaOperator.sendKafkaUsageThresholdMessage(type, msisdn, ("80%"));
        }
    }


}
package com.i2i.intern.pixcell.voltdb;

import org.voltdb.VoltTable;
import org.voltdb.client.*;
import java.io.IOException;
import java.util.Arrays;


public class VoltDbOperation {

    private final String IP = "35.198.145.16";
    private final int PORT = 32794;

    private final Client clientInstance;

    public VoltDbOperation() {
        ClientConfig config = new ClientConfig();
        clientInstance = ClientFactory.createClient(config);
        try {
            clientInstance.createConnection(IP, PORT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int getUserID(long MSISDN) {
        ClientResponse response = null;

        try {
            response = clientInstance.callProcedure("GetCustomerIdByMSISDN", MSISDN);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (checkResponse(response)) {
            return (int) response.getResults()[0].getLong(0);
        }
        return -1;
    }

    public String getUserName(long MSISDN) {
        ClientResponse response = null;

        try {
            response = clientInstance.callProcedure("GetCustomerInfo", MSISDN);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (checkResponse(response)) {
            return response.getResults()[0].getString("NAME");
        }

        return null;
    }

    public String getUserSurname(long MSISDN) {
        ClientResponse response = null;

        try {
            response = clientInstance.callProcedure("GetCustomerInfo", MSISDN);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (checkResponse(response)) {
            return response.getResults()[0].getString("SURNAME");
        }

        return null;
    }

    public String getUserMail(long MSISDN) {
        ClientResponse response = null;

        try {
            response = clientInstance.callProcedure("GetCustomerInfo", MSISDN);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (checkResponse(response)) {
            return response.getResults()[0].getString("EMAIL");
        }

        return null;
    }

    private VoltTable getPackageDetailsByCustomerId(int cust_id) {
        ClientResponse response = null;

        try {
            response = clientInstance.callProcedure("PackageDetailsByID", cust_id);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (checkResponse(response)) {
            return response.getResults()[0];
        }

        return response.getResults()[0];
    }

    public int getCustomerPackageMinutes(long msisdn) {
        var id = getUserID(msisdn);
        var table = getPackageDetailsByCustomerId(id);

        return (int) table.getLong("AMOUNT_MINUTES");
    }

    public int getCustomerPackageSms(long msisdn) {
        var id = getUserID(msisdn);
        var table = getPackageDetailsByCustomerId(id);

        return (int) table.getLong("AMOUNT_SMS");
    }

    public int getCustomerPackageData(long msisdn) {
        var id = getUserID(msisdn);
        var table = getPackageDetailsByCustomerId(id);

        return (int) table.getLong("AMOUNT_DATA");
    }

    public int getCustomerPackagePeriod(long msisdn) {
        var id = getUserID(msisdn);
        var table = getPackageDetailsByCustomerId(id);

        return (int) table.getLong("PERIOD");
    }

    public int getCustomerPackagePrice(long msisdn) {
        var id = getUserID(msisdn);
        var table = getPackageDetailsByCustomerId(id);

        return (int) table.getLong("PRICE");
    }

    public String getCustomerPackageName(long msisdn) {
        var id = getUserID(msisdn);
        var table = getPackageDetailsByCustomerId(id);

        return table.getString("PACKAGE_NAME");
    }

    public int getInternetBalance(long MSISDN) {
        ClientResponse response = null;

        try {
            response = clientInstance.callProcedure("GetDataDataForCustomer", MSISDN);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (checkResponse(response)) {
            return (int) response.getResults()[0].getLong(0);
        }
        return -1;
    }

    public int getMinutesBalance(long MSISDN) {
        ClientResponse response = null;

        try {
            response = clientInstance.callProcedure("GetMinutesDataForCustomer", MSISDN);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (checkResponse(response)) {
            return (int) response.getResults()[0].getLong(0);
        }
        return -1;
    }

    public int getSmsBalance(long MSISDN) {
        ClientResponse response = null;

        try {
            response = clientInstance.callProcedure("GetSmsDataForCustomer", MSISDN);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (checkResponse(response)) {
            return (int) response.getResults()[0].getLong(0);
        }
        return -1;
    }

    public int getMoneyBalance(long MSISDN) {
        ClientResponse response = null;

        try {
            response = clientInstance.callProcedure("GetMoneyDataForCustomer", MSISDN);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (checkResponse(response)) {
            return (int) response.getResults()[0].getLong(0);
        }
        return -1;
    }

    public void setMinutesBalance(int amount_minutes, long MSISDN) {
        ClientResponse response = null;
        try {
            response = clientInstance.callProcedure("UpdateCustomerMinutes", amount_minutes, MSISDN);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setInternetBalance(int amount_internet, long MSISDN) {
        ClientResponse response = null;
        try {
            response = clientInstance.callProcedure("UpdateCustomerData", amount_internet, MSISDN);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setSmsBalance(int amount_sms, long MSISDN) {
        ClientResponse response = null;
        try {
            response = clientInstance.callProcedure("UpdateCustomerSms", amount_sms, MSISDN);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void setMoneyBalance(int amount_money, long MSISDN) {
        ClientResponse response = null;
        try {
            response = clientInstance.callProcedure("UpdateCustomerMoney", amount_money, MSISDN);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updateVoiceBalance(long MSISDN, int amount) {
        int currentBalance = getMinutesBalance(MSISDN);
        int newBalance = currentBalance + amount;
        setMinutesBalance(newBalance, MSISDN);
    }

    public void updateDataBalance(long MSISDN, int amount) {
        int currentBalance = getInternetBalance(MSISDN);
        int newBalance = currentBalance + amount;
        setInternetBalance(newBalance, MSISDN);
    }

    public void updateSmsBalance(long MSISDN, int amount) {
        int currentBalance = getSmsBalance(MSISDN);
        int newBalance = currentBalance + amount;
        setSmsBalance(newBalance, MSISDN);
    }

    public void updateMoneyBalance(long MSISDN,int amount) {
        int currentBalance = getMoneyBalance(MSISDN);
        int newBalance = currentBalance + amount;
        setMoneyBalance(newBalance, MSISDN);
    }

    private boolean checkResponse(ClientResponse response) {
        if (response.getStatus() == ClientResponse.SUCCESS &&
                response.getResults()[0].advanceRow()
        ) {
            return true;
        }
        System.out.println(Arrays.toString(response.getResults()));
        return false;
    }
}

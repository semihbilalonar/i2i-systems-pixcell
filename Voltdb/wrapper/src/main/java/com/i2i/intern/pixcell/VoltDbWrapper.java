package com.i2i.intern.pixcell;

import org.voltdb.VoltTable;
import org.voltdb.client.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class VoltDbWrapper {

    private final String IP = "35.198.145.16";
    private final int PORT = 32794;
    private final Client clientInstance;

    public VoltDbWrapper() {
        ClientConfig config = new ClientConfig();
        clientInstance = ClientFactory.createClient(config);
        try {
            clientInstance.createConnection(IP, PORT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void InsertCustomer(long msisdn, String name, String surname, String email, String password, String status, String securityKey) {
        ClientResponse response = null;

        try {
            response = clientInstance.callProcedure("InsertCustomer", msisdn, name, surname, email, password, status, securityKey);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        InitBalance(msisdn);
    }

    public void InsertCustomerWithPackage(long msisdn, String name, String surname, String email, String password, String status, String securityKey, int package_id) {
        ClientResponse response = null;

        try {
            response = clientInstance.callProcedure("InsertCustomer", msisdn, name, surname, email, password, status, securityKey);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        InitBalance(msisdn);
        setCustomerBalanceByPackageId(msisdn, package_id);
        updateMoneyBalance(msisdn, getPackagePrıce(package_id));
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

    public VoltTable getPackageDetails(int packageId) {
        ClientResponse response = null;

        try {
            response = clientInstance.callProcedure("GetPackageInfo", packageId);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (checkResponse(response)) {
            return response.getResults()[0];
        }

        return null;
    }

    public int[] getPackageIds() {
        VoltTable table = null;
        ClientResponse response = null;

        try {
            response = clientInstance.callProcedure("GetPackageIds");
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (checkResponse(response)) {
            table =  response.getResults()[0];
        }
        else {
            return null;
        }

        int[] packageIds = new int[table.getRowCount()];

        for (int i = 0; i < table.getRowCount(); i++) {
            packageIds[i] = (int) table.getLong(0);
            table.advanceRow();
        }

        return packageIds;
    }

    public int getPackageInternet(int package_id) {
        ClientResponse response = null;

        try {
            response = clientInstance.callProcedure("GetAmountData", package_id);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (checkResponse(response)) {
            return (int) response.getResults()[0].getLong(0);
        }
        return -1;
    }

    public int getPackageSms(int package_id) {
        ClientResponse response = null;

        try {
            response = clientInstance.callProcedure("GetAmountSMS", package_id);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (checkResponse(response)) {
            return (int) response.getResults()[0].getLong(0);
        }
        return -1;
    }

    public int getPackageMinutes(int package_id) {
        ClientResponse response = null;

        try {
            response = clientInstance.callProcedure("GetAmountMinutes", package_id);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (checkResponse(response)) {
            return (int) response.getResults()[0].getLong(0);
        }
        return -1;
    }

    public int getPackagePrıce(int package_id) {
        ClientResponse response = null;

        try {
            response = clientInstance.callProcedure("GetPackagePrice", package_id);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (checkResponse(response)) {
            return (int) response.getResults()[0].getLong(0);
        }
        return -1;
    }

    public List<List<Object>> AllPackages() {
        VoltTable table = null;
        ClientResponse response = null;
        List<List<Object>> packages = new ArrayList<>();

        try {
            response = clientInstance.callProcedure("AllPackages");
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (checkResponse(response)) {
            table =  response.getResults()[0];
        }
        else {
            return null;
        }

        for (int i = 0; i < table.getRowCount(); i++) {
            packages.add(List.of(table.getRowObjects()));
            table.advanceRow();
        }

        return packages;
    }

    public String getPackageName(int package_id) {
        ClientResponse response = null;

        try {
            response = clientInstance.callProcedure("GetPackageName", package_id);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (checkResponse(response)) {
            return (String) response.getResults()[0].getString(0);
        }
        return null;
    }

    public int getPackagePeriod(int package_id) {
        ClientResponse response = null;

        try {
            response = clientInstance.callProcedure("GetPackagePeriod ", package_id);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (checkResponse(response)) {
            return (int) response.getResults()[0].getLong(0);
        }
        return -1;
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

    public void setMoneyBalance(int amount_money, long MSISDN) {
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

    public void updateMoneyBalance(long MSISDN, int amount) {
        int currentBalance = getMoneyBalance(MSISDN);
        int newBalance = currentBalance + amount;
        setMoneyBalance(newBalance, MSISDN);
    }

    public void setCustomerBalanceByPackageId(long MSISDN, int package_id) {
        var table = getPackageDetails(package_id);

        String packageName = (String) table.get(1);
        int packagePrice = (int) table.get(2);
        int min = (int) table.get(3);
        int data = (int) table.get(4);
        int sms = (int) table.get(5);
        int period = (int) table.get(6);

        ClientResponse response = null;

        try {
            response = clientInstance.callProcedure("SetBalanceByPackageId", package_id, min, sms, data, packagePrice, getUserID(MSISDN));
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    // ---- PRIVATE,

    private void InitBalance(int cust_id, int package_id, int bal_lvl_money) {
        ClientResponse response = null;

        try {
            response = clientInstance.callProcedure("InitBalance", cust_id, package_id, bal_lvl_money);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
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

    private void InitBalance(long msisdn) {
        InitBalance(getUserID(msisdn), -1, 1000);
    }
}


package com.i2iintern.AOM_demo.Repository;


import java.io.IOException;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;
import com.i2iintern.AOM_demo.Connecters.OracleDbConnecter;
import com.i2iintern.AOM_demo.Connecters.VoltDbConnecter;
import com.i2iintern.AOM_demo.Connecters.VoltDbWrapper;
import com.i2iintern.AOM_demo.Entities.PackageEntity;
import com.i2iintern.AOM_demo.Model.CustomerBalance;
import com.i2iintern.AOM_demo.Model.PackageBalance;
import oracle.jdbc.OracleTypes;

import org.voltdb.VoltTable;
import org.voltdb.client.Client;
import org.voltdb.client.ClientResponse;
import org.voltdb.client.NoConnectionsException;
import org.voltdb.client.ProcCallException;


public class PackageRepository {

    VoltDbWrapper voltDbWrapper = new VoltDbWrapper();

    public List<PackageBalance> getAllPackages() throws IOException, ProcCallException, InterruptedException {
        List<List<Object>> rawPackages = voltDbWrapper.getAllPackages();
        List<PackageBalance> packages = new ArrayList<>();

        for (List<Object> rawPackage : rawPackages) {
            int packageId = (int) rawPackage.get(0);
            String packageName = (String) rawPackage.get(1);
            int packagePrice = (int) rawPackage.get(2);
            int packageMinutes = (int) rawPackage.get(3);
            int packageData = (int) rawPackage.get(4);
            int packageSms = (int) rawPackage.get(5);
            int packagePeriod = (int) rawPackage.get(6);
            PackageBalance packageBalance = new PackageBalance(packageId, packageName, packagePrice, packageMinutes, packageData, packageSms, packagePeriod);
            packages.add(packageBalance);
        }

        return packages;
    }

    public List<PackageBalance> getPackageDetailsByMsisdn(String msisdn) throws IOException, ProcCallException, InterruptedException {
        Object[] details = voltDbWrapper.getPackageDetailsByMsisdn(Long.parseLong(msisdn));
        if (details == null || details.length == 0) {
            return new ArrayList<>();
        }
        List<PackageBalance> packages = new ArrayList<>();
        PackageBalance packageBalance = new PackageBalance(
                (int) details[0],       // packageId
                (String) details[1],     // packageName
                (int) details[2],        // packagePrice
                (int) details[3],        // packageMinutes
                (int) details[4],        // packageData
                (int) details[5],        // packageSms
                (int) details[6]         // packagePeriod
        );
        packages.add(packageBalance);

        return packages;
    }

    public PackageBalance GetPackageById(int packageId) throws IOException, ProcCallException, InterruptedException {
        var table = voltDbWrapper.getPackageDetails(packageId);
        String packageName= (String) table.get(1);
        int packagePrice= (int) table.get(2);
        int packageMinutes= (int) table.get(3);
        int packageData= (int) table.get(4);
        int packageSms= (int) table.get(5);
        int packagePeriod= (int) table.get(6);
        PackageBalance packageBalance = new PackageBalance(packageId,packageName,packagePrice,packageMinutes,packageData,packageSms,packagePeriod);

        return packageBalance;
    }

    public PackageEntity getPackageByMSISDNinObject(String PACKAGE_ID) throws IOException, ProcCallException {
        VoltDbConnecter voltDbHelper = new VoltDbConnecter();
        Client client = voltDbHelper.client();
        ClientResponse response;
        response = client.callProcedure("getPackage",PACKAGE_ID);
        VoltTable tablePackageInfo = response.getResults()[0];
        tablePackageInfo.advanceRow();
        long packageID =tablePackageInfo.getLong(0);
        String packageName =tablePackageInfo.getString(1);
        long amountMinute =tablePackageInfo.getLong(2);
        long amountData =tablePackageInfo.getLong(3);
        long amountSMS =tablePackageInfo.getLong(4);
        long period =tablePackageInfo.getLong(5);

        return (new PackageEntity(packageID,packageName,0,amountMinute,amountData,amountSMS,period));

    }








}
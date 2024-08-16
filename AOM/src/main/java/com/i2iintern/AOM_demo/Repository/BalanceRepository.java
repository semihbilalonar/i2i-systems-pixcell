package com.i2iintern.AOM_demo.Repository;


import com.i2iintern.AOM_demo.Connecters.OracleDbConnecter;
import com.i2iintern.AOM_demo.Connecters.VoltDbConnecter;
import com.i2iintern.AOM_demo.Connecters.VoltDbWrapper;
import com.i2iintern.AOM_demo.Entities.BalanceEntity;
import com.i2iintern.AOM_demo.Entities.CustomerEntity;
import com.i2iintern.AOM_demo.Entities.UserBalanceEntity;
import com.i2iintern.AOM_demo.Model.CustomerBalance;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.voltdb.VoltTable;
import org.voltdb.client.Client;
import org.voltdb.client.ClientResponse;
import org.voltdb.client.NoConnectionsException;
import org.voltdb.client.ProcCallException;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class BalanceRepository {

    private VoltDbWrapper voltDbWrapper = new VoltDbWrapper();

    public List<BalanceEntity> getAllBalances() throws SQLException, ClassNotFoundException {
        // Veritabanı bağlantısını al
        Connection conn = OracleDbConnecter.getConnection();

        // SQL sorgusu
        String sql = "SELECT * FROM BALANCE";

        Statement stmt = null;
        ResultSet rs = null;
        List<BalanceEntity> balances = new ArrayList<>();

        try {
            // Statement oluştur ve sorguyu çalıştır
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            // Sonuçları al ve listeye ekle
            while (rs.next()) {
                BalanceEntity balance = new BalanceEntity();
                balance.setBalance_id(rs.getLong("balance_id"));
                balance.setPackage_id(rs.getLong("package_id"));
                balance.setCust_id(rs.getLong("cust_id"));
                balance.setPartition_id(rs.getLong("partition_id"));
                balance.setBal_lvl_minutes(rs.getInt("bal_lvl_minutes"));
                balance.setBal_lvl_sms(rs.getInt("bal_lvl_sms"));
                balance.setBal_lvl_data(rs.getInt("bal_lvl_data"));
                balance.setSdate(rs.getTimestamp("sdate"));
                balance.setEdate(rs.getTimestamp("edate"));
                balance.setBal_lvl_money(rs.getInt("bal_lvl_money"));

                balances.add(balance);
            }
        } finally {
            // Kaynakları serbest bırak
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }

        return balances;
    }

    public CustomerBalance GetRemainingCustomerBalanceByMsisdn(Long msisdn) throws IOException, ProcCallException, InterruptedException {
        long internetBalance=voltDbWrapper.getInternetBalance(msisdn);
        long smsBalance= voltDbWrapper.getSmsBalance(msisdn);
        long minutesBalance= voltDbWrapper.getMinutesBalance(msisdn);
        CustomerBalance customerBalance= new CustomerBalance(msisdn, (int) internetBalance, (int) smsBalance, (int) minutesBalance);
        return customerBalance;
    }
















}




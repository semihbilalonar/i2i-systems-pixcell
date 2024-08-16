package com.i2iintern.AOM_demo.Repository;

import com.i2iintern.AOM_demo.Connecters.OracleDbConnecter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.i2iintern.AOM_demo.Connecters.VoltDbWrapper;
import com.i2iintern.AOM_demo.Entities.BalanceEntity;
import com.i2iintern.AOM_demo.Entities.CreateBalanceRequest;
import com.i2iintern.AOM_demo.Entities.CustomerEntity;
import com.i2iintern.AOM_demo.Entities.Login;
import oracle.jdbc.internal.OracleTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Connection;
import java.sql.SQLException;

public class CustomerRepository {
    private BalanceRepository balanceRepository = new BalanceRepository();
    private VoltDbWrapper voltDbWrapper = new VoltDbWrapper();

    public List<CustomerEntity> getAllCustomers() throws SQLException, ClassNotFoundException {
        List<CustomerEntity> customers = new ArrayList<>();
        Connection conn = OracleDbConnecter.getConnection();
        CallableStatement cstmt = null;
        ResultSet rs = null;

        try {
            // Prosedürü çağırma
            String sql = "{call getAllCustomers(?)}";
            cstmt = conn.prepareCall(sql);
            cstmt.registerOutParameter(1, OracleTypes.CURSOR);
            cstmt.execute();

            // Sonuçları almak için ResultSet kullanma
            rs = (ResultSet) cstmt.getObject(1);

            while (rs.next()) {
                long customerId = rs.getLong("cust_id");
                long msisdn = rs.getLong("msisdn");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String status = rs.getString("status");
                String securityKey = rs.getString("security_key");

                CustomerEntity customer = new CustomerEntity(
                        customerId, msisdn, name, surname, email, password, status, securityKey
                );

                customers.add(customer);
            }
        } finally {
            if (rs != null) rs.close();
            if (cstmt != null) cstmt.close();
            if (conn != null) conn.close();
        }

        return customers;
    }

    public List<CustomerEntity> getCustomerByMsisdn(String MSISDN) throws ClassNotFoundException, SQLException {
        Connection conn = OracleDbConnecter.getConnection();
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        List<CustomerEntity> customers = new ArrayList<>();

        try {
            String procedureCall = "{call getCustomerInformation(?, ?)}";
            callableStatement = conn.prepareCall(procedureCall);
            callableStatement.setString(1, MSISDN);
            callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
            callableStatement.execute();

            resultSet = (ResultSet) callableStatement.getObject(2);

            while (resultSet.next()) {
                long CUST_ID = resultSet.getLong("CUST_ID");
                long _MSISDN = resultSet.getLong("MSISDN");
                String NAME = resultSet.getString("NAME");
                String SURNAME = resultSet.getString("SURNAME");
                String EMAIL = resultSet.getString("EMAIL");
                String PASSWORD = resultSet.getString("PASSWORD");
               // Date STR_DATE = Date.valueOf(resultSet.getString("STR_DATE"));
                String STATUS = resultSet.getString("STATUS");
                String SECURITY_KEY = resultSet.getString("SECURITY_KEY");

                customers.add(new CustomerEntity(CUST_ID, _MSISDN, NAME, SURNAME, EMAIL, PASSWORD, STATUS, SECURITY_KEY));
            }
        } finally {
            if (resultSet != null) resultSet.close();
            if (callableStatement != null) callableStatement.close();
            if (conn != null) conn.close();
        }
        System.out.println(customers);
        return customers;
    }

    public ResponseEntity<String> login(Login login) throws ClassNotFoundException, SQLException {
        Connection conn = OracleDbConnecter.getConnection();
        CallableStatement callableStatement = null;
        int matchCount = 0;

        try {
            // Prosedürü çağır
            String functionCall = "{? = call login(?, ?)}";
            callableStatement = conn.prepareCall(functionCall);
            callableStatement.registerOutParameter(1, Types.INTEGER);
            callableStatement.setString(2, login.getMSISDN());
            callableStatement.setString(3, login.getPassword());

            callableStatement.execute();

            // Geri dönen değeri alıyoruz
            matchCount = callableStatement.getInt(1);

        } finally {
            if (callableStatement != null) callableStatement.close();
            if (conn != null) conn.close();
        }

        // Kullanıcı login başarılı mı kontrol ediyoruz
        if (matchCount == 1) {
            System.out.println("Successful login.");
            return ResponseEntity.ok("Successful login\n" + login.getMSISDN());
        } else {
            System.out.println("Login failed.");
            return ResponseEntity.badRequest().body("Login failed!");
        }
    }

    public ResponseEntity<String> loginWithSecurityKey(Login login) throws ClassNotFoundException, SQLException {
        Connection conn = OracleDbConnecter.getConnection();
        CallableStatement callableStatement = null;
        int matchCount = 0;

        try {
            // Prosedürü çağır
            String functionCall = "{? = call login(?, ?)}";
            callableStatement = conn.prepareCall(functionCall);
            callableStatement.registerOutParameter(1, Types.INTEGER);
            callableStatement.setString(2, login.getMSISDN());
            callableStatement.setString(3, login.getPassword());

            callableStatement.execute();

            // Geri dönen değeri alıyoruz
            matchCount = callableStatement.getInt(1);

        } finally {
            if (callableStatement != null) callableStatement.close();
            if (conn != null) conn.close();
        }

        // Kullanıcı login başarılı mı kontrol ediyoruz
        if (matchCount == 1) {
            System.out.println("Successful login.");
            return ResponseEntity.ok("Successful login\n" + login.getMSISDN());
        } else {
            System.out.println("Login failed.");
            return ResponseEntity.badRequest().body("Login failed!");
        }
    }

    public ResponseEntity<String> createCustomerWithPackage(CustomerEntity customer) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        CallableStatement cstmt = null;

        try {
            conn = OracleDbConnecter.getConnection();
            String sql = "{call create_customer_with_package(?, ?, ?, ?, ?, ?, ?)}";
            cstmt = conn.prepareCall(sql);

            // Prosedüre parametreleri geç
            cstmt.setLong(1, customer.getMsisdn());
            cstmt.setString(2, customer.getName());
            cstmt.setString(3, customer.getSurname());
            cstmt.setString(4, customer.getEmail());
            cstmt.setString(5, customer.getPassword());
            cstmt.setString(6, customer.getSecurity_key());
            cstmt.setLong(7, customer.getPackageId());

            // Prosedürü çalıştır
            cstmt.execute();
                return new ResponseEntity<>("Customer and Balance created successfully", HttpStatus.CREATED);

        } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Customer creation failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            if (cstmt != null) try { cstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (conn != null) try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

}

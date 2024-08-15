package com.i2i.intern.pixcell;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class UserUsageDetailDAO {

    public void insertUserUsageDetail(long msisdn, String type, int remain, int amount, Timestamp usageDate) {
        String sql = "INSERT INTO userUsageDetail (msisdn, usage_date, usage_type, usage_duration, remain) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, String.valueOf(msisdn));
            statement.setTimestamp(2, usageDate);
            statement.setString(3, type);
            statement.setInt(4, amount);
            statement.setInt(5, remain);

            statement.executeUpdate();
            System.out.println("Record inserted into userUsageDetail for MSISDN: " + msisdn);

        } catch (SQLException e) {
            System.err.println("Error inserting record into userUsageDetail: " + e.getMessage());
        }
    }
}

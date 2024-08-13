import org.voltdb.*;
import java.sql.Date;
import java.util.Calendar;

public class InitBalance extends VoltProcedure {

    // Retrieve the current maximum balance_id
    public final SQLStmt getMaxBalanceIdStmt = new SQLStmt(
            "SELECT COALESCE(MAX(balance_id), 0) FROM balance;"
    );

    // Retrieve package details including period
    public final SQLStmt getPackageDetailsStmt = new SQLStmt(
            "SELECT amount_minutes, amount_sms, amount_data, price, period " +
                    "FROM package WHERE package_id = ?;"
    );

    // Insert a new balance record
    public final SQLStmt insertBalanceStmt = new SQLStmt(
            "INSERT INTO balance (balance_id, package_id, cust_id, bal_lvl_minutes, bal_lvl_sms, bal_lvl_data, sdate, edate, price, bal_lvl_money) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);"
    );

    public long run(int cust_id, int package_id, int bal_lvl_money) throws VoltAbortException {
        // Get the current maximum balance_id
        voltQueueSQL(getMaxBalanceIdStmt);
        VoltTable[] results = voltExecuteSQL();
        results[0].advanceRow();
        int maxBalanceId = (int) results[0].getLong(0);

        // Get package details including period
        voltQueueSQL(getPackageDetailsStmt, package_id);
        results = voltExecuteSQL();
        results[0].advanceRow();

        int amount_minutes = (int) results[0].getLong("amount_minutes");
        int amount_sms = (int) results[0].getLong("amount_sms");
        int amount_data = (int) results[0].getLong("amount_data");
        int price = (int) results[0].getLong("price");
        int period = (int) results[0].getLong("period"); // Retrieve period

        // Set sdate to the current date
        Date sdate = new Date(System.currentTimeMillis()); // current date

        // Calculate edate by adding period days to sdate
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdate);
        cal.add(Calendar.DAY_OF_MONTH, period);
        Date edate = new Date(cal.getTimeInMillis()); // edate with period added

        // Insert the new balance record
        voltQueueSQL(insertBalanceStmt,
                maxBalanceId + 1, // new balance_id
                package_id,
                cust_id,
                amount_minutes,
                amount_sms,
                amount_data,
                sdate,
                edate,
                price,
                bal_lvl_money
        );
        voltExecuteSQL();

        return maxBalanceId + 1;
    }
}

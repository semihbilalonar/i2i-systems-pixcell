import org.voltdb.*;

public class InsertCustomer extends VoltProcedure {

    public final SQLStmt getMaxCustIdStmt = new SQLStmt(
            "SELECT COALESCE(MAX(CUST_ID), 0) FROM customer;"
    );

    public final SQLStmt insertCustomerStmt = new SQLStmt(
            "INSERT INTO customer (CUST_ID, MSISDN, NAME, SURNAME, EMAIL, PASSWORD, STR_DATE, STATUS, SECURITY_KEY) " +
                    "VALUES (?, ?, ?, ?, ?, ?, NOW(), ?, ?);"
    );

    public long run(long msisdn, String name, String surname, String email, String password, String status, String securityKey) throws VoltAbortException {

        // Get the current maximum CUST_ID
        voltQueueSQL(getMaxCustIdStmt);
        VoltTable[] results = voltExecuteSQL();
        long maxCustId = results[0].asScalarLong();

        // Insert the new customer
        voltQueueSQL(insertCustomerStmt, maxCustId + 1, msisdn, name, surname, email, password, status, securityKey);
        voltExecuteSQL();

        return maxCustId + 1;
    }
}

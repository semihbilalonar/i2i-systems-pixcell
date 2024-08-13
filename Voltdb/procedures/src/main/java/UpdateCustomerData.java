import org.voltdb.*;

public class UpdateCustomerData extends VoltProcedure {
    public final SQLStmt getCustomerIdStmt = new SQLStmt(
            "SELECT CUST_ID " +
                    "FROM customer " +
                    "WHERE MSISDN = ?;"
    );

    public final SQLStmt updateDataStmt = new SQLStmt(
            "UPDATE balance " +
                    "SET BAL_LVL_DATA = ? " +
                    "WHERE CUST_ID = ?;"
    );

    public long run(int amount_data, long MSISDN) throws VoltAbortException {
        voltQueueSQL(getCustomerIdStmt, MSISDN);
        VoltTable[] results = voltExecuteSQL();

        if (results.length == 0 || results[0].getRowCount() == 0) {
            throw new VoltAbortException("No customer found with MSISDN: " + MSISDN);
        }

        VoltTable resultTable = results[0];
        int custId = (int) resultTable.fetchRow(0).getLong("CUST_ID");

        voltQueueSQL(updateDataStmt, amount_data, custId);
        voltExecuteSQL();

        return 0;
    }
}

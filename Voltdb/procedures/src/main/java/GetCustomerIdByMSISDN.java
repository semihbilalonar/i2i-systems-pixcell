import org.voltdb.*;

public class GetCustomerIdByMSISDN extends VoltProcedure {

    public final SQLStmt getCustomerIdStmt = new SQLStmt(
            "SELECT cust_id FROM customer WHERE msisdn = ?;"
    );

    public long run(long msisdn) throws VoltAbortException {
        voltQueueSQL(getCustomerIdStmt, msisdn);
        VoltTable[] results = voltExecuteSQL();
        if (results[0].getRowCount() == 0) {
            return -1; // or some other indication that no record was found
        }
        results[0].advanceRow();
        return results[0].getLong("cust_id");
    }
}

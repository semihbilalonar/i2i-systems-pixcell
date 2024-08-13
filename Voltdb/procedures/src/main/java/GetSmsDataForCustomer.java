import org.voltdb.*;

public class GetSmsDataForCustomer extends VoltProcedure {
    public final SQLStmt selectSmsDataStmt = new SQLStmt(
            "SELECT BAL_LVL_SMS " +
                    "FROM balance b " +
                    "JOIN customer c ON b.CUST_ID = c.CUST_ID " +
                    "WHERE c.MSISDN = ?;"
    );

    public VoltTable[] run(long MSISDN) throws VoltAbortException {
        voltQueueSQL(selectSmsDataStmt, MSISDN);
        return voltExecuteSQL();
    }
}

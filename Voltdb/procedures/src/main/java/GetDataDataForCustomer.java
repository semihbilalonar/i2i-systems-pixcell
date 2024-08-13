import org.voltdb.*;

public class GetDataDataForCustomer extends VoltProcedure {
    public final SQLStmt selectDataDataStmt = new SQLStmt(
            "SELECT BAL_LVL_DATA " +
                    "FROM balance b " +
                    "JOIN customer c ON b.CUST_ID = c.CUST_ID " +
                    "WHERE c.MSISDN = ?;"
    );

    public VoltTable[] run(long MSISDN) throws VoltAbortException {
        voltQueueSQL(selectDataDataStmt, MSISDN);
        return voltExecuteSQL();
    }
}

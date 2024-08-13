import org.voltdb.*;

public class GetMinutesDataForCustomer extends VoltProcedure {
    public final SQLStmt selectMinutesDataStmt = new SQLStmt(
            "SELECT BAL_LVL_MINUTES " +
                    "FROM balance b " +
                    "JOIN customer c ON b.CUST_ID = c.CUST_ID " +
                    "WHERE c.MSISDN = ?;"
    );

    public VoltTable[] run(long MSISDN) throws VoltAbortException {
        voltQueueSQL(selectMinutesDataStmt, MSISDN);
        return voltExecuteSQL();
    }
}

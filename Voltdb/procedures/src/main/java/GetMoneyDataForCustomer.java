import org.voltdb.*;

public class GetMoneyDataForCustomer extends VoltProcedure {
    public final SQLStmt selectMoneyDataStmt = new SQLStmt(
            "SELECT BAL_LVL_MONEY " +
                    "FROM balance b " +
                    "JOIN customer c ON b.CUST_ID = c.CUST_ID " +
                    "WHERE c.MSISDN = ?;"
    );

    public VoltTable[] run(long MSISDN) throws VoltAbortException {
        voltQueueSQL(selectMoneyDataStmt, MSISDN);
        return voltExecuteSQL();
    }
}

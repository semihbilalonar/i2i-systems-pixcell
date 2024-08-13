import org.voltdb.*;

public class GetPackageDataForCustomer extends VoltProcedure {
    public final SQLStmt selectPackageDataStmt = new SQLStmt(
            "SELECT p.* " +
                    "FROM package p " +
                    "JOIN customer c ON p.CUST_ID = c.CUST_ID " +
                    "WHERE c.MSISDN = ?;"
    );

    public VoltTable[] run(long MSISDN) throws VoltAbortException {
        voltQueueSQL(selectPackageDataStmt, MSISDN);
        return voltExecuteSQL();
    }
}

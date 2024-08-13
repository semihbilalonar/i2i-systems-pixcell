import org.voltdb.*;

public class GetCustomerInfo extends VoltProcedure {
    public final SQLStmt selectDataDataStmt = new SQLStmt(
            "SELECT * " +
                    "FROM customer c " +
                    "WHERE c.MSISDN = ?;"
    );

    public VoltTable[] run(long MSISDN) throws VoltAbortException {
        voltQueueSQL(selectDataDataStmt, MSISDN);
        return voltExecuteSQL();
    }
}
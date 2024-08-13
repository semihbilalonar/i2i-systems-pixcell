import org.voltdb.*;

public class GetPackageInfo extends VoltProcedure {
    public final SQLStmt selectDataDataStmt = new SQLStmt(
            "SELECT * " +
                    "FROM package p " +
                    "WHERE p.PACKAGE_ID = ?;"
    );

    public VoltTable[] run(int packageId) throws VoltAbortException {
        voltQueueSQL(selectDataDataStmt, packageId);
        return voltExecuteSQL();
    }
}
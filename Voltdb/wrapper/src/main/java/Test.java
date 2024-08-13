import com.i2i.intern.pixcell.VoltDbWrapper;

public class Test {
    public static void main(String[] args) {
        var wrapper = new VoltDbWrapper();
        wrapper.setCustomerBalanceByPackageId(12345678901L, 2);

    }
}

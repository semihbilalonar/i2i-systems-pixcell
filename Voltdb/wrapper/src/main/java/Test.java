import com.i2i.intern.pixcell.VoltDbWrapper;

import java.io.Console;

public class Test {
    public static void main(String[] args) {
        VoltDbWrapper wrapper = new VoltDbWrapper();

        var list = wrapper.getPackageIds();

        for (var id : list) {
            System.out.println(id);
        }


    }
}

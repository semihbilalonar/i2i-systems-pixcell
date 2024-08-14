import com.i2i.intern.pixcell.VoltDbWrapper;

public class Test {
    public static void main(String[] args) {
        var wrapper = new VoltDbWrapper();
        var internet = wrapper.getPackageInternet(1);
        var sms = wrapper.getPackageSms(1);
        var minutes = wrapper.getPackageMinutes(1);
        var price = wrapper.getPackagePrıce(1);
        var name = wrapper.getPackageName(1);

        System.out.println(internet);
        System.out.println(sms);
        System.out.println(minutes);
        System.out.println(price);
        System.out.println(name);
    }
}

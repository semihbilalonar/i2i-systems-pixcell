package com.i2i.intern.pixcell.data_model;

public class PackageInfoResponse {

    private int package_id;
    private String package_name;

    public PackageInfoResponse(int package_id, String package_name) {
        this.package_id = package_id;
        this.package_name = package_name;
    }

    public int getPackage_id() {
        return package_id;
    }

    public void setPackage_id(int package_id) {
        this.package_id = package_id;
    }

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }
}

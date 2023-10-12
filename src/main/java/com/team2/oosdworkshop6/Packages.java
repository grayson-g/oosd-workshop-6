package com.team2.oosdworkshop6;

public class Packages {

    private int PackageId;
    private String PkgName;
    private String PkgStartDate;
    private String PkgEndDate;
    private String PkgDesc;
    private String PkgBasePrice;
    private String PkgAgencyCommission;

    public Packages(int packageId, String pkgName, String pkgStartDate, String pkgEndDate,
                    String pkgDesc, String pkgBasePrice, String pkgAgencyCommission) {
        PackageId = packageId;
        PkgName = pkgName;
        PkgStartDate = pkgStartDate;
        PkgEndDate = pkgEndDate;
        PkgDesc = pkgDesc;
        PkgBasePrice = pkgBasePrice;
        PkgAgencyCommission = pkgAgencyCommission;
    }

    public int getPackageId() {
        return PackageId;
    }

    public void setPackageId(int packageId) {
        PackageId = packageId;
    }

    public String getPkgName() {
        return PkgName;
    }

    public void setPkgName(String pkgName) {
        PkgName = pkgName;
    }

    public String getPkgStartDate() {
        return PkgStartDate;
    }

    public void setPkgStartDate(String pkgStartDate) {
        PkgStartDate = pkgStartDate;
    }

    public String getPkgEndDate() {
        return PkgEndDate;
    }

    public void setPkgEndDate(String pkgEndDate) {
        PkgEndDate = pkgEndDate;
    }

    public String getPkgDesc() {
        return PkgDesc;
    }

    public void setPkgDesc(String pkgDesc) {
        PkgDesc = pkgDesc;
    }

    public String getPkgBasePrice() {
        return PkgBasePrice;
    }

    public void setPkgBasePrice(String pkgBasePrice) {
        PkgBasePrice = pkgBasePrice;
    }

    public String getPkgAgencyCommission() {
        return PkgAgencyCommission;
    }

    public void setPkgAgencyCommission(String pkgAgencyCommission) {
        PkgAgencyCommission = pkgAgencyCommission;
    }



}

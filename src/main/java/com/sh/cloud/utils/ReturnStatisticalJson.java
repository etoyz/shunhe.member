package com.sh.cloud.utils;


/*
*
* User Coupon CouponCheck 相关，用于CouponStatisticalController、BalanceStatisticalController。
*
* */


public class ReturnStatisticalJson {
    private String customername;
    private String memberNumber;
    private String vin;
    private String platenumber;
    private String couponName;
    private int rechargeMoney;
    private int usageMoney;
    private int remainingMoney;
    private int rechargeTimes;
    private int usageTimes;
    private int remainingTimes;

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getMemberNumber() {
        return memberNumber;
    }

    public void setMemberNumber(String memberNumber) {
        this.memberNumber = memberNumber;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getPlatenumber() {
        return platenumber;
    }

    public void setPlatenumber(String platenumber) {
        this.platenumber = platenumber;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public int getRechargeMoney() {
        return rechargeMoney;
    }

    public void setRechargeMoney(int rechargeMoney) {
        this.rechargeMoney = rechargeMoney;
    }

    public int getUsageMoney() {
        return usageMoney;
    }

    public void setUsageMoney(int usageMoney) {
        this.usageMoney = usageMoney;
    }

    public int getRemainingMoney() {
        return remainingMoney;
    }

    public void setRemainingMoney(int remainingMoney) {
        this.remainingMoney = remainingMoney;
    }

    public int getRechargeTimes() {
        return rechargeTimes;
    }

    public void setRechargeTimes(int rechargeTimes) {
        this.rechargeTimes = rechargeTimes;
    }

    public int getUsageTimes() {
        return usageTimes;
    }

    public void setUsageTimes(int usageTimes) {
        this.usageTimes = usageTimes;
    }

    public int getRemainingTimes() {
        return remainingTimes;
    }

    public void setRemainingTimes(int remainingTimes) {
        this.remainingTimes = remainingTimes;
    }


}

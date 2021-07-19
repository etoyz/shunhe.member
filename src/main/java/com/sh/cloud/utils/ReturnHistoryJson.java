package com.sh.cloud.utils;


/*
 *
 * 用于UseCardHistoryController、CancelConsumeHistoryController。
 *
 * */


public class ReturnHistoryJson {
    private String time; // 消费日期
    private String groupId; // 流水号
    private String customername;
    private String memberNumber;
    private String vin;
    private String platenumber;
    private String consumeTypeName;
    private String consumeProjectName;
    private String practicalProjectName;
    private String payStyleName; // 支付方式
    private String timeCost;
    private String matCost;
    private String totalMoney;
    private String ticketID; // 工单号
    private String mileage; // 行驶里程
    private String count; // 卡券的数量
    private String invoiceID; // 发票号
    private String invoiceNum; // 开票金额
    private float costPrice; // 成本价
    private String remark; // 项目备注

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

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

    public String getConsumeTypeName() {
        return consumeTypeName;
    }

    public void setConsumeTypeName(String consumeTypeName) {
        this.consumeTypeName = consumeTypeName;
    }

    public String getConsumeProjectName() {
        return consumeProjectName;
    }

    public void setConsumeProjectName(String consumeProjectName) {
        this.consumeProjectName = consumeProjectName;
    }

    public String getPracticalProjectName() {
        return practicalProjectName;
    }

    public void setPracticalProjectName(String practicalProjectName) {
        this.practicalProjectName = practicalProjectName;
    }

    public String getPayStyleName() {
        return payStyleName;
    }

    public void setPayStyleName(String payStyleName) {
        this.payStyleName = payStyleName;
    }

    public String getTimeCost() {
        return timeCost;
    }

    public void setTimeCost(String timeCost) {
        this.timeCost = timeCost;
    }

    public String getMatCost() {
        return matCost;
    }

    public void setMatCost(String matCost) {
        this.matCost = matCost;
    }

    public String getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getTicketID() {
        return ticketID;
    }

    public void setTicketID(String ticketID) {
        this.ticketID = ticketID;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(String invoiceID) {
        this.invoiceID = invoiceID;
    }

    public String getInvoiceNum() {
        return invoiceNum;
    }

    public void setInvoiceNum(String invoiceNum) {
        this.invoiceNum = invoiceNum;
    }

    public float getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(float costPrice) {
        this.costPrice = costPrice;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

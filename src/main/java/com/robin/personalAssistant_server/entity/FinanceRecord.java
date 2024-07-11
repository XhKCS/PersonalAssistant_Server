package com.robin.personalAssistant_server.entity;

import com.robin.personalAssistant_server.util.DateUtil;

public class FinanceRecord {
    private int financeId;
    private int financeType; // 1:收入  2:支出
    private int amount; //金额，整数
    private String remark; //备注
    private String createTime; //创建日期

    public FinanceRecord() {}

    public FinanceRecord(int financeId, int financeType, int amount, String remark) {
        this.financeId = financeId;
        this.financeType = financeType;
        this.amount = amount;
        this.remark = remark;
        this.createTime = DateUtil.getCurrentDateStr();
    }

    @Override
    public String toString() {
        return "FinanceRecord{" +
                "financeId=" + financeId +
                ", financeType=" + financeType +
                ", amount=" + amount +
                ", remark='" + remark + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }

    public int getFinanceId() {
        return financeId;
    }

    public void setFinanceId(int financeId) {
        this.financeId = financeId;
    }

    public int getFinanceType() {
        return financeType;
    }

    public void setFinanceType(int financeType) {
        this.financeType = financeType;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}

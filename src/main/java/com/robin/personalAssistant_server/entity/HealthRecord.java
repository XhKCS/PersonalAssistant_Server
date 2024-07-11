package com.robin.personalAssistant_server.entity;

import com.robin.personalAssistant_server.util.DateUtil;

public class HealthRecord {
    private int healthId;
    private double height; //单位：米
    private double weight; //单位：千克
    private double BMI;
    private String conclusion; //结论：消瘦 / 正常 / 超重
    private String createTime; //创建时间

    public HealthRecord() {}

//    public HealthRecord(int healthId, double height, double weight, double BMI, String conclusion, String createTime) {
//        this.healthId = healthId;
//        this.height = height;
//        this.weight = weight;
//        this.BMI = BMI;
//        this.conclusion = conclusion;
//        this.createTime = createTime;
//    }

    public HealthRecord(int healthId, double height, double weight) {
        this.healthId = healthId;
        this.height = height;
        this.weight = weight;
        this.createTime = DateUtil.getCurrentDateStr();
        this.updateBMI();
    }

    public void updateBMI() {
        this.BMI = weight / (height*height); //计算BMI
        if (this.BMI < 18.5) {
            this.conclusion = "偏瘦";
        }
        else if (this.BMI > 24) {
            this.conclusion = "超重";
        }
        else {
            this.conclusion = "正常";
        }
    }

    @Override
    public String toString() {
        return "HealthRecord{" +
                "healthId=" + healthId +
                ", height=" + height +
                ", weight=" + weight +
                ", BMI=" + BMI +
                ", conclusion='" + conclusion + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }

    public int getHealthId() {
        return healthId;
    }

    public void setHealthId(int healthId) {
        this.healthId = healthId;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
        this.updateBMI();
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
        this.updateBMI();
    }

    public double getBMI() {
        return BMI;
    }

    public void setBMI(double BMI) {
        this.BMI = BMI;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}

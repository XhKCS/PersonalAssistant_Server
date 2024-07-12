package com.robin.personalAssistant_server.entity;

import com.robin.personalAssistant_server.util.DateUtil;

// 联系人
public class Contact {
    private int contactId;
    private String contactName; //思考了一下，联系人应该可以重名（一个人可以有多个电话号码），但电话号码不能重复
    private String phoneNumber; //需要验证有效性（是否是11位整数；是否不重复）
    private String createTime; //创建时间

    public Contact() {}

    public Contact(int contactId, String contactName, String phoneNumber) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.phoneNumber = phoneNumber;
        this.createTime = DateUtil.getCurrentDateStr();
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}

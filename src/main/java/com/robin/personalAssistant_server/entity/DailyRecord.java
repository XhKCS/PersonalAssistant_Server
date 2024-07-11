package com.robin.personalAssistant_server.entity;

import com.robin.personalAssistant_server.util.DateUtil;

//备忘录
public class DailyRecord {
    private int dailyId;
    private String title;
    private String content;
    private String createTime; //创建时间，后端自动生成

    public DailyRecord() {}

    public DailyRecord(int dailyId, String title, String content) {
        this.dailyId = dailyId;
        this.title = title;
        this.content = content;
        this.createTime = DateUtil.getCurrentDateStr();
    }

    @Override
    public String toString() {
        return "DailyRecord{" +
                "dailyId=" + dailyId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }

    public int getDailyId() {
        return dailyId;
    }

    public void setDailyId(int dailyId) {
        this.dailyId = dailyId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}

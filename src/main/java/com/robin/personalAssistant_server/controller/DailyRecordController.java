package com.robin.personalAssistant_server.controller;

import com.robin.personalAssistant_server.biz.DailyRecordBiz;
import com.robin.personalAssistant_server.entity.DailyRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.robin.personalAssistant_server.util.*;

@RestController
@RequestMapping("/daily")
public class DailyRecordController {
    @Autowired
    private DailyRecordBiz biz;

    public void setBiz(DailyRecordBiz biz) {
        this.biz = biz;
    }

    @RequestMapping("/listAll")
    public Map<String, Object> listAll() {
        Map<String, Object> response = new HashMap<>();
        List<DailyRecord> dailyRecordList = biz.getDailiesList();
        response.put("dailyRecordList", dailyRecordList);
        response.put("isOk", true);
        return response;
    }

    @RequestMapping("/getById")
    public Map<String, Object> getById(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        String dailyId_str = (String)request.get("dailyId");
        if (!JudgeUtil.isInteger(dailyId_str)) {
            response.put("isOk", false);
            response.put("msg", "输入格式错误，请输入正整数");
            return response;
        }
        int dailyId = Integer.parseInt(dailyId_str);
        DailyRecord dailyRecord = biz.getDailyById(dailyId);
        if (dailyRecord != null) {
            response.put("isOk", true);
            response.put("dailyRecord", dailyRecord);
            response.put("msg", "查询成功");
        } else {
            response.put("isOk", false);
            response.put("dailyRecord", null);
            response.put("msg", "查询失败，请检查dailyId");
        }
        return response;
    }

    @RequestMapping("/add")
    public Map<String, Object> addDailyRecord(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        String title = request.get("title");
        String content = request.get("content");
        if (title.isEmpty() || content.isEmpty()) {
            response.put("isOk", false);
            response.put("msg", "备忘标题和内容不能为空！添加失败");
            return response;
        }
//        String createTime = DateUtil.getCurrentDateStr();
        DailyRecord newDaily = new DailyRecord(0, title, content);
        boolean isOk = biz.addDaily(newDaily);
        if (isOk) {
            response.put("isOk", true);
            response.put("msg", "添加成功");
        } else {
            response.put("isOk", false);
            response.put("msg", "添加失败");
        }
        return response;
    }

    @RequestMapping("/deleteById")
    public Map<String, Object> deleteById(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        String dailyId_str = request.get("dailyId");
        if (!JudgeUtil.isInteger(dailyId_str)) {
            response.put("isOk", false);
            response.put("msg", "输入格式错误，请输入正整数");
            return response;
        }
        int dailyId = Integer.parseInt(dailyId_str);
        boolean isOk = biz.removeDailyById(dailyId);
        if (isOk) {
            response.put("isOk", true);
            response.put("msg", "删除成功");
        } else {
            response.put("isOk", false);
            response.put("msg", "删除失败，请检查dailyId");
        }
        return response;
    }

    @RequestMapping("/update")
    public Map<String, Object> updateDailyRecord(@RequestBody DailyRecord updatedDaily) {
        Map<String, Object> response = new HashMap<>();
        if (updatedDaily.getTitle().isEmpty() || updatedDaily.getContent().isEmpty()) {
            response.put("isOk", false);
            response.put("msg", "不能修改为空字符串！修改失败");
            return response;
        }
        boolean isOk = biz.updateDaily(updatedDaily);
        if (isOk) {
            response.put("isOk", true);
            response.put("msg", "修改成功");
        } else {
            response.put("isOk", false);
            response.put("msg", "修改失败");
        }
        return response;
    }

}

package com.robin.personalAssistant_server.controller;

import com.robin.personalAssistant_server.biz.HealthRecordBiz;
import com.robin.personalAssistant_server.entity.DailyRecord;
import com.robin.personalAssistant_server.entity.HealthRecord;
import com.robin.personalAssistant_server.util.DateUtil;
import com.robin.personalAssistant_server.util.JudgeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/health")
public class HealthRecordController {
    @Autowired
    private HealthRecordBiz biz;

    public void setBiz(HealthRecordBiz biz) {
        this.biz = biz;
    }

    @RequestMapping("/listAll")
    public Map<String, Object> listAll() {
        Map<String, Object> response = new HashMap<>();
        List<HealthRecord> healthRecordList = biz.getHealthRecordList();
        response.put("healthRecordList", healthRecordList);
        response.put("isOk", true);
        return response;
    }

    @RequestMapping("/getById")
    public Map<String, Object> getById(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        String healthId_str = (String)request.get("healthId");
        if (!JudgeUtil.isInteger(healthId_str)) {
            response.put("isOk", false);
            response.put("msg", "输入格式错误，请输入正整数");
            return response;
        }
        int healthId = Integer.parseInt(healthId_str);
        HealthRecord healthRecord = biz.getHealthRecordById(healthId);
        if (healthRecord != null) {
            response.put("isOk", true);
            response.put("healthRecord", healthRecord);
            response.put("msg", "查询成功");
        } else {
            response.put("isOk", false);
            response.put("healthRecord", null);
            response.put("msg", "查询失败，请检查healthId");
        }
        return response;
    }

    @RequestMapping("/add")
    public Map<String, Object> addHealthRecord(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        String height_str = request.get("height");
        String weight_str = request.get("weight");
        System.out.println("前端输入的height: "+height_str);
        System.out.println("前端输入的weight: "+weight_str);
        if (!JudgeUtil.isDouble(height_str) || !JudgeUtil.isDouble(weight_str)) {
            response.put("isOk", false);
            response.put("msg", "输入格式错误，请输入合法数字");
            return response;
        }
        double height = Double.parseDouble(height_str);
        double weight = Double.parseDouble(weight_str);
        if (height <= 0 || weight <=0) {
            response.put("isOk", false);
            response.put("msg", "身高和体重必须为正数");
            return response;
        }
        if (height < 0.2 || height > 3 || weight < 2 || weight > 300) {
            response.put("isOk", false);
            response.put("msg", "身高或体重数据不合理");
            return response;
        }
//        String createTime = DateUtil.getCurrentDateStr();
        HealthRecord healthRecord = new HealthRecord(0, height, weight);
        boolean isOk = biz.addHealthRecord(healthRecord);
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
        String healthId_str = request.get("healthId");
        if (!JudgeUtil.isInteger(healthId_str)) {
            response.put("isOk", false);
            response.put("msg", "输入格式错误，请输入正整数");
            return response;
        }
        int healthId = Integer.parseInt(healthId_str);
        boolean isOk = biz.removeHealthRecordById(healthId);
        if (isOk) {
            response.put("isOk", true);
            response.put("msg", "删除成功");
        } else {
            response.put("isOk", false);
            response.put("msg", "删除失败，请检查healthId");
        }
        return response;
    }

    @RequestMapping("/update")
    public Map<String, Object> updateHealthRecord(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        String healthId_str = (String)request.get("healthId");
        String height_str = request.get("height");
        String weight_str = request.get("weight");
        if (!JudgeUtil.isInteger(healthId_str) || !JudgeUtil.isDouble(height_str) || !JudgeUtil.isDouble(weight_str)) {
            response.put("isOk", false);
            response.put("msg", "输入格式错误，请输入合法数字");
            return response;
        }
        int healthId = Integer.parseInt(healthId_str);
        HealthRecord healthRecord = biz.getHealthRecordById(healthId);
        if (healthRecord == null) {
            response.put("isOk", false);
            response.put("msg", "未找到该Id的健康记录");
            return response;
        }
        double height = Double.parseDouble(height_str);
        double weight = Double.parseDouble(weight_str);
        if (height < 0.2 || height > 3 || weight < 2 || weight > 300) {
            response.put("isOk", false);
            response.put("msg", "身高或体重数据不合理");
            return response;
        }
        healthRecord.setHeight(height);
        healthRecord.setWeight(weight);
        healthRecord.updateBMI();
        boolean isOk = biz.updateHealthRecord(healthRecord);
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

package com.robin.personalAssistant_server.controller;

import com.robin.personalAssistant_server.biz.FinanceRecordBiz;
import com.robin.personalAssistant_server.entity.FinanceRecord;
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
@RequestMapping("/finance")
public class FinanceRecordController {
    @Autowired
    private FinanceRecordBiz biz;

    public void setBiz(FinanceRecordBiz biz) {
        this.biz = biz;
    }

    @RequestMapping("/listAll")
    public Map<String, Object> listAll() {
        Map<String, Object> response = new HashMap<>();
        List<FinanceRecord> financeRecordList = biz.getFinanceRecordList();
        response.put("financeRecordList", financeRecordList);
        response.put("isOk", true);
        return response;
    }

    @RequestMapping("/getById")
    public Map<String, Object> getById(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        String financeId_str = (String)request.get("financeId");
        if (!JudgeUtil.isInteger(financeId_str)) {
            response.put("isOk", false);
            response.put("msg", "输入格式错误，请输入正整数");
            return response;
        }
        int financeId = Integer.parseInt(financeId_str);
        FinanceRecord financeRecord = biz.getFinanceRecordById(financeId);
        if (financeRecord != null) {
            response.put("isOk", true);
            response.put("financeRecord", financeRecord);
            response.put("msg", "查询成功");
        } else {
            response.put("isOk", false);
            response.put("financeRecord", null);
            response.put("msg", "查询失败，请检查financeId");
        }
        return response;
    }

    @RequestMapping("/add")
    public Map<String, Object> addFinance(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        System.out.println("/finance/add -----");
        String financeType_str = request.get("financeType");
        String amount_str = request.get("amount");
        System.out.println("前端输入的financeType: "+financeType_str);
        System.out.println("前端输入的amount: "+amount_str);
        if (!JudgeUtil.isInteger(financeType_str) || !JudgeUtil.isInteger(amount_str)) {
            response.put("isOk", false);
            response.put("msg", "输入格式错误，financeType和amount必须是合法正整数");
            return response;
        }
        int financeType = Integer.parseInt(financeType_str);
        int amount = Integer.parseInt(amount_str);
        if (financeType!=1 && financeType!=2) {
            response.put("isOk", false);
            response.put("msg", "financeType只能为1或2（表示收入或支出）");
            return response;
        }
        if (amount <= 0) {
            response.put("isOk", false);
            response.put("msg", "收入或支出的金额只能为正整数");
            return response;
        }
        String remark = request.get("remark");
//        String createTime = DateUtil.getCurrentDateStr();
        FinanceRecord financeRecord = new FinanceRecord(0, financeType, amount, remark);
        boolean isOk = biz.addFinanceRecord(financeRecord);
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
        String financeId_str = request.get("financeId");
        if (!JudgeUtil.isInteger(financeId_str)) {
            response.put("isOk", false);
            response.put("msg", "输入格式错误，请输入正整数");
            return response;
        }
        int financeId = Integer.parseInt(financeId_str);
        boolean isOk = biz.removeFinanceRecordById(financeId);
        if (isOk) {
            response.put("isOk", true);
            response.put("msg", "删除成功");
        } else {
            response.put("isOk", false);
            response.put("msg", "删除失败，请检查financeId");
        }
        return response;
    }

    @RequestMapping("/update")
    public Map<String, Object> updateFinanceRecord(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        System.out.println("/finance/update -----");
        String financeId_str = (String)request.get("financeId");
        String financeType_str = request.get("financeType");
        String amount_str = request.get("amount");
        System.out.println("前端输入的financeId: "+financeId_str);
        System.out.println("前端输入的financeType: "+financeType_str);
        System.out.println("前端输入的amount: "+amount_str);
        if (!JudgeUtil.isInteger(financeId_str) || !JudgeUtil.isInteger(financeType_str) || !JudgeUtil.isInteger(amount_str)) {
            response.put("isOk", false);
            response.put("msg", "输入格式错误，financeId, financeType, amount必须是合法正整数");
            return response;
        }
        int financeType = Integer.parseInt(financeType_str);
        if (financeType!=1 && financeType!=2) {
            response.put("isOk", false);
            response.put("msg", "financeType只能为1或2（表示收入或支出）");
            return response;
        }
        int amount = Integer.parseInt(amount_str);
        if (amount <= 0) {
            response.put("isOk", false);
            response.put("msg", "收入或支出的金额只能为正整数");
            return response;
        }
        int financeId = Integer.parseInt(financeId_str);
        FinanceRecord financeRecord = biz.getFinanceRecordById(financeId);
        if (financeRecord == null) {
            response.put("isOk", false);
            response.put("msg", "未找到该Id的收支记录");
            return response;
        }
        financeRecord.setFinanceType(financeType);
        financeRecord.setAmount(amount);
        String remark = request.get("remark");
        financeRecord.setRemark(remark);
        boolean isOk = biz.updateFinanceRecord(financeRecord);
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

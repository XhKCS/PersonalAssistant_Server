package com.robin.personalAssistant_server.controller;

import com.robin.personalAssistant_server.biz.ContactBiz;
import com.robin.personalAssistant_server.entity.Contact;
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
@RequestMapping("/contact")
public class ContactController {
    @Autowired
    private ContactBiz biz;

    public void setBiz(ContactBiz biz) {
        this.biz = biz;
    }

    @RequestMapping("/listAll")
    public Map<String, Object> listAll() {
        Map<String, Object> response = new HashMap<>();
        List<Contact> contactList = biz.getContactList();
        response.put("contactList", contactList);
        response.put("isOk", true);
        return response;
    }

    @RequestMapping("/listByName")
    public Map<String, Object> getByName(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        String contactName = request.get("contactName");
        List<Contact> contactList = biz.getContactsByName(contactName);
        if (contactList != null) {
            response.put("isOk", true);
            response.put("contactList", contactList);
            response.put("msg", "查询成功");
        } else {
            response.put("isOk", false);
            response.put("contactList", null);
            response.put("msg", "查询失败，请检查contactName");
        }
        return response;
    }

    @RequestMapping("/getById")
    public Map<String, Object> getById(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        String contactId_str = (String)request.get("contactId");
        if (!JudgeUtil.isInteger(contactId_str)) {
            response.put("isOk", false);
            response.put("msg", "输入格式错误，请输入正整数");
            return response;
        }
        int contactId = Integer.parseInt(contactId_str);
        Contact contact = biz.getContactById(contactId);
        if (contact != null) {
            response.put("isOk", true);
            response.put("contact", contact);
            response.put("msg", "查询成功");
        } else {
            response.put("isOk", false);
            response.put("contact", null);
            response.put("msg", "查询失败，请检查contactId");
        }
        return response;
    }

    @RequestMapping("/getByPhoneNumber")
    public Map<String, Object> getByPhoneNumber(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        String phoneNumber = request.get("phoneNumber");
        Contact contact = biz.getContactByPhoneNumber(phoneNumber);
        if (contact != null) {
            response.put("isOk", true);
            response.put("contact", contact);
            response.put("msg", "查询成功");
        } else {
            response.put("isOk", false);
            response.put("contact", null);
            response.put("msg", "查询失败，请检查电话号码");
        }
        return response;
    }

    @RequestMapping("/add")
    public Map<String, Object> addContact(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        String contactName = request.get("contactName");
        String phoneNumber = request.get("phoneNumber");
        if (contactName.isEmpty() || phoneNumber.isEmpty()) {
            response.put("isOk", false);
            response.put("msg", "联系人姓名和电话号码不能为空！添加失败");
            return response;
        }
        if (!JudgeUtil.isInteger(phoneNumber)) {
            response.put("isOk", false);
            response.put("msg", "格式有误，电话号码只能包含数字");
            return response;
        }
        if (phoneNumber.length() != 11) {
            response.put("isOk", false);
            response.put("msg", "格式有误，电话号码应为11位数字");
            return response;
        }
        Contact check = biz.getContactByPhoneNumber(phoneNumber);
        if (check != null) {
            response.put("isOk", false);
            response.put("msg", "已存在该手机号码的联系人，同一个号码不能重复添加！");
            return response;
        }
//        String createTime = DateUtil.getCurrentDateStr();
        Contact contact = new Contact(0, contactName, phoneNumber);
        boolean isOk = biz.addContact(contact);
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
        String contactId_str = request.get("contactId");
        if (!JudgeUtil.isInteger(contactId_str)) {
            response.put("isOk", false);
            response.put("msg", "输入格式错误，请输入正整数");
            return response;
        }
        int contactId = Integer.parseInt(contactId_str);
        boolean isOk = biz.removeContactById(contactId);
        if (isOk) {
            response.put("isOk", true);
            response.put("msg", "删除成功");
        } else {
            response.put("isOk", false);
            response.put("msg", "删除失败，请检查contactId");
        }
        return response;
    }

    @RequestMapping("/update")
    public Map<String, Object> updateContact(@RequestBody Contact contact) {
        Map<String, Object> response = new HashMap<>();
        if (contact.getContactName().isEmpty() || contact.getPhoneNumber().isEmpty()) {
            response.put("isOk", false);
            response.put("msg", "联系人姓名和电话不能为空！修改失败");
            return response;
        }
        if (!JudgeUtil.isInteger(contact.getPhoneNumber())) {
            response.put("isOk", false);
            response.put("msg", "格式有误，电话号码只能包含数字");
            return response;
        }
        if (contact.getPhoneNumber().length() != 11) {
            response.put("isOk", false);
            response.put("msg", "格式有误，电话号码应为11位数字");
            return response;
        }
        Contact check = biz.getContactByPhoneNumber(contact.getPhoneNumber());
        if (check != null && check.getContactId() != contact.getContactId()) {
            response.put("isOk", false);
            response.put("msg", "已存在该电话号码的其他联系人，请重新设置该联系人号码！");
            return response;
        }
        boolean isOk = biz.updateContact(contact);
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

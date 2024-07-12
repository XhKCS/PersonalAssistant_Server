package com.robin.personalAssistant_server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.robin.personalAssistant_server.biz.ContactBiz;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestBody;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class ContactControllerTest {

    private MockMvc mockMvc;
    private MockHttpSession session;

    @Autowired
    private ContactBiz contactBiz;

    private ContactController controller = new ContactController();

    public void setContactBiz(ContactBiz contactBiz) {
        this.contactBiz = contactBiz;
    }

    @BeforeEach
    void setUp() {
        session = new MockHttpSession();
        controller.setBiz(contactBiz);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        System.out.println("联系人管理模块测试开始---");
    }

    @AfterEach
    void tearDown() {
        System.out.println("联系人管理模块测试结束---");
    }

    @Test
    void listAll() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/contact/listAll")
                .accept(MediaType.APPLICATION_JSON_VALUE);
        // mockMvc.perform执行一个请求
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String jsonResponse = result.getResponse().getContentAsString();
        System.out.println(jsonResponse);
        // 使用 ObjectMapper 将 JSON 响应转换为 Map
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> responseMap = objectMapper.readValue(jsonResponse, Map.class);
        assertEquals(responseMap.get("isOk"), true);
        assertNotNull(responseMap.get("contactList"));
    }

    @Test
    void listByName() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        //正常的测试用例
        Map<String, String> request1 = new HashMap<>();
        request1.put("contactName", "小明");
        String jsonRequest1 = objectMapper.writeValueAsString(request1);
        System.out.println("jsonRequest1: "+jsonRequest1);
        MvcResult result1 = mockMvc.perform(MockMvcRequestBuilders
                .post("/contact/listByName")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(jsonRequest1)).andReturn();
        String jsonResponse1 = result1.getResponse().getContentAsString();
        System.out.println("jsonResponse1: "+jsonResponse1);
        //异常的测试用例
        Map<String, String> request2 = new HashMap<>();
        request2.put("contactName", "小A"); //contactName在数据库中不存在
        String jsonRequest2 = objectMapper.writeValueAsString(request2);
        System.out.println("jsonRequest2: "+jsonRequest2);
        MvcResult result2 = mockMvc.perform(MockMvcRequestBuilders
                .post("/contact/listByName")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(jsonRequest2)).andReturn();
        String jsonResponse2 = result2.getResponse().getContentAsString();
        System.out.println("jsonResponse2: "+jsonResponse2);

        //分别检查测试结果是否与预期一致
        Map<String, Object> responseMap1 = objectMapper.readValue(jsonResponse1, Map.class);
        assertEquals(responseMap1.get("isOk"), true);
        assertNotNull(responseMap1.get("contactList"));
        Map<String, Object> responseMap2 = objectMapper.readValue(jsonResponse2, Map.class);
        assertEquals(responseMap2.get("isOk"), false);
    }

    @Test
    void getById() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        //正常的测试用例
        Map<String, String> request1 = new HashMap<>();
        request1.put("contactId", String.valueOf(1));
        String jsonRequest1 = objectMapper.writeValueAsString(request1);
        System.out.println("jsonRequest1: "+jsonRequest1);
        MvcResult result1 = mockMvc.perform(MockMvcRequestBuilders
                .post("/contact/getById")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(jsonRequest1)).andReturn();
        String jsonResponse1 = result1.getResponse().getContentAsString();
        System.out.println("jsonResponse1: "+jsonResponse1);
        //异常的测试用例
        Map<String, String> request2 = new HashMap<>();
        request2.put("contactId", String.valueOf(1.5)); //contactId为小数
        String jsonRequest2 = objectMapper.writeValueAsString(request2);
        System.out.println("jsonRequest2: "+jsonRequest2);
        MvcResult result2 = mockMvc.perform(MockMvcRequestBuilders
                .post("/contact/getById")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(jsonRequest2)).andReturn();
        String jsonResponse2 = result2.getResponse().getContentAsString();
        System.out.println("jsonResponse2: "+jsonResponse2);

        Map<String, String> request3 = new HashMap<>();
        request3.put("contactId", "AAA"); //contactId为非数字
        String jsonRequest3 = objectMapper.writeValueAsString(request3);
        System.out.println("jsonRequest3: "+jsonRequest3);
        MvcResult result3 = mockMvc.perform(MockMvcRequestBuilders
                .post("/contact/getById")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(jsonRequest3)).andReturn();
        String jsonResponse3 = result3.getResponse().getContentAsString();
        System.out.println("jsonResponse3: "+jsonResponse3);

        //分别检查测试结果是否与预期一致
        Map<String, Object> responseMap1 = objectMapper.readValue(jsonResponse1, Map.class);
        assertEquals(responseMap1.get("isOk"), true);
        assertNotNull(responseMap1.get("contact"));
        Map<String, Object> responseMap2 = objectMapper.readValue(jsonResponse2, Map.class);
        assertEquals(responseMap2.get("isOk"), false);
        Map<String, Object> responseMap3 = objectMapper.readValue(jsonResponse3, Map.class);
        assertEquals(responseMap3.get("isOk"), false);
    }

    @Test
    void getByPhoneNumber() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        //正常的测试用例
        Map<String, String> request1 = new HashMap<>();
        request1.put("phoneNumber", "12345678900");
        String jsonRequest1 = objectMapper.writeValueAsString(request1);
        System.out.println("jsonRequest1: "+jsonRequest1);
        MvcResult result1 = mockMvc.perform(MockMvcRequestBuilders
                .post("/contact/getByPhoneNumber")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(jsonRequest1)).andReturn();
        String jsonResponse1 = result1.getResponse().getContentAsString();
        System.out.println("jsonResponse1: "+jsonResponse1);
        //异常的测试用例
        Map<String, String> request2 = new HashMap<>();
        request2.put("phoneNumber", "123456");  //手机号码不是11位
        String jsonRequest2 = objectMapper.writeValueAsString(request2);
        System.out.println("jsonRequest2: "+jsonRequest2);
        MvcResult result2 = mockMvc.perform(MockMvcRequestBuilders
                .post("/contact/getByPhoneNumber")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(jsonRequest2)).andReturn();
        String jsonResponse2 = result2.getResponse().getContentAsString();
        System.out.println("jsonResponse2: "+jsonResponse2);

        Map<String, String> request3 = new HashMap<>();
        request3.put("phoneNumber", "12345678..x");  //手机号码包含非数字
        String jsonRequest3 = objectMapper.writeValueAsString(request3);
        System.out.println("jsonRequest3: "+jsonRequest3);
        MvcResult result3 = mockMvc.perform(MockMvcRequestBuilders
                .post("/contact/getByPhoneNumber")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(jsonRequest3)).andReturn();
        String jsonResponse3 = result3.getResponse().getContentAsString();
        System.out.println("jsonResponse3: "+jsonResponse3);

        //分别检查测试结果是否与预期一致
        Map<String, Object> responseMap1 = objectMapper.readValue(jsonResponse1, Map.class);
        assertEquals(responseMap1.get("isOk"), true);
        assertNotNull(responseMap1.get("contact"));
        Map<String, Object> responseMap2 = objectMapper.readValue(jsonResponse2, Map.class);
        assertEquals(responseMap2.get("isOk"), false);
        Map<String, Object> responseMap3 = objectMapper.readValue(jsonResponse3, Map.class);
        assertEquals(responseMap3.get("isOk"), false);
    }

    @Test
    void addContact() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        //正常的测试用例
        Map<String, String> request1 = new HashMap<>();
        request1.put("contactName", "小智");
        request1.put("phoneNumber", "12345678903");
        String jsonRequest1 = objectMapper.writeValueAsString(request1);
        System.out.println("jsonRequest1: "+jsonRequest1);
        MvcResult result1 = mockMvc.perform(MockMvcRequestBuilders
                .post("/contact/add")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(jsonRequest1)).andReturn();
        String jsonResponse1 = result1.getResponse().getContentAsString();
        System.out.println("jsonResponse1: "+jsonResponse1);
        //异常的测试用例
        Map<String, String> request2 = new HashMap<>();
        request2.put("contactName", ""); //联系人姓名为空
        request2.put("phoneNumber", "12345678904");
        String jsonRequest2 = objectMapper.writeValueAsString(request2);
        System.out.println("jsonRequest2: "+jsonRequest2);
        MvcResult result2 = mockMvc.perform(MockMvcRequestBuilders
                .post("/contact/add")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(jsonRequest2)).andReturn();
        String jsonResponse2 = result2.getResponse().getContentAsString();
        System.out.println("jsonResponse2: "+jsonResponse2);

        Map<String, String> request3 = new HashMap<>();
        request3.put("contactName", "小A");
        request3.put("phoneNumber", "123456"); //手机号码不是11位数字
        String jsonRequest3 = objectMapper.writeValueAsString(request3);
        System.out.println("jsonRequest3: "+jsonRequest3);
        MvcResult result3 = mockMvc.perform(MockMvcRequestBuilders
                .post("/contact/add")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(jsonRequest3)).andReturn();
        String jsonResponse3 = result3.getResponse().getContentAsString();
        System.out.println("jsonResponse3: "+jsonResponse3);
        //分别检查测试结果是否与预期一致
        Map<String, Object> responseMap1 = objectMapper.readValue(jsonResponse1, Map.class);
        assertEquals(responseMap1.get("isOk"), true);
        Map<String, Object> responseMap2 = objectMapper.readValue(jsonResponse2, Map.class);
        assertEquals(responseMap2.get("isOk"), false);
        Map<String, Object> responseMap3 = objectMapper.readValue(jsonResponse3, Map.class);
        assertEquals(responseMap3.get("isOk"), false);
    }

    @Test
    void deleteById() {

    }

    @Test
    void updateContact() {
    }
}
package com.robin.personalAssistant_server.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.robin.personalAssistant_server.biz.FinanceRecordBiz;
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

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class FinanceRecordControllerTest {

    private MockMvc mockMvc;
    private MockHttpSession session;

    @Autowired
    private FinanceRecordBiz financeRecordBiz;

    private FinanceRecordController controller = new FinanceRecordController();

    public void setFinanceRecordBiz(FinanceRecordBiz financeRecordBiz) {
        this.financeRecordBiz = financeRecordBiz;
    }

    @BeforeEach
    void setUp() {
        session = new MockHttpSession();
        controller.setBiz(financeRecordBiz);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        System.out.println("收支管理模块测试开始---");
    }

    @AfterEach
    void tearDown() {
        System.out.println("收支管理模块测试结束---");
    }


    @Test
    void listAll() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/finance/listAll")
                .accept(MediaType.APPLICATION_JSON_VALUE);
        // mockMvc.perform执行一个请求
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String jsonResponse = result.getResponse().getContentAsString();
        System.out.println(jsonResponse);
        // 使用 ObjectMapper 将 JSON 响应转换为 Map
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> responseMap = objectMapper.readValue(jsonResponse, Map.class);
        assertEquals(responseMap.get("isOk"), true);
        assertNotNull(responseMap.get("financeRecordList"));
    }

    @Test
    void getById() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        //正常的测试用例
        Map<String, String> request1 = new HashMap<>();
        request1.put("financeId", String.valueOf(2));
        String jsonRequest1 = objectMapper.writeValueAsString(request1);
        System.out.println("jsonRequest1: "+jsonRequest1);
        MvcResult result1 = mockMvc.perform(MockMvcRequestBuilders
                .post("/finance/getById")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(jsonRequest1)).andReturn();
        String jsonResponse1 = result1.getResponse().getContentAsString();
        System.out.println("jsonResponse1: "+jsonResponse1);
        //异常的测试用例
        Map<String, String> request2 = new HashMap<>();
        request2.put("financeId", String.valueOf(-1)); //financeId为-1
        String jsonRequest2 = objectMapper.writeValueAsString(request2);
        System.out.println("jsonRequest2: "+jsonRequest2);
        MvcResult result2 = mockMvc.perform(MockMvcRequestBuilders
                .post("/finance/getById")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(jsonRequest2)).andReturn();
        String jsonResponse2 = result2.getResponse().getContentAsString();
        System.out.println("jsonResponse2: "+jsonResponse2);

        Map<String, String> request3 = new HashMap<>();
        request3.put("financeId", "AAA"); //financeId为非数字
        String jsonRequest3 = objectMapper.writeValueAsString(request3);
        System.out.println("jsonRequest3: "+jsonRequest3);
        MvcResult result3 = mockMvc.perform(MockMvcRequestBuilders
                .post("/finance/getById")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(jsonRequest3)).andReturn();
        String jsonResponse3 = result3.getResponse().getContentAsString();
        System.out.println("jsonResponse3: "+jsonResponse3);

        //分别检查测试结果是否与预期一致
        Map<String, Object> responseMap1 = objectMapper.readValue(jsonResponse1, Map.class);
        assertEquals(responseMap1.get("isOk"), true);
        assertNotNull(responseMap1.get("financeRecord"));
        Map<String, Object> responseMap2 = objectMapper.readValue(jsonResponse2, Map.class);
        assertEquals(responseMap2.get("isOk"), false);
        Map<String, Object> responseMap3 = objectMapper.readValue(jsonResponse3, Map.class);
        assertEquals(responseMap3.get("isOk"), false);
    }

    @Test
    void addFinance() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        //正常的测试用例
        Map<String, String> request1 = new HashMap<>();
        request1.put("financeType", String.valueOf(1));
        request1.put("amount", String.valueOf(500));
        request1.put("remark", "备注1");
        String jsonRequest1 = objectMapper.writeValueAsString(request1);
        System.out.println("jsonRequest1: "+jsonRequest1);
        MvcResult result1 = mockMvc.perform(MockMvcRequestBuilders
                .post("/finance/add")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(jsonRequest1)).andReturn();
        String jsonResponse1 = result1.getResponse().getContentAsString();
        System.out.println("jsonResponse1: "+jsonResponse1);
        //异常的测试用例
        Map<String, String> request2 = new HashMap<>();
        request2.put("financeType", String.valueOf(3)); //financeType不等于1或2
        request2.put("amount", String.valueOf(500));
        request2.put("remark", "备注2");
        String jsonRequest2 = objectMapper.writeValueAsString(request2);
        System.out.println("jsonRequest2: "+jsonRequest2);
        MvcResult result2 = mockMvc.perform(MockMvcRequestBuilders
                .post("/finance/add")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(jsonRequest2)).andReturn();
        String jsonResponse2 = result2.getResponse().getContentAsString();
        System.out.println("jsonResponse2: "+jsonResponse2);

        Map<String, String> request3 = new HashMap<>();
        request3.put("financeType", String.valueOf(2));
        request3.put("amount", String.valueOf(-12.34)); //金额不是正整数
        request3.put("remark", "备注3");
        String jsonRequest3 = objectMapper.writeValueAsString(request3);
        System.out.println("jsonRequest3: "+jsonRequest3);
        MvcResult result3 = mockMvc.perform(MockMvcRequestBuilders
                .post("/finance/add")
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
    void deleteById() throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        //正常的测试用例
        Map<String, String> request1 = new HashMap<>();
        request1.put("financeId", String.valueOf(13));
        String jsonRequest1 = objectMapper.writeValueAsString(request1);
        System.out.println("jsonRequest1: "+jsonRequest1);
        MvcResult result1 = mockMvc.perform(MockMvcRequestBuilders
                .post("/finance/deleteById")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(jsonRequest1)).andReturn();
        String jsonResponse1 = result1.getResponse().getContentAsString();
        System.out.println("jsonResponse1: "+jsonResponse1);
        //异常的测试用例
        Map<String, String> request2 = new HashMap<>();
        request2.put("financeId", String.valueOf(1.5)); //financeId为小数
        String jsonRequest2 = objectMapper.writeValueAsString(request2);
        System.out.println("jsonRequest2: "+jsonRequest2);
        MvcResult result2 = mockMvc.perform(MockMvcRequestBuilders
                .post("/finance/deleteById")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(jsonRequest2)).andReturn();
        String jsonResponse2 = result2.getResponse().getContentAsString();
        System.out.println("jsonResponse2: "+jsonResponse2);

        Map<String, String> request3 = new HashMap<>();
        request3.put("financeId", ""); //financeId为空字符串
        String jsonRequest3 = objectMapper.writeValueAsString(request3);
        System.out.println("jsonRequest3: "+jsonRequest3);
        MvcResult result3 = mockMvc.perform(MockMvcRequestBuilders
                .post("/finance/deleteById")
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
    void updateFinanceRecord() throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        //正常的测试用例
        Map<String, String> request1 = new HashMap<>();
        request1.put("financeId", String.valueOf(6));
        request1.put("financeType", String.valueOf(2));
        request1.put("amount", String.valueOf(500));
        request1.put("remark", "备注1");
        String jsonRequest1 = objectMapper.writeValueAsString(request1);
        System.out.println("jsonRequest1: "+jsonRequest1);
        MvcResult result1 = mockMvc.perform(MockMvcRequestBuilders
                .post("/finance/update")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(jsonRequest1)).andReturn();
        String jsonResponse1 = result1.getResponse().getContentAsString();
        System.out.println("jsonResponse1: "+jsonResponse1);
        //异常的测试用例
        Map<String, String> request2 = new HashMap<>();
        request2.put("financeId", String.valueOf(6));
        request2.put("financeType", String.valueOf(1.5)); //financeType不等于1或2
        request2.put("amount", String.valueOf(500));
        request2.put("remark", "备注2");
        String jsonRequest2 = objectMapper.writeValueAsString(request2);
        System.out.println("jsonRequest2: "+jsonRequest2);
        MvcResult result2 = mockMvc.perform(MockMvcRequestBuilders
                .post("/finance/update")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(jsonRequest2)).andReturn();
        String jsonResponse2 = result2.getResponse().getContentAsString();
        System.out.println("jsonResponse2: "+jsonResponse2);

        Map<String, String> request3 = new HashMap<>();
        request3.put("financeId", String.valueOf(3.5)); //financeId不存在
        request3.put("financeType", String.valueOf(1));
        request3.put("amount", String.valueOf(1234));
        request3.put("remark", "备注3");
        String jsonRequest3 = objectMapper.writeValueAsString(request3);
        System.out.println("jsonRequest3: "+jsonRequest3);
        MvcResult result3 = mockMvc.perform(MockMvcRequestBuilders
                .post("/finance/update")
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
}
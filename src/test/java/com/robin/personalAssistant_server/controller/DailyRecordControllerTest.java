package com.robin.personalAssistant_server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.robin.personalAssistant_server.biz.DailyRecordBiz;
import com.robin.personalAssistant_server.entity.DailyRecord;
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
class DailyRecordControllerTest {

    private MockMvc mockMvc;
    private MockHttpSession session;

    @Autowired
    private DailyRecordBiz dailyRecordBiz;

    private DailyRecordController controller = new DailyRecordController();

    public void setDailyRecordBiz(DailyRecordBiz dailyRecordBiz) {
        this.dailyRecordBiz = dailyRecordBiz;
    }

    @BeforeEach
    void setUp() {
        session = new MockHttpSession();
        controller.setBiz(dailyRecordBiz);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        System.out.println("备忘录管理模块测试开始---");
    }

    @AfterEach
    void tearDown() {
        System.out.println("备忘录管理模块测试结束---");
    }

    @Test
    void listAll() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/daily/listAll")
                .accept(MediaType.APPLICATION_JSON_VALUE);
        // mockMvc.perform执行一个请求
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String jsonResponse = result.getResponse().getContentAsString();
        System.out.println(jsonResponse);
        // 使用 ObjectMapper 将 JSON 响应转换为 Map
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> responseMap = objectMapper.readValue(jsonResponse, Map.class);
        assertEquals(responseMap.get("isOk"), true);
        assertNotNull(responseMap.get("dailyRecordList"));
    }

    @Test
    void getById() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        //正常的测试用例
        Map<String, String> request1 = new HashMap<>();
        request1.put("dailyId", String.valueOf(1));
        String jsonRequest1 = objectMapper.writeValueAsString(request1);
        System.out.println("jsonRequest1: "+jsonRequest1);
        MvcResult result1 = mockMvc.perform(MockMvcRequestBuilders
                .post("/daily/getById")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(jsonRequest1)).andReturn();
        String jsonResponse1 = result1.getResponse().getContentAsString();
        System.out.println("jsonResponse1: "+jsonResponse1);
        //异常的测试用例
        Map<String, String> request2 = new HashMap<>();
        request2.put("dailyId", String.valueOf(2.5)); //dailyId为小数
        String jsonRequest2 = objectMapper.writeValueAsString(request2);
        System.out.println("jsonRequest2: "+jsonRequest2);
        MvcResult result2 = mockMvc.perform(MockMvcRequestBuilders
                .post("/daily/getById")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(jsonRequest2)).andReturn();
        String jsonResponse2 = result2.getResponse().getContentAsString();
        System.out.println("jsonResponse2: "+jsonResponse2);

        Map<String, String> request3 = new HashMap<>();
        request3.put("dailyId", "..?"); //dailyId为非数字
        String jsonRequest3 = objectMapper.writeValueAsString(request3);
        System.out.println("jsonRequest3: "+jsonRequest3);
        MvcResult result3 = mockMvc.perform(MockMvcRequestBuilders
                .post("/daily/getById")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(jsonRequest3)).andReturn();
        String jsonResponse3 = result3.getResponse().getContentAsString();
        System.out.println("jsonResponse3: "+jsonResponse3);

        //分别检查测试结果是否与预期一致
        Map<String, Object> responseMap1 = objectMapper.readValue(jsonResponse1, Map.class);
        assertEquals(responseMap1.get("isOk"), true);
        assertNotNull(responseMap1.get("dailyRecord"));
        Map<String, Object> responseMap2 = objectMapper.readValue(jsonResponse2, Map.class);
        assertEquals(responseMap2.get("isOk"), false);
        Map<String, Object> responseMap3 = objectMapper.readValue(jsonResponse3, Map.class);
        assertEquals(responseMap3.get("isOk"), false);
    }

    @Test
    void addDailyRecord() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        //正常的测试用例
        Map<String, String> request1 = new HashMap<>();
        request1.put("title", "期末复习");
        request1.put("content", "胜利就在眼前，加油！发挥出自己最好的水平！");
        String jsonRequest1 = objectMapper.writeValueAsString(request1);
        System.out.println("jsonRequest1: "+jsonRequest1);
        MvcResult result1 = mockMvc.perform(MockMvcRequestBuilders
                .post("/daily/add")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(jsonRequest1)).andReturn();
        String jsonResponse1 = result1.getResponse().getContentAsString();
        System.out.println("jsonResponse1: "+jsonResponse1);
        //异常的测试用例
        Map<String, String> request2 = new HashMap<>();
        request2.put("title", "");  //标题为空
        request2.put("content", "测试空标题");
        String jsonRequest2 = objectMapper.writeValueAsString(request2);
        System.out.println("jsonRequest2: "+jsonRequest2);
        MvcResult result2 = mockMvc.perform(MockMvcRequestBuilders
                .post("/daily/add")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(jsonRequest2)).andReturn();
        String jsonResponse2 = result2.getResponse().getContentAsString();
        System.out.println("jsonResponse2: "+jsonResponse2);

        Map<String, String> request3 = new HashMap<>();
        request3.put("title", "测试空内容");
        request3.put("content", "");  //内容为空
        String jsonRequest3 = objectMapper.writeValueAsString(request3);
        System.out.println("jsonRequest3: "+jsonRequest3);
        MvcResult result3 = mockMvc.perform(MockMvcRequestBuilders
                .post("/daily/add")
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
    void deleteById() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        //正常的测试用例
        Map<String, String> request1 = new HashMap<>();
        request1.put("dailyId", String.valueOf(2));
        String jsonRequest1 = objectMapper.writeValueAsString(request1);
        System.out.println("jsonRequest1: "+jsonRequest1);
        MvcResult result1 = mockMvc.perform(MockMvcRequestBuilders
                .post("/daily/deleteById")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(jsonRequest1)).andReturn();
        String jsonResponse1 = result1.getResponse().getContentAsString();
        System.out.println("jsonResponse1: "+jsonResponse1);
        //异常的测试用例
        Map<String, String> request2 = new HashMap<>();
        request2.put("dailyId", String.valueOf(-1)); //dailyId为负数
        String jsonRequest2 = objectMapper.writeValueAsString(request2);
        System.out.println("jsonRequest2: "+jsonRequest2);
        MvcResult result2 = mockMvc.perform(MockMvcRequestBuilders
                .post("/daily/deleteById")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(jsonRequest2)).andReturn();
        String jsonResponse2 = result2.getResponse().getContentAsString();
        System.out.println("jsonResponse2: "+jsonResponse2);

        Map<String, String> request3 = new HashMap<>();
        request3.put("dailyId", "12ab"); //dailyId包含非数字
        String jsonRequest3 = objectMapper.writeValueAsString(request3);
        System.out.println("jsonRequest3: "+jsonRequest3);
        MvcResult result3 = mockMvc.perform(MockMvcRequestBuilders
                .post("/daily/deleteById")
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
    void updateDailyRecord() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        //正常的测试用例
        DailyRecord dailyRecord1 = new DailyRecord(1, "标题A", "备忘内容A——TEST001");
        String jsonRequest1 = objectMapper.writeValueAsString(dailyRecord1);
        System.out.println("jsonRequest1: "+jsonRequest1);
        MvcResult result1 = mockMvc.perform(MockMvcRequestBuilders
                .post("/daily/update")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(jsonRequest1)).andReturn();
        String jsonResponse1 = result1.getResponse().getContentAsString();
        System.out.println("jsonResponse1: "+jsonResponse1);
        //异常的测试用例
        DailyRecord dailyRecord2 = new DailyRecord(-1, "Test2", "备忘内容2——XXX"); // dailyId不存在
        String jsonRequest2 = objectMapper.writeValueAsString(dailyRecord2);
        System.out.println("jsonRequest2: "+jsonRequest2);
        MvcResult result2 = mockMvc.perform(MockMvcRequestBuilders
                .post("/daily/update")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(jsonRequest2)).andReturn();
        String jsonResponse2 = result2.getResponse().getContentAsString();
        System.out.println("jsonResponse2: "+jsonResponse2);

        DailyRecord dailyRecord3 = new DailyRecord(2, "TITLE", ""); //试图将备忘内容修改为空
        String jsonRequest3 = objectMapper.writeValueAsString(dailyRecord3);
        System.out.println("jsonRequest3: "+jsonRequest3);
        MvcResult result3 = mockMvc.perform(MockMvcRequestBuilders
                .post("/daily/update")
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
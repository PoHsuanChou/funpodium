package com.example.demo;

import com.example.demo.dto.CreateUserRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.web.servlet.function.RequestPredicates.contentType;

@SpringBootTest
@AutoConfigureMockMvc
class Demo1ApplicationTests {

    @Autowired
    private MockMvc mockMvc;
    //addUser
        //檢查正確創建使用者
        //檢查如果已經有重複使用者
        //檢查request如果empty
    //deleteUser
        //檢查正確刪資料
        //檢查request如果empty
    //listUser
       //檢查如果沒有user
       //檢查request如果empty
       //檢查正確
    //addBTC
      //檢查正確新增資料
      //檢查如果沒有user
      //沒錢
      //


    @Test
    @Transactional
    void addUserCorrect() throws Exception {

        val requestBody = CreateUserRequest.builder()
                .username("test4")
                .email("test4@gmail.com")
                .build();
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/btc/addUser")
                .content(new ObjectMapper().writeValueAsString(requestBody))
                .contentType(MediaType.APPLICATION_JSON);

        // 發送 POST 請求
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post("/btc/addUser")
                        .content(new ObjectMapper().writeValueAsString(requestBody))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andReturn();

        // 獲取響應內容
        String responseContent = mvcResult.getResponse().getContentAsString();

        // 斷言響應內容是否為成功新增帳號字串
        assertEquals("成功新增帳號", responseContent);
    }


    @Transactional
    @Test
    void addUserDuplicate() throws Exception {

        addUserCorrect();

        val requestBody = CreateUserRequest.builder()
                .username("test4")
                .email("test4@gmail.com")
                .build();
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/btc/addUser")
                .content(new ObjectMapper().writeValueAsString(requestBody))
                .contentType(MediaType.APPLICATION_JSON);

        // 發送 POST 請求
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post("/btc/addUser")
                        .content(new ObjectMapper().writeValueAsString(requestBody))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("400 BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("使用者已經存在"))
                .andReturn();
    }


}

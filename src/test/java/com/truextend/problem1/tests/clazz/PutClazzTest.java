package com.truextend.problem1.tests.clazz;

import com.truextend.problem1.module.clazz.service.Clazz;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PutClazzTest {

    @SpyBean
    private Map<String, Clazz> volatileClazzes;

    @Autowired
    private MockMvc mockMvc;

    private String clazzCode;

    @Before
    public void setup() {
        clazzCode = "1A-192";
    }

    @After
    public void tearDown() {
        volatileClazzes.get(clazzCode)
                .setTitle("Geology");
        volatileClazzes.get(clazzCode)
                .setDescription("Sedimentary Petrology");
    }

    @Test
    public void PutClazzEndpoint_WithoutCodeInThePayload_ReturnsSuccess() throws Exception {
        assertTrue(volatileClazzes.containsKey(clazzCode));
        mockMvc.perform(put(String.format("/api/classes/%s", clazzCode))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\": \"Math\", \"description\": \"Mathematical Principles\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$.status", is("success")));

        verify(volatileClazzes).put(eq(clazzCode), any(Clazz.class));
    }

    @Test
    public void PutClazzEndpoint_WithCodeInThePayload_ReturnsSuccessWithoutModifyTheCode() throws Exception {
        assertTrue(volatileClazzes.containsKey(clazzCode));
        mockMvc.perform(put(String.format("/api/classes/%s", clazzCode))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"code\": \"6K-014\", \"title\": \"Math\", \"description\": \"Mathematical Principles\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$.status", is("success")));

        verify(volatileClazzes).put(eq(clazzCode), any(Clazz.class));
    }

    @Test
    public void PutClazzEndpoint_WithInvalidClazzCode_ReturnsNotFoundError() throws Exception {
        String invalidClazzCode = "1H-104";
        assertFalse(volatileClazzes.containsKey(invalidClazzCode));
        mockMvc.perform(put(String.format("/api/classes/%s", invalidClazzCode))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\": \"Math\", \"description\": \"Mathematical Principles\"}"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.status", is("error")))
                .andExpect(jsonPath("$.message", is("Class not found")));

        verify(volatileClazzes, never()).put(eq(invalidClazzCode), any(Clazz.class));
    }
}
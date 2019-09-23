package com.truextend.problem1.integration.clazz;

import com.truextend.problem1.module.clazz.service.Clazz;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PostClazzTest {

    @SpyBean
    private Map<String, Clazz> volatileClazzes;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void PostClazzEndpoint_WithoutCode_ReturnsSuccess() throws Exception {
        mockMvc.perform(post("/api/classes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\": \"Art Hitory\", \"description\": \"Art Hitory\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$.status", is("success")));

        verify(volatileClazzes).put(eq("3C-015"), any(Clazz.class));
        verify(volatileClazzes).keySet();
    }

    @Test
    public void PostClazzEndpoint_WithCode_ReturnsSuccess() throws Exception {
        String clazzCode = "4D-011";
        assertFalse(volatileClazzes.containsKey(clazzCode));

        mockMvc.perform(post("/api/classes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"code\": \"4D-011\", \"title\": \"Art Hitory\", \"description\": \"Art Hitory\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$.status", is("success")));

        verify(volatileClazzes).put(eq(clazzCode), any(Clazz.class));
        verify(volatileClazzes, never()).keySet();
        volatileClazzes.remove(clazzCode);
    }


    @Test
    public void PostClazzEndpoint_WithExistsCode_ReturnsBadRequestError() throws Exception {
        String clazzCode = "1A-192";
        assertTrue(volatileClazzes.containsKey(clazzCode));

        mockMvc.perform(post("/api/classes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"code\": \"1A-192\", \"firstName\": \"Carlos\", \"lastName\": \"Estrada\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.status", is("error")))
                .andExpect(jsonPath("$.message", is("Class already exists")));

        verify(volatileClazzes, never()).put(eq(clazzCode), any(Clazz.class));
        verify(volatileClazzes, never()).keySet();
    }
}

package com.truextend.problem1.integration.clazz;

import com.truextend.problem1.module.clazz.service.Clazz;
import com.truextend.problem1.module.student.service.Student;
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

    @Test
    public void PutClazzEndpoint_WithoutCodeInThePayload_ReturnsSuccess() throws Exception {
        String clazzCode = "1A-192";
        assertTrue(volatileClazzes.containsKey(clazzCode));

        mockMvc.perform(put("/api/classes/" + clazzCode)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\": \"Petrology\", \"description\": \"Sedimentary Petrology\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$.status", is("success")));

        verify(volatileClazzes).put(eq(clazzCode), any(Clazz.class));
    }

    @Test
    public void PutClazzEndpoint_WithCodeInThePayload_ReturnsSuccessWithoutModifyTheCode() throws Exception {
        String clazzCode = "1A-192";
        assertTrue(volatileClazzes.containsKey(clazzCode));

        mockMvc.perform(put("/api/classes/" + clazzCode)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"code\": \"1S-014\", \"title\": \"Petrology\", \"description\": \"Sedimentary Petrology\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$.status", is("success")));

        verify(volatileClazzes).put(eq(clazzCode), any(Clazz.class));
    }

    @Test
    public void PutClazzEndpoint_WithInvalidCode_ReturnsNotFoundError() throws Exception {
        String clazzCode = "1H-104";
        assertFalse(volatileClazzes.containsKey(clazzCode));

        mockMvc.perform(put("/api/classes/" + clazzCode)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\": \"Petrology\", \"description\": \"Sedimentary Petrology\"}"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.status", is("error")))
                .andExpect(jsonPath("$.message", is("Class not found")));

        verify(volatileClazzes, never()).put(eq(clazzCode), any(Clazz.class));
    }
}

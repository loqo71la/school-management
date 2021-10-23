package com.tx.schoolmanagement.clazz;

import com.tx.schoolmanagement.module.clazz.service.Clazz;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DeleteClazzTest {

    @SpyBean
    private Map<String, Clazz> volatileClazzes;

    @Autowired
    private MockMvc mockMvc;

    private String clazzCode;

    @BeforeEach
    public void setup() {
        clazzCode = "3A-189";

        Clazz clazz = new Clazz();
        clazz.setId(clazzCode);
        volatileClazzes.put(clazzCode, clazz);
    }

    @AfterEach
    public void tearDown() {
        volatileClazzes.remove(clazzCode);
    }

    @Test
    public void DeleteClazzEndpoint_WithValidClazzCode_ReturnsSuccess() throws Exception {
        mockMvc.perform(delete(String.format("/api/classes/%s", clazzCode)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$.status", is("success")));

        verify(volatileClazzes).remove(clazzCode);
    }

    @Test
    public void DeleteClazzEndpoint_WithInvalidClazzCode_ReturnsSuccess() throws Exception {
        String invalidClazzCode = "1F-192";
        assertFalse(volatileClazzes.containsKey(invalidClazzCode));

        mockMvc.perform(delete(String.format("/api/classes/%s", invalidClazzCode)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$.status", is("success")));

        verify(volatileClazzes).remove(invalidClazzCode);
    }
}

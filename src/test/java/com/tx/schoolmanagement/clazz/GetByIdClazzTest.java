package com.tx.schoolmanagement.clazz;

import com.tx.schoolmanagement.module.clazz.service.Clazz;
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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GetByIdClazzTest {

    @SpyBean
    private Map<String, Clazz> volatileClazzes;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void GetClazzByCodeEndpoint_WithValidClazzCode_ReturnsAClazz() throws Exception {
        String clazzCode = "3C-014";
        assertTrue(volatileClazzes.containsKey(clazzCode));

        mockMvc.perform(get(String.format("/api/classes/%s", clazzCode)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(3)))
                .andExpect(jsonPath("$.code", is(clazzCode)))
                .andExpect(jsonPath("$.title", is("Music")))
                .andExpect(jsonPath("$.description", is("Art of Listening")));

        verify(volatileClazzes).get(clazzCode);
    }

    @Test
    public void GetClazzByCodeEndpoint_WithInvalidClazzCode_ReturnsNotFoundError() throws Exception {
        String invalidClazzCode = "8C-718";
        assertFalse(volatileClazzes.containsKey(invalidClazzCode));

        mockMvc.perform(get(String.format("/api/classes/%s", invalidClazzCode)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.status", is("error")))
                .andExpect(jsonPath("$.message", is("Class not found")));

        verify(volatileClazzes, never()).get(invalidClazzCode);
    }
}

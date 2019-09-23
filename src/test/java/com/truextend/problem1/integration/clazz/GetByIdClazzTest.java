package com.truextend.problem1.integration.clazz;

import com.truextend.problem1.module.clazz.service.Clazz;
import com.truextend.problem1.module.student.service.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GetByIdClazzTest {

    @SpyBean
    private Map<String, Clazz> volatileClazzes;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void GetClazzByCodeEndpoint_WithValidId_ReturnsAClazz() throws Exception {
        String clazzCode = "3C-014";
        assertTrue(volatileClazzes.containsKey(clazzCode));

        mockMvc.perform(get("/api/classes/" + clazzCode))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(3)))
                .andExpect(jsonPath("$.code", is(clazzCode)))
                .andExpect(jsonPath("$.title", is("Art of Listening")))
                .andExpect(jsonPath("$.description", is("Art of Listening")));

        verify(volatileClazzes).get(clazzCode);
    }

    @Test
    public void GetClazzByCodeEndpoint_WithInvalidId_ReturnsNotFoundError() throws Exception {
        String clazzCode = "3C-018";
        assertFalse(volatileClazzes.containsKey(clazzCode));

        mockMvc.perform(get("/api/classes/" + clazzCode))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.status", is("error")))
                .andExpect(jsonPath("$.message", is("Class not found")));

        verify(volatileClazzes, never()).get(clazzCode);
    }
}

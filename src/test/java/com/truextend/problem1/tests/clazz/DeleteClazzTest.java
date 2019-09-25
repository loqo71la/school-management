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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DeleteClazzTest {

    @SpyBean
    private Map<String, Clazz> volatileClazzes;

    @Autowired
    private MockMvc mockMvc;

    private String clazzCode;

    @Before
    public void setup() {
        clazzCode = "3A-189";

        Clazz clazz = new Clazz();
        clazz.setId(clazzCode);
        volatileClazzes.put(clazzCode, clazz);
    }

    @After
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

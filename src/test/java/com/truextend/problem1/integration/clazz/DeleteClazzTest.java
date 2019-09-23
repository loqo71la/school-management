package com.truextend.problem1.integration.clazz;

import com.truextend.problem1.module.clazz.service.Clazz;
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

    @Test
    public void DeleteClazzEndpoint_WithValidCode_ReturnsSuccess() throws Exception {
        Clazz clazz = new Clazz();
        clazz.setId("3A-189");
        volatileClazzes.put(clazz.getId(), clazz);
        assertTrue(volatileClazzes.containsKey(clazz.getId()));

        mockMvc.perform(delete("/api/classes/" + clazz.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$.status", is("success")));

        verify(volatileClazzes).remove(clazz.getId());
    }

    @Test
    public void DeleteClazzEndpoint_WithInvalidCode_ReturnsSuccess() throws Exception {
        String clazzCode = "1F-192";
        assertFalse(volatileClazzes.containsKey(clazzCode));

        mockMvc.perform(delete("/api/classes/" + clazzCode))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$.status", is("success")));

        verify(volatileClazzes).remove(clazzCode);
    }
}

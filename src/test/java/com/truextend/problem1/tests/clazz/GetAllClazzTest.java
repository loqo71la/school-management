package com.truextend.problem1.tests.clazz;

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
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GetAllClazzTest {

    @SpyBean
    private Map<String, Clazz> volatileClazzes;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void GetAllClazzEndpoint_WithoutFilters_ReturnsTheClazzList() throws Exception {
        mockMvc.perform(get("/api/classes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].code", is("1A-192")))
                .andExpect(jsonPath("$[0].title", is("Geology")))
                .andExpect(jsonPath("$[0].description", is("Sedimentary Petrology")))
                .andExpect(jsonPath("$[1].code", is("2B-032")))
                .andExpect(jsonPath("$[1].title", is("Engineering")))
                .andExpect(jsonPath("$[1].description", is("Principles of computational geo-location analysis")))
                .andExpect(jsonPath("$[2].code", is("3C-014")))
                .andExpect(jsonPath("$[2].title", is("Music")))
                .andExpect(jsonPath("$[2].description", is("Art of Listening")));

        verify(volatileClazzes).values();
    }

    @Test
    public void GetAllClazzEndpoint_WithInvalidFilters_ReturnsBadRequestError() throws Exception {
        mockMvc.perform(get("/api/classes?title=music&owner=juan&male=true"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.status", is("error")))
                .andExpect(jsonPath("$.message", is("Invalid filters: [owner, male]")));

        verify(volatileClazzes, never()).values();
    }

    @Test
    public void GetAllClazzEndpoint_WithValidFilters_ReturnsTheClazzList() throws Exception {
        mockMvc.perform(get("/api/classes?description=art"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].code", is("3C-014")))
                .andExpect(jsonPath("$[0].title", is("Music")))
                .andExpect(jsonPath("$[0].description", is("Art of Listening")));

        verify(volatileClazzes).values();
    }
}

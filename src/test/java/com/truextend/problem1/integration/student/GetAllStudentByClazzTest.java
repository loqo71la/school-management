package com.truextend.problem1.integration.student;

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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GetAllStudentByClazzTest {

    @SpyBean
    private Map<String, Clazz> volatileClazzes;

    @SpyBean
    private Map<Integer, Student> volatileStudents;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void GetAllStudentByClazzEndpoint_WithoutFilters_ReturnsTheStudentList() throws Exception {
        String clazzCode = "1A-192";
        assertTrue(volatileClazzes.containsKey(clazzCode));

        mockMvc.perform(get(String.format("/api/classes/%s/students", clazzCode)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].studentId", is(5)))
                .andExpect(jsonPath("$[0].firstName", is("Jane")))
                .andExpect(jsonPath("$[0].lastName", is("Graham")))
                .andExpect(jsonPath("$[1].studentId", is(13)))
                .andExpect(jsonPath("$[1].firstName", is("Judith")))
                .andExpect(jsonPath("$[1].lastName", is("Gray")))
                .andExpect(jsonPath("$[2].studentId", is(45)))
                .andExpect(jsonPath("$[2].firstName", is("John")))
                .andExpect(jsonPath("$[2].lastName", is("Wilson")));

        // Includes the initial verification
        verify(volatileClazzes, times(2)).containsKey(clazzCode);
        verify(volatileStudents).containsKey(5);
        verify(volatileStudents).containsKey(13);
        verify(volatileStudents).containsKey(45);

        verify(volatileClazzes).get(clazzCode);
        verify(volatileStudents).get(5);
        verify(volatileStudents).get(13);
        verify(volatileStudents).get(45);
    }

    @Test
    public void GetAllStudentByClazzEndpoint_WithInvalidStudent_ReturnsNotFoundError() throws Exception {
        String clazzCode = "1A-582";
        assertFalse(volatileClazzes.containsKey(clazzCode));

        mockMvc.perform(get(String.format("/api/classes/%s/students", clazzCode)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.status", is("error")))
                .andExpect(jsonPath("$.message", is("Class not found")));

        // Includes the initial verification
        verify(volatileClazzes, times(2)).containsKey(clazzCode);
        verify(volatileClazzes, never()).get(clazzCode);
        verifyZeroInteractions(volatileStudents);
    }

    @Test
    public void GetAllClazzByStudentEndpoint_WithInvalidFilters_ReturnsBadRequestError() throws Exception {
        String clazzCode = "1A-582";
        assertFalse(volatileClazzes.containsKey(clazzCode));

        mockMvc.perform(get(String.format("/api/classes/%s/students?firstName=judith&class=math&floor=3", clazzCode)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.status", is("error")))
                .andExpect(jsonPath("$.message", is("Invalid filters: [class, floor]")));

        // Includes the initial verification
        verify(volatileClazzes).containsKey(clazzCode);
        verify(volatileClazzes, never()).get(clazzCode);
        verifyZeroInteractions(volatileStudents);
    }

    @Test
    public void GetAllClazzByStudentEndpoint_WithValidFilters_ReturnsTheClazzList() throws Exception {
        String clazzCode = "1A-192";
        assertTrue(volatileClazzes.containsKey(clazzCode));

        mockMvc.perform(get(String.format("/api/classes/%s/students?firstName=judith", clazzCode)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].studentId", is(13)))
                .andExpect(jsonPath("$[0].firstName", is("Judith")))
                .andExpect(jsonPath("$[0].lastName", is("Gray")));

        // Include the initial verification
        verify(volatileClazzes, times(2)).containsKey(clazzCode);
        verify(volatileStudents).containsKey(5);
        verify(volatileStudents).containsKey(13);
        verify(volatileStudents).containsKey(45);

        verify(volatileClazzes).get(clazzCode);
        verify(volatileStudents).get(5);
        verify(volatileStudents).get(13);
        verify(volatileStudents).get(45);
    }
}

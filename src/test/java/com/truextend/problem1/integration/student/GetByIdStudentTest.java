package com.truextend.problem1.integration.student;

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
public class GetByIdStudentTest {

    @SpyBean
    private Map<Integer, Student> volatileStudents;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void GetStudentByIdEndpoint_WithValidId_ReturnsTheStudent() throws Exception {
        int studentId = 45;
        assertTrue(volatileStudents.containsKey(studentId));

        mockMvc.perform(get("/api/students/" + studentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(3)))
                .andExpect(jsonPath("$.studentId", is(studentId)))
                .andExpect(jsonPath("$.firstName", is("John")))
                .andExpect(jsonPath("$.lastName", is("Wilson")));

        verify(volatileStudents).get(studentId);
    }

    @Test
    public void GetStudentByIdEndpoint_WithInvalidId_ReturnsNotFoundError() throws Exception {
        int studentId = 8;
        assertFalse(volatileStudents.containsKey(studentId));

        mockMvc.perform(get("/api/students/" + studentId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.status", is("error")))
                .andExpect(jsonPath("$.message", is("Student not found")));

        verify(volatileStudents, never()).get(studentId);
    }
}

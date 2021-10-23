package com.tx.schoolmanagement.student;

import com.tx.schoolmanagement.module.student.service.Student;
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
public class GetByIdStudentTest {

    @SpyBean
    private Map<Integer, Student> volatileStudents;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void GetStudentByIdEndpoint_WithValidStudentId_ReturnsTheStudent() throws Exception {
        Integer studentId = 45;
        assertTrue(volatileStudents.containsKey(studentId));

        mockMvc.perform(get(String.format("/api/students/%d", studentId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(3)))
                .andExpect(jsonPath("$.studentId", is(studentId)))
                .andExpect(jsonPath("$.firstName", is("John")))
                .andExpect(jsonPath("$.lastName", is("Wilson")));

        verify(volatileStudents).get(studentId);
    }

    @Test
    public void GetStudentByIdEndpoint_WithInvalidStudentId_ReturnsNotFoundError() throws Exception {
        Integer invalidStudentId = 8;
        assertFalse(volatileStudents.containsKey(invalidStudentId));

        mockMvc.perform(get(String.format("/api/students/%d", invalidStudentId)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.status", is("error")))
                .andExpect(jsonPath("$.message", is("Student not found")));

        verify(volatileStudents, never()).get(invalidStudentId);
    }
}

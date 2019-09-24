package com.truextend.problem1.integration.student;

import com.truextend.problem1.module.student.service.Student;
import org.junit.After;
import org.junit.Before;
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
public class PutStudentTest {

    @SpyBean
    private Map<Integer, Student> volatileStudents;

    @Autowired
    private MockMvc mockMvc;

    private Integer studentId;

    @Before
    public void setup() {
        studentId = 5;
    }

    @After
    public void tearDown() {
        volatileStudents.get(studentId)
                .setName("Jane");
        volatileStudents.get(studentId)
                .setLastName("Graham");
    }

    @Test
    public void PutStudentEndpoint_WithoutIdInThePayload_ReturnsSuccess() throws Exception {
        assertTrue(volatileStudents.containsKey(studentId));
        mockMvc.perform(put(String.format("/api/students/%d", studentId))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\": \"Jane\", \"lastName\": \"Bam\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$.status", is("success")));

        verify(volatileStudents).put(eq(studentId), any(Student.class));
    }

    @Test
    public void PutStudentEndpoint_WithIdInThePayload_ReturnsSuccessWithoutModifyTheId() throws Exception {
        assertTrue(volatileStudents.containsKey(studentId));
        mockMvc.perform(put(String.format("/api/students/%d", studentId))
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("{\"studentId\": %d, \"firstName\": \"Jane\", \"lastName\": \"Bam\"}", studentId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$.status", is("success")));

        verify(volatileStudents).put(eq(studentId), any(Student.class));
    }

    @Test
    public void PutStudentEndpoint_WithInvalidId_ReturnsNotFoundError() throws Exception {
        int studentId = 8;
        assertFalse(volatileStudents.containsKey(studentId));

        mockMvc.perform(put(String.format("/api/students/%d", studentId))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Pam\", \"lastName\": \"Bam\"}"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.status", is("error")))
                .andExpect(jsonPath("$.message", is("Student not found")));

        verify(volatileStudents, never()).put(eq(studentId), any(Student.class));
    }
}

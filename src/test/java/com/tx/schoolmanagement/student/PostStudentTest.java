package com.tx.schoolmanagement.student;

import com.tx.schoolmanagement.module.student.service.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PostStudentTest {

    @SpyBean
    private Map<Integer, Student> volatileStudents;

    @Autowired
    private MockMvc mockMvc;

    private Integer studentId;

    @BeforeEach
    public void setup() {
        studentId = 46;
    }

    @AfterEach
    public void tearDown() {
        volatileStudents.remove(studentId);
    }

    @Test
    public void PostStudentEndpoint_WithoutId_ReturnsSuccess() throws Exception {
        assertFalse(volatileStudents.containsKey(studentId));
        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\": \"Carlos\", \"lastName\": \"Estrada\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$.status", is("success")));

        verify(volatileStudents).put(eq(studentId), any(Student.class));
        verify(volatileStudents).keySet();
    }

    @Test
    public void PostStudentEndpoint_WithId_ReturnsSuccess() throws Exception {
        assertFalse(volatileStudents.containsKey(studentId));
        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("{\"studentId\": %d, \"firstName\": \"Carlos\", \"lastName\": \"Estrada\"}", studentId)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$.status", is("success")));

        verify(volatileStudents).put(eq(studentId), any(Student.class));
        verify(volatileStudents, never()).keySet();
    }

    @Test
    public void PostStudentEndpoint_WithExistingStudentId_ReturnsBadRequestError() throws Exception {
        int existingStudentId = 27;
        assertTrue(volatileStudents.containsKey(existingStudentId));

        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("{\"studentId\": %d, \"firstName\": \"Carlos\", \"lastName\": \"Estrada\"}", existingStudentId)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.status", is("error")))
                .andExpect(jsonPath("$.message", is("Student already exists")));

        verify(volatileStudents, never()).put(eq(existingStudentId), any(Student.class));
        verify(volatileStudents, never()).keySet();
    }
}

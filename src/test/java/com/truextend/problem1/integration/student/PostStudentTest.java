package com.truextend.problem1.integration.student;

import com.truextend.problem1.module.student.service.Student;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PostStudentTest {

    @SpyBean
    private Map<Integer, Student> volatileStudents;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void PostStudentEndpoint_WithoutId_ReturnsSuccess() throws Exception {
        mockMvc.perform(post("/api/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\": \"Carlos\", \"lastName\": \"Estrada\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$.status", is("success")));

        verify(volatileStudents).put(eq(46), any(Student.class));
        verify(volatileStudents).keySet();
    }

    @Test
    public void PostStudentEndpoint_WithId_ReturnsSuccess() throws Exception {
        int studentId = 4;
        assertFalse(volatileStudents.containsKey(studentId));

        mockMvc.perform(post("/api/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"studentId\": 4, \"firstName\": \"Carlos\", \"lastName\": \"Estrada\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$.status", is("success")));

        verify(volatileStudents).put(eq(studentId), any(Student.class));
        verify(volatileStudents, never()).keySet();
    }


    @Test
    public void PostStudentEndpoint_WithExistsId_ReturnsBadRequestError() throws Exception {
        int studentId = 27;
        assertTrue(volatileStudents.containsKey(studentId));

        mockMvc.perform(post("/api/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"studentId\": 27, \"firstName\": \"Carlos\", \"lastName\": \"Estrada\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.status", is("error")))
                .andExpect(jsonPath("$.message", is("Student already exists")));

        verify(volatileStudents, never()).put(eq(studentId), any(Student.class));
        verify(volatileStudents, never()).keySet();
    }
}

package com.truextend.problem1.tests.student;

import com.truextend.problem1.module.student.service.Student;
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
public class DeleteStudentTest {

    @SpyBean
    private Map<Integer, Student> volatileStudents;

    @Autowired
    private MockMvc mockMvc;

    private Integer studentId;

    @Before
    public void setup() {
        studentId = 289;

        Student student = new Student();
        student.setId(studentId);
        volatileStudents.put(studentId, student);
    }

    @After
    public void tearDown() {
        volatileStudents.remove(studentId);
    }

    @Test
    public void DeleteStudentEndpoint_WithValidStudentId_ReturnsSuccess() throws Exception {
        mockMvc.perform(delete(String.format("/api/students/%d", studentId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$.status", is("success")));

        verify(volatileStudents).remove(studentId);
    }

    @Test
    public void DeleteStudentEndpoint_WithInvalidId_ReturnsSuccess() throws Exception {
        int invalidStudentId = 192;
        assertFalse(volatileStudents.containsKey(invalidStudentId));

        mockMvc.perform(delete(String.format("/api/students/%d", invalidStudentId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$.status", is("success")));

        verify(volatileStudents).remove(invalidStudentId);
    }
}

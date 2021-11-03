package com.tx.schoolmanagement.student;

import com.tx.schoolmanagement.module.student.repository.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DeleteStudentTest {

    @SpyBean
    private Map<String, Student> volatileStudents;

    @Autowired
    private MockMvc mockMvc;

    private String studentId;

    @BeforeEach
    public void setup() {
        studentId = "289";

        Student student = new Student();
        student.setId(studentId);
        volatileStudents.put(studentId, student);
    }

    @AfterEach
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

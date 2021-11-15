package com.tx.schoolmanagement.student;

import com.tx.schoolmanagement.module.student.repository.Student;
import com.tx.schoolmanagement.module.student.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Optional;

import static com.tx.schoolmanagement.TestUtil.buildStudent;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PutStudentTest {

    @MockBean
    private StudentRepository studentRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void PutStudentEndpoint_WithValidPayload_ReturnsSuccess() throws Exception {
        // Arrange
        when(studentRepository.findById("00001"))
            .thenReturn(Optional.of(buildStudent("00001", "Jose", "Perez")));

        // Act
        ResultActions result = mockMvc.perform(put("/api/students/00001")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"firstName\": \"Jose\", \"lastName\": \"Perez\"}"));

        // Assert
        result.andExpect(status().isOk())
            .andExpect(jsonPath("$.*", hasSize(2)))
            .andExpect(jsonPath("$.status", is("success")))
            .andExpect(jsonPath("$.message", is("Student with ID [00001] was successfully updated.")));

        verify(studentRepository).findById("00001");
        verify(studentRepository).save(any(Student.class));
    }

    @Test
    public void PutStudentEndpoint_WithInvalidId_ReturnsNotFoundError() throws Exception {
        // Arrange
        when(studentRepository.findById("00001"))
            .thenReturn(Optional.empty());

        // Act
        ResultActions result = mockMvc.perform(put("/api/students/00001")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"firstName\": \"Jose\", \"lastName\": \"Perez\"}"));

        // Assert
        result.andExpect(status().isNotFound())
            .andExpect(jsonPath("$.*", hasSize(2)))
            .andExpect(jsonPath("$.status", is("error")))
            .andExpect(jsonPath("$.message", is("Student with ID [00001] was not found.")));

        verify(studentRepository).findById("00001");
        verify(studentRepository, never()).save(any(Student.class));
    }
}

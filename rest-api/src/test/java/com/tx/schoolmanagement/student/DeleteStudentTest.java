package com.tx.schoolmanagement.student;

import com.tx.schoolmanagement.module.clazz.repository.Clazz;
import com.tx.schoolmanagement.module.clazz.repository.ClazzRepository;
import com.tx.schoolmanagement.module.student.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.Optional;

import static com.tx.schoolmanagement.TestUtil.buildClazz;
import static com.tx.schoolmanagement.TestUtil.buildStudent;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DeleteStudentTest {

    @MockBean
    private StudentRepository studentRepository;

    @MockBean
    private ClazzRepository clazzRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void DeleteStudentEndpoint_WithValidStudentId_ReturnsSuccess() throws Exception {
        // Arrange
        var clazz = buildClazz("1A-192", "Geology", "Sedimentary Petrology");
        clazz.addStudent(buildStudent("00001", "Jose", "Perez"));
        when(clazzRepository.findAllByStudent("00001")).thenReturn(List.of(clazz));
        when(clazzRepository.findById("1A-192")).thenReturn(Optional.of(clazz));

        // Act
        ResultActions result = mockMvc.perform(delete("/api/students/00001"));

        // Assert
        result.andExpect(status().isOk())
            .andExpect(jsonPath("$.*", hasSize(2)))
            .andExpect(jsonPath("$.status", is("success")))
            .andExpect(jsonPath("$.message", is("Student with ID [00001] was successfully removed.")));

        assertTrue(clazz.getStudentList().isEmpty());
        verify(clazzRepository).findAllByStudent("00001");
        verify(clazzRepository).findById("1A-192");
        verify(clazzRepository).save(clazz);
        verify(studentRepository).deleteById("00001");
    }

    @Test
    public void DeleteStudentEndpoint_WithInvalidId_ReturnsSuccess() throws Exception {
        // Arrange
        when(clazzRepository.findAllByStudent("00001")).thenReturn(List.of());

        // Act
        ResultActions result = mockMvc.perform(delete("/api/students/00001"));

        // Assert
        result.andExpect(status().isOk())
            .andExpect(jsonPath("$.*", hasSize(2)))
            .andExpect(jsonPath("$.status", is("success")))
            .andExpect(jsonPath("$.message", is("Student with ID [00001] was successfully removed.")));

        verify(clazzRepository).findAllByStudent("00001");
        verify(clazzRepository, never()).findById(anyString());
        verify(clazzRepository, never()).save(any(Clazz.class));
        verify(studentRepository).deleteById("00001");
    }
}

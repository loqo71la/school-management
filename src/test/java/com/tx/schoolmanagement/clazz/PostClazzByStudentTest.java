package com.tx.schoolmanagement.clazz;

import com.tx.schoolmanagement.module.clazz.repository.Clazz;
import com.tx.schoolmanagement.module.clazz.repository.ClazzRepository;
import com.tx.schoolmanagement.module.student.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Optional;

import static com.tx.schoolmanagement.TestUtil.buildClazz;
import static com.tx.schoolmanagement.TestUtil.buildStudent;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PostClazzByStudentTest {

    @MockBean
    private ClazzRepository clazzRepository;

    @MockBean
    private StudentRepository studentRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void PostClazzByStudentEndpoint_WithoutPayload_ReturnsSuccess() throws Exception {
        // Arrange
        var clazz = buildClazz("3A-189", "Geology", "Sedimentary Petrology");
        var student = buildStudent("00001", "Jose", "Perez");
        when(studentRepository.findById("00001")).thenReturn(Optional.of(student));
        when(clazzRepository.findById("3A-189")).thenReturn(Optional.of(clazz));

        // Act
        ResultActions result = mockMvc.perform(post("/api/students/00001/classes/3A-189"));

        // Assert
        result.andExpect(status().isOk())
            .andExpect(jsonPath("$.*", hasSize(2)))
            .andExpect(jsonPath("$.status", is("success")))
            .andExpect(jsonPath("$.message", is("Class with ID [3A-189] was assigned to Student with ID [00001].")));

        assertTrue(clazz.getStudentList().contains(student));
        verify(studentRepository).findById("00001");
        verify(clazzRepository).findById("3A-189");
        verify(clazzRepository).save(clazz);
    }

    @Test
    public void PostClazzByStudentEndpoint_WithInvalidStudentId_ReturnsNotFoundError() throws Exception {
        // Arrange
        var clazz = buildClazz("3A-189", "Geology", "Sedimentary Petrology");
        when(studentRepository.findById("00001")).thenReturn(Optional.empty());
        when(clazzRepository.findById("3A-189")).thenReturn(Optional.of(clazz));

        // Act
        ResultActions result = mockMvc.perform(post("/api/students/00001/classes/3A-189"));

        // Assert
        result.andExpect(status().isNotFound())
            .andExpect(jsonPath("$.*", hasSize(2)))
            .andExpect(jsonPath("$.status", is("error")))
            .andExpect(jsonPath("$.message", is("Student with ID [00001] was not found.")));

        assertTrue(clazz.getStudentList().isEmpty());
        verify(studentRepository).findById("00001");
        verify(clazzRepository).findById("3A-189");
        verify(clazzRepository, never()).save(clazz);
    }

    @Test
    public void PutClazzByStudentEndpoint_WithInvalidClazzCode_ReturnsNotFoundError() throws Exception {
        // Arrange
        var student = buildStudent("00001", "Jose", "Perez");
        when(studentRepository.findById("00001")).thenReturn(Optional.of(student));
        when(clazzRepository.findById("3A-189")).thenReturn(Optional.empty());

        // Act
        ResultActions result = mockMvc.perform(post("/api/students/00001/classes/3A-189"));

        // Assert
        result.andExpect(status().isNotFound())
            .andExpect(jsonPath("$.*", hasSize(2)))
            .andExpect(jsonPath("$.status", is("error")))
            .andExpect(jsonPath("$.message", is("Class with ID [3A-189] was not found.")));

        verify(clazzRepository).findById("3A-189");
        verify(studentRepository, never()).findById("00001");
        verify(clazzRepository, never()).save(any(Clazz.class));
    }
}

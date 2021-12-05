package com.tx.schoolmanagement.student;

import com.tx.schoolmanagement.module.clazz.repository.Clazz;
import com.tx.schoolmanagement.module.clazz.repository.ClazzRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DeleteStudentByClazzTest {

    @MockBean
    private ClazzRepository clazzRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void DeleteStudentByClazzEndpoint_WithoutPayload_ReturnsSuccess() throws Exception {
        // Arrange
        var clazz = buildClazz("3A-189", "Geology", "Sedimentary Petrology");
        clazz.addStudent(buildStudent("00001", "Jose", "Perez"));
        when(clazzRepository.findById("3A-189")).thenReturn(Optional.of(clazz));

        // Act
        ResultActions result = mockMvc.perform(delete("/api/classes/3A-189/students/00001"));

        // Assert
        result.andExpect(status().isOk())
            .andExpect(jsonPath("$.*", hasSize(2)))
            .andExpect(jsonPath("$.status", is("success")))
            .andExpect(jsonPath("$.message", is("Student with ID [00001] was unassigned from Class with ID [3A-189].")));

        assertTrue(clazz.getStudentList().isEmpty());
        verify(clazzRepository).findById("3A-189");
        verify(clazzRepository).save(clazz);
    }

    @Test
    public void DeleteStudentByClazzEndpoint_WithInvalidClazzCode_ReturnsNotFoundError() throws Exception {
        // Arrange
        when(clazzRepository.findById("3A-189")).thenReturn(Optional.empty());

        // Act
        ResultActions result = mockMvc.perform(delete("/api/classes/3A-189/students/00001"));

        // Assert
        result.andExpect(status().isNotFound())
            .andExpect(jsonPath("$.*", hasSize(2)))
            .andExpect(jsonPath("$.status", is("error")))
            .andExpect(jsonPath("$.message", is("Class with ID [3A-189] was not found.")));

        verify(clazzRepository).findById("3A-189");
        verify(clazzRepository, never()).save(any(Clazz.class));
    }

    @Test
    public void DeleteStudentByClazzEndpoint_WithInvalidStudentId_ReturnsNotFoundError() throws Exception {
        // Arrange
        var clazz = buildClazz("3A-189", "Geology", "Sedimentary Petrology");
        when(clazzRepository.findById("3A-189")).thenReturn(Optional.of(clazz));

        // Act
        ResultActions result = mockMvc.perform(delete("/api/classes/3A-189/students/00001"));

        // Assert
        result.andExpect(status().isNotFound())
            .andExpect(jsonPath("$.*", hasSize(2)))
            .andExpect(jsonPath("$.status", is("error")))
            .andExpect(jsonPath("$.message", is("Student with ID [00001] was not found.")));

        assertTrue(clazz.getStudentList().isEmpty());
        verify(clazzRepository).findById("3A-189");
        verify(clazzRepository, never()).save(clazz);
    }
}

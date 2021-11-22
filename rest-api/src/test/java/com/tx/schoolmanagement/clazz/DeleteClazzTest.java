package com.tx.schoolmanagement.clazz;

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
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DeleteClazzTest {

    @MockBean
    private ClazzRepository clazzRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void DeleteClazzEndpoint_WithValidClazzCode_ReturnsSuccess() throws Exception {
        // Arrange
        when(clazzRepository.findById("3A-189"))
            .thenReturn(Optional.of(buildClazz("3A-189", "Geology", "Sedimentary Petrology")));

        // Act
        ResultActions result = mockMvc.perform(delete("/api/classes/3A-189"));

        // Assert
        result.andExpect(status().isOk())
            .andExpect(jsonPath("$.*", hasSize(2)))
            .andExpect(jsonPath("$.status", is("success")))
            .andExpect(jsonPath("$.message", is("Class with ID [3A-189] was successfully removed.")));

        verify(clazzRepository).findById("3A-189");
        verify(clazzRepository).deleteById("3A-189");
    }

    @Test
    public void DeleteClazzEndpoint_WithInvalidClazzCode_ReturnsSuccess() throws Exception {
        // Arrange
        when(clazzRepository.findById("1F-192")).thenReturn(Optional.empty());

        // Act
        ResultActions result = mockMvc.perform(delete("/api/classes/1F-192"));

        // Assert
        result.andExpect(status().isOk())
            .andExpect(jsonPath("$.*", hasSize(2)))
            .andExpect(jsonPath("$.status", is("success")))
            .andExpect(jsonPath("$.message", is("Class with ID [1F-192] was successfully removed.")));

        verify(clazzRepository).findById("1F-192");
        verify(clazzRepository, never()).deleteById("1F-192");
    }
}

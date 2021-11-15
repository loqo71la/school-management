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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GetByIdClazzTest {

    @MockBean
    private ClazzRepository clazzRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void GetClazzByCodeEndpoint_WithValidClazzCode_ReturnsAClazz() throws Exception {
        // Arrange
        when(clazzRepository.findById("1A-192"))
            .thenReturn(Optional.of(buildClazz("1A-192", "Geology", "Sedimentary Petrology")));

        // Act
        ResultActions result = mockMvc.perform(get("/api/classes/1A-192"));

        // Assert
        result.andExpect(status().isOk())
            .andExpect(jsonPath("$.code", is("1A-192")))
            .andExpect(jsonPath("$.title", is("Geology")))
            .andExpect(jsonPath("$.description", is("Sedimentary Petrology")));

        verify(clazzRepository).findById("1A-192");
    }

    @Test
    public void GetClazzByCodeEndpoint_WithInvalidClazzCode_ReturnsNotFoundError() throws Exception {
        // Arrange
        when(clazzRepository.findById("1A-192"))
            .thenReturn(Optional.empty());

        // Act
        ResultActions result = mockMvc.perform(get("/api/classes/1A-192"));

        // Assert
        result.andExpect(status().isNotFound())
            .andExpect(jsonPath("$.*", hasSize(2)))
            .andExpect(jsonPath("$.status", is("error")))
            .andExpect(jsonPath("$.message", is("Class with ID [1A-192] was not found.")));

        verify(clazzRepository).findById("1A-192");
    }
}

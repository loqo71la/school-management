package com.tx.schoolmanagement.clazz;

import com.tx.schoolmanagement.module.clazz.repository.Clazz;
import com.tx.schoolmanagement.module.clazz.repository.ClazzRepository;
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

import static com.tx.schoolmanagement.TestUtil.buildClazz;
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
public class PutClazzTest {

    @MockBean
    private ClazzRepository clazzRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void PutClazzEndpoint_WithValidPayload_ReturnsSuccess() throws Exception {
        // Arrange
        when(clazzRepository.findById("1A-192"))
            .thenReturn(Optional.of(buildClazz("1A-192", "Geology", "Sedimentary Petrology")));

        // Act
        ResultActions result = mockMvc.perform(put("/api/classes/1A-192")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"title\": \"Art Hitory\", \"description\": \"Art Hitory\"}"));

        // Assert
        result.andExpect(status().isOk())
            .andExpect(jsonPath("$.*", hasSize(2)))
            .andExpect(jsonPath("$.status", is("success")))
            .andExpect(jsonPath("$.message", is("Class with ID [1A-192] was successfully updated.")));

        verify(clazzRepository).findById("1A-192");
        verify(clazzRepository).save(any(Clazz.class));
    }

    @Test
    public void PutClazzEndpoint_WithInvalidClazzCode_ReturnsNotFoundError() throws Exception {
        // Arrange
        when(clazzRepository.findById("1A-192"))
            .thenReturn(Optional.empty());

        // Act
        ResultActions result = mockMvc.perform(put("/api/classes/1A-192")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"title\": \"Math\", \"description\": \"Mathematical Principles\"}"));

        // Assert
        result.andExpect(status().isNotFound())
            .andExpect(jsonPath("$.*", hasSize(2)))
            .andExpect(jsonPath("$.status", is("error")))
            .andExpect(jsonPath("$.message", is("Class with ID [1A-192] was not found.")));

        verify(clazzRepository).findById("1A-192");
        verify(clazzRepository, never()).save(any(Clazz.class));
    }
}

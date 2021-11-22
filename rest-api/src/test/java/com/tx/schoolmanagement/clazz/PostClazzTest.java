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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PostClazzTest {

    @MockBean
    private ClazzRepository clazzRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void PostClazzEndpoint_WithNotExistingCode_ReturnsSuccess() throws Exception {
        // Arrange
        when(clazzRepository.findById("1A-192"))
            .thenReturn(Optional.empty());

        // Act
        ResultActions result = mockMvc.perform(post("/api/classes")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"code\": \"1A-192\", \"title\": \"Art Hitory\", \"description\": \"Art Hitory\"}"));

        // Assert
        result.andExpect(status().isCreated())
            .andExpect(jsonPath("$.*", hasSize(2)))
            .andExpect(jsonPath("$.status", is("success")))
            .andExpect(jsonPath("$.message", is("Class with ID [1A-192] was successfully created.")));

        verify(clazzRepository).findById("1A-192");
        verify(clazzRepository).save(any(Clazz.class));
    }


    @Test
    public void PostClazzEndpoint_WithExistingClazzCode_ReturnsAlreadyExistError() throws Exception {
        // Arrange
        when(clazzRepository.findById("1A-192"))
            .thenReturn(Optional.of(buildClazz("1A-192", "Geology", "Sedimentary Petrology")));

        // Act
        ResultActions result = mockMvc.perform(post("/api/classes")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"code\": \"1A-192\", \"title\": \"Art Hitory\", \"description\": \"Art Hitory\"}"));

        // Assert
        result.andExpect(status().isConflict())
            .andExpect(jsonPath("$.*", hasSize(2)))
            .andExpect(jsonPath("$.status", is("error")))
            .andExpect(jsonPath("$.message", is("Class with ID [1A-192] already exist.")));

        verify(clazzRepository).findById("1A-192");
        verify(clazzRepository, never()).save(any(Clazz.class));
    }
}

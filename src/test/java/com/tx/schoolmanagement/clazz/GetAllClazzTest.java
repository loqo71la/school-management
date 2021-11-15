package com.tx.schoolmanagement.clazz;

import com.tx.schoolmanagement.module.clazz.repository.ClazzRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

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
public class GetAllClazzTest {

    @MockBean
    private ClazzRepository clazzRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void GetAllClazzEndpoint_WithoutFilters_ReturnsTheClazzPage() throws Exception {
        // Arrange
        var pageable = PageRequest.of(0, 15);
        var clazzList = List.of(
            buildClazz("1A-192", "Geology", "Sedimentary Petrology"),
            buildClazz("2B-032", "Engineering", "Principles of computational geo-location analysis"),
            buildClazz("3C-014", "Music", "Art of Listening")
        );
        when(clazzRepository.findAll(pageable))
            .thenReturn(new PageImpl<>(clazzList, pageable, 3));

        // Act
        ResultActions result = mockMvc.perform(get("/api/classes"));

        // Assert
        result.andExpect(status().isOk())
            .andExpect(jsonPath("$.*", hasSize(4)))
            .andExpect(jsonPath("$.totalItem", is(3)))
            .andExpect(jsonPath("$.totalPage", is(1)))
            .andExpect(jsonPath("$.currentPage", is(0)))
            .andExpect(jsonPath("$.items[0].code", is("1A-192")))
            .andExpect(jsonPath("$.items[0].title", is("Geology")))
            .andExpect(jsonPath("$.items[0].description", is("Sedimentary Petrology")))
            .andExpect(jsonPath("$.items[1].code", is("2B-032")))
            .andExpect(jsonPath("$.items[1].title", is("Engineering")))
            .andExpect(jsonPath("$.items[1].description", is("Principles of computational geo-location analysis")))
            .andExpect(jsonPath("$.items[2].code", is("3C-014")))
            .andExpect(jsonPath("$.items[2].title", is("Music")))
            .andExpect(jsonPath("$.items[2].description", is("Art of Listening")));

        verify(clazzRepository).findAll(pageable);
    }

    @Test
    public void GetAllClazzEndpoint_WithSizeFilters_ReturnsTheFirstClazzPage() throws Exception {
        // Arrange
        var pageable = PageRequest.of(0, 1);
        var clazzList = List.of(
            buildClazz("1A-192", "Geology", "Sedimentary Petrology")
        );
        when(clazzRepository.findAll(pageable))
            .thenReturn(new PageImpl<>(clazzList, pageable, 3));

        // Act
        ResultActions result = mockMvc.perform(get("/api/classes?size=1"));

        // Assert
        result.andExpect(status().isOk())
            .andExpect(jsonPath("$.*", hasSize(4)))
            .andExpect(jsonPath("$.totalItem", is(3)))
            .andExpect(jsonPath("$.totalPage", is(3)))
            .andExpect(jsonPath("$.currentPage", is(0)))
            .andExpect(jsonPath("$.items[0].code", is("1A-192")))
            .andExpect(jsonPath("$.items[0].title", is("Geology")))
            .andExpect(jsonPath("$.items[0].description", is("Sedimentary Petrology")));

        verify(clazzRepository).findAll(pageable);
    }

    @Test
    public void GetAllClazzEndpoint_WithPageFilters_ReturnsTheSpecificClazzPage() throws Exception {
        // Arrange
        var pageable = PageRequest.of(4, 2);
        var clazzList = List.of(
            buildClazz("1A-192", "Geology", "Sedimentary Petrology")
        );
        when(clazzRepository.findAll(pageable))
            .thenReturn(new PageImpl<>(clazzList, pageable, 9));

        // Act
        ResultActions result = mockMvc.perform(get("/api/classes?size=2&page=4"));

        // Assert
        result.andExpect(status().isOk())
            .andExpect(jsonPath("$.*", hasSize(4)))
            .andExpect(jsonPath("$.totalItem", is(9)))
            .andExpect(jsonPath("$.totalPage", is(5)))
            .andExpect(jsonPath("$.currentPage", is(4)))
            .andExpect(jsonPath("$.items[0].code", is("1A-192")))
            .andExpect(jsonPath("$.items[0].title", is("Geology")))
            .andExpect(jsonPath("$.items[0].description", is("Sedimentary Petrology")));

        verify(clazzRepository).findAll(pageable);
    }
}

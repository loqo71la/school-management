package com.truextend.problem1.integration.clazz;

import com.truextend.problem1.module.clazz.service.Clazz;
import com.truextend.problem1.module.student.service.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GetAllClazzByStudentTest {

    @SpyBean
    private Map<String, Clazz> volatileClazzes;

    @SpyBean
    private Map<Integer, Student> volatileStudents;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void GetAllClazzByStudentEndpoint_WithoutFilters_ReturnsTheClazzList() throws Exception {
        int studentId = 45;
        assertTrue(volatileStudents.containsKey(studentId));

        mockMvc.perform(get(String.format("/api/students/%d/classes", studentId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].code", is("1A-192")))
                .andExpect(jsonPath("$[0].title", is("Geology")))
                .andExpect(jsonPath("$[0].description", is("Sedimentary Petrology")))
                .andExpect(jsonPath("$[1].code", is("2B-032")))
                .andExpect(jsonPath("$[1].title", is("Engineering")))
                .andExpect(jsonPath("$[1].description", is("Principles of computational geo-location analysis")))
                .andExpect(jsonPath("$[2].code", is("3C-014")))
                .andExpect(jsonPath("$[2].title", is("Music")))
                .andExpect(jsonPath("$[2].description", is("Art of Listening")));

        // Includes the initial verification
        verify(volatileStudents, times(2)).containsKey(studentId);
        verify(volatileClazzes).containsKey("1A-192");
        verify(volatileClazzes).containsKey("2B-032");
        verify(volatileClazzes).containsKey("3C-014");

        verify(volatileStudents).get(studentId);
        verify(volatileClazzes).get("1A-192");
        verify(volatileClazzes).get("2B-032");
        verify(volatileClazzes).get("3C-014");
    }

    @Test
    public void GetAllClazzByStudentEndpoint_WithInvalidStudent_ReturnsNotFoundError() throws Exception {
        int studentId = 174;
        assertFalse(volatileStudents.containsKey(studentId));

        mockMvc.perform(get(String.format("/api/students/%d/classes", studentId)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.status", is("error")))
                .andExpect(jsonPath("$.message", is("Student not found")));

        // Includes the initial verification
        verify(volatileStudents, times(2)).containsKey(studentId);
        verify(volatileStudents, never()).get(studentId);
        verifyZeroInteractions(volatileClazzes);
    }

    @Test
    public void GetAllClazzByStudentEndpoint_WithInvalidFilters_ReturnsBadRequestError() throws Exception {
        int studentId = 45;
        assertTrue(volatileStudents.containsKey(studentId));

        mockMvc.perform(get(String.format("/api/students/%d/classes?title=music&owner=juan&male=true", studentId)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.status", is("error")))
                .andExpect(jsonPath("$.message", is("Invalid filters: [owner, male]")));

        // Includes the initial verification
        verify(volatileStudents).containsKey(studentId);
        verify(volatileStudents, never()).get(studentId);
        verifyZeroInteractions(volatileClazzes);
    }

    @Test
    public void GetAllClazzByStudentEndpoint_WithValidFilters_ReturnsTheClazzList() throws Exception {
        int studentId = 45;
        assertTrue(volatileStudents.containsKey(studentId));

        mockMvc.perform(get(String.format("/api/students/%d/classes?description=art", studentId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].code", is("3C-014")))
                .andExpect(jsonPath("$[0].title", is("Music")))
                .andExpect(jsonPath("$[0].description", is("Art of Listening")));

        // Include the initial verification
        verify(volatileStudents, times(2)).containsKey(studentId);
        verify(volatileClazzes).containsKey("1A-192");
        verify(volatileClazzes).containsKey("2B-032");
        verify(volatileClazzes).containsKey("3C-014");

        verify(volatileStudents).get(studentId);
        verify(volatileClazzes).get("1A-192");
        verify(volatileClazzes).get("2B-032");
        verify(volatileClazzes).get("3C-014");
    }
}

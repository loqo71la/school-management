package com.truextend.problem1.integration.student;

import com.truextend.problem1.module.student.service.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GetAllStudentTest {

    @SpyBean
    private Map<Integer, Student> volatileStudents;

    @Autowired
    private MockMvc mockMvc;

    private List<Student> studentList;

    @Test
    public void GetAllStudentEndpoint_WithoutFilters_ReturnsTheStudentList() throws Exception {
        mockMvc.perform(get("/api/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].studentId", is(5)))
                .andExpect(jsonPath("$[0].firstName", is("Jane")))
                .andExpect(jsonPath("$[0].lastName", is("Graham")))
                .andExpect(jsonPath("$[1].studentId", is(27)))
                .andExpect(jsonPath("$[1].firstName", is("Pam")))
                .andExpect(jsonPath("$[1].lastName", is("Bam")))
                .andExpect(jsonPath("$[2].studentId", is(45)))
                .andExpect(jsonPath("$[2].firstName", is("John")))
                .andExpect(jsonPath("$[2].lastName", is("Wilson")));

        verify(volatileStudents).values();
    }

    @Test
    public void GetAllStudentEndpoint_WithInvalidFilters_ReturnsBadRequestError() throws Exception {
        mockMvc.perform(get("/api/students?firstName=john&age=12&class=math"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.status", is("error")))
                .andExpect(jsonPath("$.message", is("Invalid filters: [age, class]")));

        verify(volatileStudents, never()).values();
    }

    @Test
    public void GetAllStudentEndpoint_WithValidFilters_ReturnsTheStudentList() throws Exception {
        mockMvc.perform(get("/api/students?firstName=pam"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].studentId", is(27)))
                .andExpect(jsonPath("$[0].firstName", is("Pam")))
                .andExpect(jsonPath("$[0].lastName", is("Bam")));

        verify(volatileStudents).values();
    }
}

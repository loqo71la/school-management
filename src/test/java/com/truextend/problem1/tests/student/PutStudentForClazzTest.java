package com.truextend.problem1.tests.student;

import com.truextend.problem1.module.clazz.service.Clazz;
import com.truextend.problem1.module.student.service.Student;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PutStudentForClazzTest {

    @SpyBean
    private Map<Integer, Student> volatileStudents;

    @SpyBean
    private Map<String, Clazz> volatileClazzes;

    @Autowired
    private MockMvc mockMvc;

    private Integer studentId;

    private String clazzCode;

    @Before
    public void setup() {
        clazzCode = "2B-032";
        studentId = 32;
    }

    @After
    public void tearDown() {
        volatileClazzes.get(clazzCode)
                .getStudentIdList()
                .remove(studentId);
        volatileStudents.get(studentId)
                .getClazzCodeList()
                .remove(clazzCode);
    }

    @Test
    public void PutStudentForClazzEndpoint_WithoutPayload_ReturnsSuccess() throws Exception {
        mockMvc.perform(put(String.format("/api/classes/%s/students/%d", clazzCode, studentId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$.status", is("success")));

        verify(volatileClazzes).containsKey(clazzCode);
        verify(volatileClazzes).get(clazzCode);

        verify(volatileStudents).containsKey(studentId);
        verify(volatileStudents).get(studentId);
    }

    @Test
    public void PutStudentForClazzEndpoint_WithPayload_ReturnsSuccessWithoutModifyTheClazz() throws Exception {
        mockMvc.perform(put(String.format("/api/classes/%s/students/%d", clazzCode, studentId))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"code\": \"1S-014\", \"title\": \"Petrology\", \"description\": \"Sedimentary\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$.status", is("success")));

        verify(volatileClazzes).containsKey(clazzCode);
        verify(volatileClazzes).get(clazzCode);

        verify(volatileStudents).containsKey(studentId);
        verify(volatileStudents).get(studentId);
    }

    @Test
    public void PutStudentForClazzEndpoint_WithInvalidClazzCode_ReturnsNotFoundError() throws Exception {
        String invalidClazzCode = "9K-071";
        mockMvc.perform(put(String.format("/api/classes/%s/students/%d", invalidClazzCode, studentId)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.status", is("error")))
                .andExpect(jsonPath("$.message", is("Class not found")));

        verify(volatileClazzes).containsKey(invalidClazzCode);
        verify(volatileClazzes, never()).get(invalidClazzCode);
        verifyZeroInteractions(volatileStudents);
    }

    @Test
    public void PutStudentForClazzEndpoint_WithInvalidStudentId_ReturnsNotFoundError() throws Exception {
        int invalidStudentId = 1841;
        mockMvc.perform(put(String.format("/api/classes/%s/students/%s", clazzCode, invalidStudentId)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.status", is("error")))
                .andExpect(jsonPath("$.message", is("Student not found")));

        verify(volatileClazzes).containsKey(clazzCode);
        verify(volatileClazzes).get(clazzCode);

        verify(volatileStudents).containsKey(invalidStudentId);
        verify(volatileStudents, never()).get(invalidStudentId);
    }
}

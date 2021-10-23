package com.tx.schoolmanagement.clazz;

import com.tx.schoolmanagement.module.clazz.service.Clazz;
import com.tx.schoolmanagement.module.student.service.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PutClazzForStudentTest {

    @SpyBean
    private Map<Integer, Student> volatileStudents;

    @SpyBean
    private Map<String, Clazz> volatileClazzes;

    @Autowired
    private MockMvc mockMvc;

    private Integer studentId;

    private String clazzCode;

    @BeforeEach
    public void setup() {
        clazzCode = "2B-032";
        studentId = 32;
    }

    @AfterEach
    public void tearDown() {
        volatileClazzes.get(clazzCode)
                .getStudentIdList()
                .remove(studentId);
        volatileStudents.get(studentId)
                .getClazzCodeList()
                .remove(clazzCode);
    }

    @Test
    public void PutClazzForStudentEndpoint_WithoutPayload_ReturnsSuccess() throws Exception {
        mockMvc.perform(put(String.format("/api/students/%d/classes/%s", studentId, clazzCode)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$.status", is("success")));

        verify(volatileStudents).containsKey(studentId);
        verify(volatileStudents).get(studentId);

        verify(volatileClazzes).containsKey(clazzCode);
        verify(volatileClazzes).get(clazzCode);
    }

    @Test
    public void PutClazzForStudentEndpoint_WithPayload_ReturnsSuccessWithoutModifyTheClazz() throws Exception {
        mockMvc.perform(put(String.format("/api/students/%d/classes/%s", studentId, clazzCode))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"code\": \"1S-014\", \"title\": \"Petrology\", \"description\": \"Sedimentary\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$.status", is("success")));

        verify(volatileStudents).containsKey(studentId);
        verify(volatileStudents).get(studentId);

        verify(volatileClazzes).containsKey(clazzCode);
        verify(volatileClazzes).get(clazzCode);
    }

    @Test
    public void PutClazzForStudentEndpoint_WithInvalidStudentId_ReturnsNotFoundError() throws Exception {
        int invalidStudentId = 3105;
        mockMvc.perform(put(String.format("/api/students/%d/classes/%s", invalidStudentId, clazzCode)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.status", is("error")))
                .andExpect(jsonPath("$.message", is("Student not found")));

        verify(volatileStudents).containsKey(invalidStudentId);
        verify(volatileStudents, never()).get(invalidStudentId);
        verifyNoMoreInteractions(volatileClazzes);
    }

    @Test
    public void PutClazzForStudentEndpoint_WithInvalidClazzCode_ReturnsNotFoundError() throws Exception {
        String invalidClazzCode = "6Z-802";
        mockMvc.perform(put(String.format("/api/students/%d/classes/%s", studentId, invalidClazzCode)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.status", is("error")))
                .andExpect(jsonPath("$.message", is("Class not found")));

        verify(volatileStudents).containsKey(studentId);
        verify(volatileStudents).get(studentId);

        verify(volatileClazzes).containsKey(invalidClazzCode);
        verify(volatileClazzes, never()).get(invalidClazzCode);
    }
}

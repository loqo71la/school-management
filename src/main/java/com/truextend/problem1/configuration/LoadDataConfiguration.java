package com.truextend.problem1.configuration;

import com.truextend.problem1.module.student.service.Student;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class LoadDataConfiguration {

    @Bean
    public Map<Integer, Student> loadStudents() {
        Student johnStudent = new Student();
        johnStudent.setId(45);
        johnStudent.setName("John");
        johnStudent.setLastName("Wilson");

        Student janeStudent = new Student();
        janeStudent.setId(5);
        janeStudent.setName("Jane");
        janeStudent.setLastName("Graham");

        Student pamStudent = new Student();
        pamStudent.setId(27);
        pamStudent.setName("Pam");
        pamStudent.setLastName("Bam");

        Map<Integer, Student> volatileStudents = new HashMap<>();
        volatileStudents.put(johnStudent.getId(), johnStudent);
        volatileStudents.put(janeStudent.getId(), janeStudent);
        volatileStudents.put(pamStudent.getId(), pamStudent);
        return volatileStudents;
    }
}

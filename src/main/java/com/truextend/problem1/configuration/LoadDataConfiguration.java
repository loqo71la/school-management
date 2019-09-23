package com.truextend.problem1.configuration;

import com.truextend.problem1.module.clazz.service.Clazz;
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

    @Bean
    public Map<String, Clazz> loadClazzes() {
        Clazz geologyClazz = new Clazz();
        geologyClazz.setDescription("Sedimentary Petrology");
        geologyClazz.setTitle("Sedimentary Petrology");
        geologyClazz.setId("1A-192");

        Clazz musicClazz = new Clazz();
        musicClazz.setDescription("Art of Listening");
        musicClazz.setTitle("Art of Listening");
        musicClazz.setId("3C-014");

        Map<String, Clazz> volatileClazzes = new HashMap<>();
        volatileClazzes.put(geologyClazz.getId(), geologyClazz);
        volatileClazzes.put(musicClazz.getId(), musicClazz);
        return volatileClazzes;
    }
}

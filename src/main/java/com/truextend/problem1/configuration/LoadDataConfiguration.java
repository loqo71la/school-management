package com.truextend.problem1.configuration;

import com.truextend.problem1.module.clazz.service.Clazz;
import com.truextend.problem1.module.student.service.Student;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Provides the default data.
 */
@Configuration
public class LoadDataConfiguration {

    /**
     * Loads the map of student in the system.
     *
     * @return the students.
     */
    @Bean
    public Map<Integer, Student> loadStudents() {
        Student johnStudent = new Student();
        johnStudent.setId(45);
        johnStudent.setName("John");
        johnStudent.setLastName("Wilson");
        johnStudent.addClazzCode("1A-192");
        johnStudent.addClazzCode("2B-032");
        johnStudent.addClazzCode("3C-014");

        Student janeStudent = new Student();
        janeStudent.setId(5);
        janeStudent.setName("Jane");
        janeStudent.setLastName("Graham");
        janeStudent.addClazzCode("1A-192");

        Student pamStudent = new Student();
        pamStudent.setId(27);
        pamStudent.setName("Pam");
        pamStudent.setLastName("Bam");
        pamStudent.addClazzCode("2B-032");
        pamStudent.addClazzCode("3C-014");

        Student judithStudent = new Student();
        judithStudent.setId(13);
        judithStudent.setName("Judith");
        judithStudent.setLastName("Gray");
        judithStudent.addClazzCode("1A-192");

        Student steveStudent = new Student();
        steveStudent.setId(32);
        steveStudent.setName("Steve");
        steveStudent.setLastName("Collin");

        Map<Integer, Student> volatileStudents = new HashMap<>();
        volatileStudents.put(judithStudent.getId(), judithStudent);
        volatileStudents.put(steveStudent.getId(), steveStudent);
        volatileStudents.put(johnStudent.getId(), johnStudent);
        volatileStudents.put(janeStudent.getId(), janeStudent);
        volatileStudents.put(pamStudent.getId(), pamStudent);
        return volatileStudents;
    }

    /**
     * Loads the map of clazzes in the system.
     *
     * @return the clazzes.s
     */
    @Bean
    public Map<String, Clazz> loadClazzes() {
        Clazz geologyClazz = new Clazz();
        geologyClazz.setDescription("Sedimentary Petrology");
        geologyClazz.setTitle("Geology");
        geologyClazz.setId("1A-192");
        geologyClazz.addStudentId(5);
        geologyClazz.addStudentId(13);
        geologyClazz.addStudentId(45);

        Clazz musicClazz = new Clazz();
        musicClazz.setDescription("Art of Listening");
        musicClazz.setTitle("Music");
        musicClazz.setId("3C-014");
        musicClazz.addStudentId(27);
        musicClazz.addStudentId(45);

        Clazz engineeringClazz = new Clazz();
        engineeringClazz.setDescription("Principles of computational geo-location analysis");
        engineeringClazz.setTitle("Engineering");
        engineeringClazz.setId("2B-032");
        engineeringClazz.addStudentId(45);
        engineeringClazz.addStudentId(27);

        Map<String, Clazz> volatileClazzes = new HashMap<>();
        volatileClazzes.put(engineeringClazz.getId(), engineeringClazz);
        volatileClazzes.put(geologyClazz.getId(), geologyClazz);
        volatileClazzes.put(musicClazz.getId(), musicClazz);
        return volatileClazzes;
    }
}

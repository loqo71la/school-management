package com.tx.schoolmanagement;

import com.tx.schoolmanagement.module.clazz.repository.Clazz;
import com.tx.schoolmanagement.module.student.repository.Student;

import java.util.ArrayList;
import java.util.List;

public final class TestUtil {

    private TestUtil() {
    }

    public static Clazz buildClazz(String clazzCode, String title, String description) {
        Clazz clazz = new Clazz();
        clazz.setId(clazzCode);
        clazz.setTitle(title);
        clazz.setDescription(description);
        clazz.setStudentList(new ArrayList<>());
        return clazz;
    }

    public static Student buildStudent(String studentId, String name, String lastName) {
        Student student = new Student();
        student.setId(studentId);
        student.setName(name);
        student.setLastname(lastName);
        student.setClazzList(new ArrayList<>());
        return student;
    }
}

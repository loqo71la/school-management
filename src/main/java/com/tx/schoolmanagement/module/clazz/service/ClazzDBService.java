package com.tx.schoolmanagement.module.clazz.service;

import com.tx.schoolmanagement.module.clazz.repository.Clazz;
import com.tx.schoolmanagement.module.clazz.repository.ClazzRepository;
import com.tx.schoolmanagement.module.common.service.DBService;
import com.tx.schoolmanagement.module.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

@Service
public class ClazzDBService extends DBService<String, Clazz> implements ClazzService {

    @Autowired
    private StudentService studentService;

    @Autowired
    public void setModelRepository(ClazzRepository clazzRepository) {
        super.modelRepository = clazzRepository;
    }

    @Override
    public Page<Clazz> readAll(Map<String, String> queryParams, Pageable pageable) {
        String description = queryParams.get("description");
        String title = queryParams.get("title");

        ClazzRepository clazzRepository = getClazzRepository();
        if (title != null && description != null) {
            return clazzRepository.findAllByTitleAndDescription(title, description, pageable);
        }
        return description != null ?
            clazzRepository.findAllByDescription(description, pageable) :
            title != null ?
                clazzRepository.findAllByTitle(title, pageable) :
                clazzRepository.findAll(pageable);
    }

    @Override
    public Page<Clazz> readAllByStudent(String studentId, Map<String, String> queryParams, Pageable pageable) {
        return getClazzRepository().findAllByStudent(studentId, pageable);
    }

    @Override
    public void update(String id, Clazz newClazz) {
        Clazz clazz = super.read(id);
        clazz.setTitle(newClazz.getTitle());
        clazz.setDescription(newClazz.getDescription());
        clazz.setEnable(newClazz.getEnable());
        clazz.setModifiedDate(new Date());
        super.modelRepository.save(clazz);
    }

    @Override
    public void assignStudent(String clazzCode, String studentId) {
        Clazz clazz = super.read(clazzCode);
        if (!contains(clazz, studentId)) {
            clazz.addStudent(studentService.read(studentId));
            super.modelRepository.save(clazz);
        }
    }

    @Override
    public void unassignStudent(String clazzCode, String studentId) {
        Clazz clazz = super.read(clazzCode);
        int index = indexOf(clazz, studentId);
        if (index != -1) {
            clazz.getStudentList().remove(index);
            super.modelRepository.save(clazz);
        }
    }

    private ClazzRepository getClazzRepository() {
        return (ClazzRepository) super.modelRepository;
    }

    private boolean contains(Clazz clazz, String studentId) {
        return indexOf(clazz, studentId) != -1;
    }

    private int indexOf(Clazz clazz, String studentId) {
        IntFunction<String> getId = index -> clazz.getStudentList()
            .get(index)
            .getId();
        return IntStream.range(0, clazz.getStudentList().size())
            .filter(index -> Objects.equals(getId.apply(index), studentId))
            .findFirst()
            .orElse(-1);
    }
}

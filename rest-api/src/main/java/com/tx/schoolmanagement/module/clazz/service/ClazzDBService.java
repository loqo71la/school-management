package com.tx.schoolmanagement.module.clazz.service;

import com.tx.schoolmanagement.module.clazz.repository.Clazz;
import com.tx.schoolmanagement.module.clazz.repository.ClazzRepository;
import com.tx.schoolmanagement.module.common.exception.NotFoundException;
import com.tx.schoolmanagement.module.common.service.DBService;
import com.tx.schoolmanagement.module.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.tx.schoolmanagement.module.common.constant.DtoConstants.CLAZZ_MODEL;
import static com.tx.schoolmanagement.module.common.constant.DtoConstants.STUDENT_MODEL;
import static com.tx.schoolmanagement.module.common.utils.ServiceUtil.contains;
import static com.tx.schoolmanagement.module.common.utils.ServiceUtil.indexOf;

@Service
public class ClazzDBService extends DBService<String, Clazz> implements ClazzService {

    @Autowired
    private StudentService studentService;

    @Autowired
    private ClazzRepository clazzRepository;

    @Override
    public List<Clazz> readAllByStudent(String studentId) {
        return clazzRepository.findAllByStudent(studentId);
    }

    @Override
    public Page<Clazz> readPageByStudent(String studentId, Pageable pageable) {
        return clazzRepository.findPageByStudent(studentId, pageable);
    }

    @Override
    public void update(String clazzCode, Clazz newClazz) {
        Clazz clazz = super.read(clazzCode);
        clazz.setDescription(newClazz.getDescription());
        clazz.setEnable(newClazz.getEnable());
        clazz.setTitle(newClazz.getTitle());
        clazz.setModifiedDate(new Date());
        clazzRepository.save(clazz);
    }

    @Override
    public void delete(String clazzCode) {
        Optional<Clazz> clazz = super.readById(clazzCode);
        if (clazz.isPresent()) {
            clazz.get().getStudentList()
                .removeAll(clazz.get().getStudentList());
            clazzRepository.deleteById(clazzCode);
        }
    }

    @Override
    public void assignStudent(String clazzCode, String studentId) {
        Clazz clazz = super.read(clazzCode);
        if (!contains(clazz.getStudentList(), studentId)) {
            clazz.addStudent(studentService.read(studentId));
            clazzRepository.save(clazz);
        }
    }

    @Override
    public void unassignStudent(String clazzCode, String studentId) {
        Clazz clazz = super.read(clazzCode);
        int index = indexOf(clazz.getStudentList(), studentId);
        if (index == -1) {
            throw new NotFoundException(STUDENT_MODEL, studentId);
        }
        clazz.getStudentList().remove(index);
        clazzRepository.save(clazz);
    }

    @Override
    protected PagingAndSortingRepository<Clazz, String> getModelRepository() {
        return clazzRepository;
    }

    @Override
    protected String getModelName() {
        return CLAZZ_MODEL;
    }
}

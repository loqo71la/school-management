package com.tx.schoolmanagement.module.clazz.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClazzRepository extends PagingAndSortingRepository<Clazz, String> {

    @Query("select c from Clazz c inner join c.studentList s where s.id = :studentId")
    List<Clazz> findAllByStudent(String studentId);

    @Query("select c from Clazz c inner join c.studentList s where s.id = :studentId")
    Page<Clazz> findPageByStudent(String studentId, Pageable pageable);
}

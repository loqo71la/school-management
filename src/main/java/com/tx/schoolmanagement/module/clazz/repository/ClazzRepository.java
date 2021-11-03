package com.tx.schoolmanagement.module.clazz.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClazzRepository extends PagingAndSortingRepository<Clazz, String> {

    Page<Clazz> findAll(Pageable pageable);

    Page<Clazz> findAllByTitle(String title, Pageable pageable);

    Page<Clazz> findAllByDescription(String description, Pageable pageable);

    Page<Clazz> findAllByTitleAndDescription(String title, String description, Pageable pageable);

    @Query("select c from Clazz c inner join c.studentList s where s.id = :studentId")
    Page<Clazz> findAllByStudent(String studentId, Pageable pageable);
}

package com.tx.schoolmanagement.module.student.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends PagingAndSortingRepository<Student, String> {

    Page<Student> findAll(Pageable pageable);

    Page<Student> findAllByName(String name, Pageable pageable);

    Page<Student> findAllByLastname(String lastname, Pageable pageable);

    Page<Student> findAllByNameAndLastname(String name, String lastname, Pageable pageable);

    @Query("select s FROM Student s JOIN s.clazzList c WHERE c.id = :clazzCode")
    Page<Student> findAllByClazz(String clazzCode, Pageable pageable);
}

package com.nilesh.demo4.repository;

import org.springframework.data.repository.CrudRepository;

import com.nilesh.demo4.model.Student;

public interface SaveStudentRepository extends CrudRepository<Student, Integer> {

}

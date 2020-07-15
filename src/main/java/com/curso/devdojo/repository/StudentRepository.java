package com.curso.devdojo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.curso.devdojo.model.Student;

public interface StudentRepository extends CrudRepository<Student, Long> {
	List<Student> findByNameIgnoreCaseContaining(String name);
}

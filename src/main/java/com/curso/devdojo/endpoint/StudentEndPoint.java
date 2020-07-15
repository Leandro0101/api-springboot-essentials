package com.curso.devdojo.endpoint;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.curso.devdojo.error.ResourceNotFoundException;
import com.curso.devdojo.model.Student;
import com.curso.devdojo.repository.StudentRepository;

@RestController
@RequestMapping("students")
public class StudentEndPoint {
	private final StudentRepository studentDao;

	@Autowired
	public StudentEndPoint(StudentRepository studentDao) {
		this.studentDao = studentDao;
	}

//	@RequestMapping(method = RequestMethod.GET, path = "/list")
	@GetMapping
	public ResponseEntity<?> listAll() {
		return new ResponseEntity<>(studentDao.findAll(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getStudentById(@PathVariable("id") long id) {
		verifyIfStudentExists(id);
		Student student = studentDao.findById(id).get();
		
		return new ResponseEntity<>(student, HttpStatus.OK);
	}
	
	@GetMapping("/findByName/{name}")
	public ResponseEntity<?> findStudentByName(@PathVariable String name){
		return new ResponseEntity<>(studentDao.findByNameIgnoreCaseContaining(name), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<?> save(@Valid @RequestBody Student student, HttpServletResponse response) {
		return new ResponseEntity<>(studentDao.save(student), HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable long id) {
		verifyIfStudentExists(id);
		studentDao.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<?> update(@Valid @RequestBody Student student) {
		verifyIfStudentExists(student.getId());
		studentDao.save(student);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	private void verifyIfStudentExists(Long id) {
		if (!studentDao.findById(id).isPresent()) {
			throw new ResourceNotFoundException("Student not found for ID: "+id);
		}
	}

}

package com.terceiraIdade.terceira_idade_API.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.terceiraIdade.terceira_idade_API.models.Student;
import com.terceiraIdade.terceira_idade_API.services.StudentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/student")
@Validated
public class StudentController {

	@Autowired
	private StudentService studentService;

	@GetMapping
	public ResponseEntity<List<Student>> findAll() {
		List<Student> students = this.studentService.findAll();
		return ResponseEntity.ok().body(students);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Student> findById(@PathVariable Long id) {
		Student student = this.studentService.findById(id);
		return ResponseEntity.ok().body(student);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> create(@RequestBody @Valid Student studentObj) {
		this.studentService.create(studentObj);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> update(@Valid @RequestBody Student student, @PathVariable Long id) {
		this.studentService.update(student, id);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		this.studentService.delete(id);
		return ResponseEntity.noContent().build();
	}
}

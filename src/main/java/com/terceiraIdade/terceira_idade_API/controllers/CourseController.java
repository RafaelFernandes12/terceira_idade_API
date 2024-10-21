package com.terceiraIdade.terceira_idade_API.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.terceiraIdade.terceira_idade_API.exceptions.ErrorResponse;
import com.terceiraIdade.terceira_idade_API.models.Course;
import com.terceiraIdade.terceira_idade_API.services.CourseService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/course")
@Validated
public class CourseController {

	@Autowired
	private CourseService courseService;

	@GetMapping
	public ResponseEntity<List<Course>> findAll() {
		List<Course> courses = this.courseService.findAll();
		return ResponseEntity.ok().body(courses);
	}

	@GetMapping("/archive")
	public ResponseEntity<List<Course>> findAllByIsArchivedFalse(@RequestParam boolean isArchived) {
		List<Course> courses = this.courseService.findAllByIsArchived(isArchived);
		return ResponseEntity.ok().body(courses);
	}

	@GetMapping("/search")
	public ResponseEntity<List<Course>> findByName(@RequestParam String name) {
		List<Course> courses = this.courseService.findByName(name);
		return ResponseEntity.ok().body(courses);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Course> findById(@PathVariable Long id) {
		Course course = this.courseService.findById(id);
		return ResponseEntity.ok().body(course);
	}

	@PostMapping
	public ResponseEntity<ErrorResponse> create(@RequestBody @Valid Course courseObj) {
		this.courseService.create(courseObj);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@Valid @RequestBody Course course, @PathVariable Long id) {
		this.courseService.update(course, id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/archive/{id}")
	public ResponseEntity<Void> updateArchived(@PathVariable Long id) {
		this.courseService.archiveCourse(id);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		this.courseService.delete(id);
		return ResponseEntity.noContent().build();
	}
}

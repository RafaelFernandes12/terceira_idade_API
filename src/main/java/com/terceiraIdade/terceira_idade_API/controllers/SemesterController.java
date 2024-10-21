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
import org.springframework.web.bind.annotation.RestController;

import com.terceiraIdade.terceira_idade_API.models.Semester;
import com.terceiraIdade.terceira_idade_API.services.SemesterService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/semester")
@Validated
public class SemesterController {

	@Autowired
	private SemesterService semesterService;

	@GetMapping
	public ResponseEntity<List<Semester>> findAll() {
		List<Semester> obj = this.semesterService.findAll();
		return ResponseEntity.ok().body(obj);
	}

	@GetMapping("/{year}")
	public ResponseEntity<Semester> findById(@PathVariable Double year) {
		Semester obj = this.semesterService.findById(year);
		return ResponseEntity.ok().body(obj);
	}

	@PostMapping
	public ResponseEntity<Semester> create(@Valid @RequestBody Semester semester) {
		semesterService.create(semester);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PutMapping("/{year}")
	public ResponseEntity<Void> update(@Valid @RequestBody Semester semester,
			@PathVariable Double year) {
		this.semesterService.update(semester, year);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{year}")
	public ResponseEntity<Void> delete(@PathVariable Double year) {
		this.semesterService.delete(year);
		return ResponseEntity.noContent().build();
	}
}

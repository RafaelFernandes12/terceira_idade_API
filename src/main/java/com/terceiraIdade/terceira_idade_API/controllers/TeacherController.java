package com.terceiraIdade.terceira_idade_API.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.terceiraIdade.terceira_idade_API.models.Teacher;
import com.terceiraIdade.terceira_idade_API.services.TeacherService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/teacher")
@Validated
public class TeacherController {
	@Autowired
	private TeacherService teacherService;

	@GetMapping
	public ResponseEntity<List<Teacher>> findAll() {
		List<Teacher> obj = this.teacherService.findAll();
		return ResponseEntity.ok().body(obj);
	}
	@GetMapping("/{id}")
	public ResponseEntity<Teacher> findById(@PathVariable Long id) {
		Teacher obj = this.teacherService.findById(id);
		return ResponseEntity.ok().body(obj);
	}
    @PostMapping
    public ResponseEntity<Teacher> create(@Valid @RequestBody Teacher teacher) {
    	Teacher obj = teacherService.create(teacher);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@Valid @RequestBody Teacher teacher, @PathVariable Long id) {
		this.teacherService.update(teacher);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		this.teacherService.delete(id);
		return ResponseEntity.noContent().build();
	}
}

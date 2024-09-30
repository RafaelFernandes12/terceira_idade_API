package com.terceiraIdade.terceira_idade_API.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.terceiraIdade.terceira_idade_API.models.Local;
import com.terceiraIdade.terceira_idade_API.services.LocalService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/local")
@Validated
public class LocalController {
	@Autowired
	private LocalService LocalService;

	@GetMapping
	public ResponseEntity<List<Local>> findAll() {
		List<Local> obj = this.LocalService.findAll();
		return ResponseEntity.ok().body(obj);
	}
	@GetMapping("/{id}")
	public ResponseEntity<Local> findById(@PathVariable Long id) {
		Local obj = this.LocalService.findById(id);
		return ResponseEntity.ok().body(obj);
	}
    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody Local LocalObj) {
        Local Local = LocalService.create(LocalObj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(Local.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}

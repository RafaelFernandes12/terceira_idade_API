package com.terceiraIdade.terceira_idade_API.controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.terceiraIdade.terceira_idade_API.models.AttendanceSheet;
import com.terceiraIdade.terceira_idade_API.services.AttendanceSheetService;

@RestController
@RequestMapping("/attendanceSheet")
@Validated
public class AttendanceSheetController {

	@Autowired
	private AttendanceSheetService sheetService;

	@GetMapping("/{day}")
	public ResponseEntity<AttendanceSheet> findById(@PathVariable LocalDateTime day) {
		AttendanceSheet AttendanceSheet = sheetService.getAttendance(day);
		return ResponseEntity.ok().body(AttendanceSheet);
	}

	@GetMapping
	public ResponseEntity<List<AttendanceSheet>> findAll() {
		List<AttendanceSheet> AttendanceSheet = sheetService.findAll();
		return ResponseEntity.ok().body(AttendanceSheet);
	}

	@PostMapping
	public ResponseEntity<Void> create(@RequestBody AttendanceSheet sheet) {

		sheetService.createAttendance(sheet);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
}

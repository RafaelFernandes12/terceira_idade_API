package com.terceiraIdade.terceira_idade_API.models;

import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceSheet {

	@Id
	private LocalDateTime day = LocalDateTime.now();

	@ManyToOne
	@JsonIgnore
	private Course course;

	@OneToMany
	@JoinColumn
	private Set<StudentAttendance> studentsAttendances;

}

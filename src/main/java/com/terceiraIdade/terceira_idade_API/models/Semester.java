package com.terceiraIdade.terceira_idade_API.models;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Semester {

	@Id
	private Double year;

	private LocalDateTime start = LocalDateTime.now();

	private LocalDateTime end;

	@PrePersist
	public void prePersist() {
		if (end == null) {
			end = start.plusMonths(6);
		}
	}

	public Semester(Double year, LocalDateTime start, LocalDateTime end) {
		super();
		this.year = year;
		this.start = start;
		this.end = end;
	}
}

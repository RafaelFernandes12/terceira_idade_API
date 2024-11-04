package com.terceiraIdade.terceira_idade_API.utils;

import java.time.LocalDateTime;

import com.terceiraIdade.terceira_idade_API.models.Semester;

public class SemesterObjects {

	public Semester today() {
		return new Semester(2024.1, LocalDateTime.parse("2024-10-17T14:30:00"),
				LocalDateTime.parse("2025-04-17T14:30:00"));
	}

}

package com.terceiraIdade.terceira_idade_API.models.DTO;

import com.terceiraIdade.terceira_idade_API.enums.Type;

import lombok.Data;

@Data
public class CoursesNoStudentDTO {
	private Long id;

	private String name;

	private String img;

	private Type type;
}

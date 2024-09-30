package com.terceiraIdade.terceira_idade_API.dto;

import com.terceiraIdade.terceira_idade_API.enums.Type;
import com.terceiraIdade.terceira_idade_API.models.Teacher;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCourseDto {
	
	private Long id;
	private String name;
	private String img;
	private Type type = Type.EXTENSAO;
	private Teacher teacher;
}

package com.terceiraIdade.terceira_idade_API.dto;

import com.terceiraIdade.terceira_idade_API.enums.Type;
import com.terceiraIdade.terceira_idade_API.models.Teacher;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCourseDto {
	
	private String name;
	private Type type = Type.EXTENSAO;
	private String img;
	private Teacher teacher;
}

package com.terceiraIdade.terceira_idade_API.models.DTO;

import com.terceiraIdade.terceira_idade_API.models.PersonResponsible;
import com.terceiraIdade.terceira_idade_API.models.Docs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentsNoCourseDTO {

	private Long id;

	private String name;

	private String cpf;

	private String birth;

	private String phone;

	private String img;

	private PersonResponsible personResponsible;

	private Docs studentDocs;
}

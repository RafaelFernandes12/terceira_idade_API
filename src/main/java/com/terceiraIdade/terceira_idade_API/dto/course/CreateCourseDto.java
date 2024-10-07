package com.terceiraIdade.terceira_idade_API.dto.course;

import java.util.Objects;

import com.terceiraIdade.terceira_idade_API.enums.Type;
import com.terceiraIdade.terceira_idade_API.models.Teacher;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCourseDto {

	
	@NotBlank(message = "O nome não pode estar vazio")
	private String name;
	@Builder.Default
	private Type type = Type.EXTENSAO;
	private String img;
	private Teacher teacher;

	@Override
	public int hashCode() {
		return Objects.hash(img, name, teacher, type);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CreateCourseDto other = (CreateCourseDto) obj;
		return Objects.equals(name, other.name)
				&& Objects.equals(teacher, other.teacher) && type == other.type;
	}
}

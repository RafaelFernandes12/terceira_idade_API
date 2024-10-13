package com.terceiraIdade.terceira_idade_API.models;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "teacher")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Teacher {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	private String name;

	private String img;

	@OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
	@JsonIgnoreProperties(value = "teacher")
	private Set<Course> courses;

	public Teacher(Long id, @Size(min = 2, max = 100) @NotBlank String name,
			@Size(min = 2, max = 255) String img) {
		super();
		this.id = id;
		this.name = name;
		this.img = img;
	}
}

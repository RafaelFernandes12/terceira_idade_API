package com.terceiraIdade.terceira_idade_API.models;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "teacher")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Teacher {

	@Id
	@Column(name = "id", unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name", length = 100, nullable = false, unique = true)
	@Size(min = 2, max = 100)
	@NotBlank
	private String name;

	@Column(name = "img", length = 255, nullable = false)
	@Size(min = 2, max = 255)
	private String img;

	@OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<Course> courses;

	public Teacher(Long id, @Size(min = 2, max = 100) @NotBlank String name, @Size(min = 2, max = 255) String img) {
		super();
		this.id = id;
		this.name = name;
		this.img = img;
	}
}

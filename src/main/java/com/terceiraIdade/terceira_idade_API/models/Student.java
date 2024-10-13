package com.terceiraIdade.terceira_idade_API.models;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "student")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	private String name;

	@Column(unique = true)
	@Size(min = 11, max = 11, message = "O cpf deve ter 11 caracteres")
	@NotBlank
	private String cpf;

	@NotBlank
	private String birth;

	@NotBlank
	@Size(min = 8, max = 12, message = "O telefone deve ter entre 8 e 12 caracteres")
	private String phone;

	private String img;

	@NotNull
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "personResponsible_id")
	private PersonResponsible personResponsible;

	@NotNull
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "studentDocs_id")
	private StudentDocs studentDocs;

	@ManyToMany(mappedBy = "students", cascade = CascadeType.ALL)
	@JsonIgnoreProperties(value = "students")
	private Set<Course> courses;
}

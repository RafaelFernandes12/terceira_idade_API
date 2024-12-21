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
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
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

	@NotBlank(message  = "O nome não pode estar em branco")
	private String name;

	@Column(unique = true)
	@Size(min = 11, max = 11, message = "O cpf deve ter 11 caracteres")
	@NotBlank(message = "O cpf não pode estar em branco")
	private String cpf;

	@NotBlank(message = "A data de nascimento não pode estar em branco")
	private String birth;

	@NotBlank(message = "O telefone não pode estar em branco")
	@Size(min = 8, max = 12, message = "O telefone deve ter entre 8 e 12 caracteres")
	private String phone;

	private String img;

	@Valid
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "personResponsible_id")
	private PersonResponsible personResponsible;

	@Valid
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "docs_id")
	private Docs docs;

	@ManyToMany(mappedBy = "students", cascade = CascadeType.ALL)
	@JsonIgnoreProperties(value = "students")
	private Set<Course> courses;

}

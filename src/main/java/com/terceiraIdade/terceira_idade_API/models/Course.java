package com.terceiraIdade.terceira_idade_API.models;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.terceiraIdade.terceira_idade_API.enums.Type;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "course")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Course {

	@Id
	@Column(unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	@NotBlank(message = "O nome não pode estar em branco")
	private String name;

	private String img;

	@Column(nullable = false)
	@Builder.Default
	private Type type = Type.EXTENSAO;

	@ManyToOne
	@JoinColumn(name = "teacher_id")
	@JsonIgnoreProperties(value = "courses")
	private Teacher teacher;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "student_course", joinColumns = @JoinColumn(name = "course_id"), inverseJoinColumns = @JoinColumn(name = "student_id"))
	@JsonIgnoreProperties(value = "courses")
	private Set<Student> students;
}

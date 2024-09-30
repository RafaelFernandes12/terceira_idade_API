package com.terceiraIdade.terceira_idade_API.models;

import com.terceiraIdade.terceira_idade_API.enums.Type;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "course")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Course {

	@Id
	@Column(name = "id", unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", length = 100, nullable = false)
	@Size(min = 2, max = 100)
	@NotBlank
	private String name;

	@Column(name = "img", length = 100)
    @Size(min = 2, max = 100)
	private String img;

	@Column(name = "type", length = 100, nullable = false)
	@NotBlank
	private Type type;

	@ManyToOne
	@JoinColumn(name = "teacher_id")
	private Teacher teacher;

//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "course", cascade = CascadeType.ALL)
//	private Set<Local> local = new HashSet<>();
}

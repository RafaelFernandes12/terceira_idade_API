package com.terceiraIdade.terceira_idade_API.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Docs {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	private String vaccineImg;

	@NotBlank
	private String rgFrontImg;

	@NotBlank
	private String rgBackImg;

	@NotBlank
	private String residenceImg;

	@NotBlank
	private String cardioImg;

	@NotBlank
	private String dermaImg;

	@OneToOne(mappedBy = "docs")
	@JsonIgnore
	private Student student;
}

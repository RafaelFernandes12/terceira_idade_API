package com.terceiraIdade.terceira_idade_API.models;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "student")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Student {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	
	@Column(unique = true)
	private String cpf;
	private String birth;
	private String phone;
	private String img;
	private String vaccineImg;
	private String rgFrontImg;
	private String rgBackImg;
	private String residenceImg;
	private String cardioImg;
	private String dermaImg;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "personResponsible_id")
	private PersonResponsible personResponsible;
	
	@ManyToMany(mappedBy = "students")
	@JsonIgnoreProperties(value = "students")
	private List<Course> courses;
}



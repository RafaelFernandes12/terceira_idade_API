package com.terceiraIdade.terceira_idade_API.models;

import com.terceiraIdade.terceira_idade_API.enums.*;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "local")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Local {

	@Id
	@Column(name = "id", unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private DaysOfWeek day;

	@Enumerated(EnumType.STRING)
	private HoursOfClass hour;

	private String place;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "courses")
	private Course course;

	public Local(DaysOfWeek day, HoursOfClass hour, String place) {
		super();
		this.day = day;
		this.hour = hour;
		this.place = place;
	}

}

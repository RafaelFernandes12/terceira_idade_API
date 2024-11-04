package com.terceiraIdade.terceira_idade_API.models;

import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.terceiraIdade.terceira_idade_API.enums.DaysOfWeek;
import com.terceiraIdade.terceira_idade_API.enums.HoursOfClass;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
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

	@ManyToMany(mappedBy = "locals", cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<Course> course;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Local other = (Local) obj;
		return day == other.day && hour == other.hour && Objects.equals(place, other.place);
	}

	@Override
	public int hashCode() {
		return Objects.hash(day, hour, place);
	}

}

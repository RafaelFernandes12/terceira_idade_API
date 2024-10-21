package com.terceiraIdade.terceira_idade_API.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.terceiraIdade.terceira_idade_API.enums.DaysOfWeek;
import com.terceiraIdade.terceira_idade_API.enums.HoursOfClass;
import com.terceiraIdade.terceira_idade_API.models.Course;
import com.terceiraIdade.terceira_idade_API.models.Local;

public interface LocalRepository extends JpaRepository<Local, Long> {
	List<Local> findByCourse(Course course);

	Optional<Local> findByDayAndHourAndPlace(DaysOfWeek day, HoursOfClass hour, String place);
}

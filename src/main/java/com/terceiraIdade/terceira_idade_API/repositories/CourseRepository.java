package com.terceiraIdade.terceira_idade_API.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.terceiraIdade.terceira_idade_API.enums.DaysOfWeek;
import com.terceiraIdade.terceira_idade_API.enums.HoursOfClass;
import com.terceiraIdade.terceira_idade_API.models.Course;
import com.terceiraIdade.terceira_idade_API.models.Semester;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

	List<Course> findByNameContainingIgnoreCase(String name);

	List<Course> findAllByIsArchived(boolean isArchived);

	@Query("SELECT c FROM Course c JOIN c.locals l WHERE c.semester = :semester AND l.day = :day AND l.hour = :hour AND l.place = :place")
	Optional<Course> findBySemesterAndLocals(@Param("semester") Semester semester,
			@Param("day") DaysOfWeek day, @Param("hour") HoursOfClass hour,
			@Param("place") String place);
}
package com.terceiraIdade.terceira_idade_API.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.terceiraIdade.terceira_idade_API.models.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

	List<Course> findByNameContainingIgnoreCase(String name);

	List<Course> findAllByIsArchived(boolean isArchived);
}
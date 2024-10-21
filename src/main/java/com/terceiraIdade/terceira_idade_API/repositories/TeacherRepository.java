package com.terceiraIdade.terceira_idade_API.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.terceiraIdade.terceira_idade_API.models.Teacher;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

	List<Teacher> findByNameContainingIgnoreCase(String name);
}
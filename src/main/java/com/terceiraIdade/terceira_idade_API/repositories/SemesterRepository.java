package com.terceiraIdade.terceira_idade_API.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.terceiraIdade.terceira_idade_API.models.Semester;

public interface SemesterRepository extends JpaRepository<Semester, Double> {

	Optional<Semester> findByYear(Double year);

}

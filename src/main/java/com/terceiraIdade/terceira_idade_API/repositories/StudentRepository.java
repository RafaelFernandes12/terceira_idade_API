package com.terceiraIdade.terceira_idade_API.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.terceiraIdade.terceira_idade_API.models.Student;

public interface StudentRepository extends JpaRepository<Student, Long>{

}

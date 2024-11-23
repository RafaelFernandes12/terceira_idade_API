package com.terceiraIdade.terceira_idade_API.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.terceiraIdade.terceira_idade_API.models.StudentAttendance;

public interface StudentAttendanceRepository extends JpaRepository<StudentAttendance, Long> {

}

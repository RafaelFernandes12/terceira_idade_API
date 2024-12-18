package com.terceiraIdade.terceira_idade_API.repositories;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;

import com.terceiraIdade.terceira_idade_API.models.AttendanceSheet;

public interface AttendanceSheetRepository extends JpaRepository<AttendanceSheet, LocalDateTime> {

}

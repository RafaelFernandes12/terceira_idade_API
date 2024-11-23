package com.terceiraIdade.terceira_idade_API.models;

import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceSheet {

    @Id
    private LocalDateTime day = LocalDateTime.now();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    @JsonIgnoreProperties(value = { "students", "locals", "teacher", "semester" })
    @NotNull(message = "O curso não pode estar vazio!")
    private Course course;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "attendanceSheet")
    private Set<StudentAttendance> studentsAttendances;
}

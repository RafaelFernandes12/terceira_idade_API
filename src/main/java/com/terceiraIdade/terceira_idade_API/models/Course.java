package com.terceiraIdade.terceira_idade_API.models;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.terceiraIdade.terceira_idade_API.enums.Type;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "course")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Course {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "O nome não pode estar em branco")
  private String name;

  private String img;

  @Builder.Default
  private Type type = Type.EXTENSAO;

  @Builder.Default
  private boolean isArchived = false;

  @Builder.Default
  private int maxStudents = 30;

  @Builder.Default
  private int maxClasses = 1800;

  @ManyToOne
  @JoinColumn
  @JsonIgnoreProperties(value = "courses")
  @Valid
  private Teacher teacher;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "student_course", joinColumns = @JoinColumn(name = "course_id"), inverseJoinColumns = @JoinColumn(name = "student_id"))
  @JsonIgnoreProperties(value = "courses")
  private Set<Student> students;

  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinTable(name = "local_course", joinColumns = @JoinColumn(name = "course_id"), inverseJoinColumns = @JoinColumn(name = "local_id"))
  @Valid
  private Set<Local> locals;

  @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JsonIgnore
  private Set<AttendanceSheet> sheets;

  @ManyToOne
  @JoinColumn
  @Valid
  private Semester semester;

}

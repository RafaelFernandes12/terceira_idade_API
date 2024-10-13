package com.terceiraIdade.terceira_idade_API.utils;

import java.util.HashSet;

import com.terceiraIdade.terceira_idade_API.models.Course;
import com.terceiraIdade.terceira_idade_API.models.Teacher;

public class TeacherObjects {

	public Teacher mario() {
		return Teacher.builder().id(1L).name("mario").img("teacher.png")
				.courses(new HashSet<Course>()).build();
	}

	public Teacher maria() {
		return Teacher.builder().id(2L).name("maria").img("teacher.png")
				.courses(new HashSet<Course>()).build();
	}

}

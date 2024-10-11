package com.terceiraIdade.terceira_idade_API.utils;

import com.terceiraIdade.terceira_idade_API.models.Student;

public class StudentObjects {

	public Student carla() {
		Student carla = Student.builder().id(1L).name("carla").build();
		return carla;
	}
	
}

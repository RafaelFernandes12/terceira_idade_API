package com.terceiraIdade.terceira_idade_API.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.terceiraIdade.terceira_idade_API.enums.Type;
import com.terceiraIdade.terceira_idade_API.models.Course;
import com.terceiraIdade.terceira_idade_API.models.Student;
import com.terceiraIdade.terceira_idade_API.models.Teacher;

public class CourseObjects {

	public Course mathCourseMock() {
		return Course.builder().id(1L).name("Math").type(Type.EXTENSAO).img("math.png")
				.teacher(new Teacher(1L, "John", "img.file")).students(new HashSet<Student>())
				.build();
	}

	public Course scienceCourseMock() {
		return Course.builder().name("Science").type(Type.EXTENSAO).img("science.png")
				.teacher(new Teacher()).students(new HashSet<Student>()).build();
	}

	public Course englishCourseMock() {
		return Course.builder().id(3L).name("English").type(Type.ENSINO).img("english.png")
				.teacher(new Teacher()).students(new HashSet<Student>()).build();
	}

	public List<Course> listOfCoursesMock() {

		List<Course> courses = new ArrayList<Course>();
		courses.add(scienceCourseMock());
		courses.add(englishCourseMock());
		courses.add(mathCourseMock());

		return courses;
	}
}

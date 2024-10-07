package com.terceiraIdade.terceira_idade_API.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.terceiraIdade.terceira_idade_API.dto.course.UpdateCourseDto;
import com.terceiraIdade.terceira_idade_API.exceptions.exceptionsDetails.NotFoundException;
import com.terceiraIdade.terceira_idade_API.models.Course;
import com.terceiraIdade.terceira_idade_API.repositories.CourseRepository;

@Service
public class CourseService {

	@Autowired
	private CourseRepository courseRespository;

	public Course create(Course course) {

		courseRespository.save(course);
		return course;
	}

	public List<Course> findAll() {
		return this.courseRespository.findAll();
	}

	public Course findById(Long id) {
		return this.courseRespository.findById(id)
				.orElseThrow(() -> new NotFoundException("Nenhum curso foi encontrado!"));
	}

	public Course update(UpdateCourseDto course) {

		Course newCourse = Course.builder().id(course.getId()).name(course.getName()).type(course.getType())
				.img(course.getImg()).teacher(course.getTeacher()).build();

		this.courseRespository.delete(findById(course.getId()));

		this.courseRespository.save(newCourse);
		return newCourse;
	}

	public void delete(Long id) {
		Course course = findById(id);
		this.courseRespository.delete(course);
	}

}

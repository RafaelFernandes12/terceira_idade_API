package com.terceiraIdade.terceira_idade_API.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.terceiraIdade.terceira_idade_API.dto.CreateCourseDto;
import com.terceiraIdade.terceira_idade_API.dto.UpdateCourseDto;
import com.terceiraIdade.terceira_idade_API.models.Course;
import com.terceiraIdade.terceira_idade_API.repositories.CourseRepository;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class CourseService {

	@Autowired
	private CourseRepository courseRespository;

	@Transactional
	public Course create(CreateCourseDto course) {
		Course newCourse = Course.builder().name(course.getName()).type(course.getType()).img(course.getImg())
				.teacher(course.getTeacher()).build();

		courseRespository.save(newCourse);
		return newCourse;
	}

	public List<Course> findAll() {
		return this.courseRespository.findAll();
	}

	public Course findById(Long id) {
		Course course = this.courseRespository.findById(id).orElseThrow(() -> new RuntimeException("achei nada"));
		return course;
	}

	public Course update(UpdateCourseDto course) {
		
		Course updatedCourse = findById(course.getId());
		
		updatedCourse.setName(course.getName());
		updatedCourse.setImg(course.getImg());
		updatedCourse.setTeacher(course.getTeacher());
		updatedCourse.setType(course.getType());
		
		this.courseRespository.save(updatedCourse);
		return updatedCourse;
	}
	
	public void delete(Long id) {
		this.courseRespository.deleteById(id);
	}

}

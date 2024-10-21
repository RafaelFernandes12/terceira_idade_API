package com.terceiraIdade.terceira_idade_API.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.terceiraIdade.terceira_idade_API.exceptions.exceptionsDetails.NotFoundException;
import com.terceiraIdade.terceira_idade_API.models.Course;
import com.terceiraIdade.terceira_idade_API.models.Teacher;
import com.terceiraIdade.terceira_idade_API.repositories.CourseRepository;
import com.terceiraIdade.terceira_idade_API.repositories.TeacherRepository;

import jakarta.transaction.Transactional;

@Service
public class TeacherService {

	@Autowired
	private TeacherRepository teacherRepository;
	@Autowired
	private CourseService courseService;
	@Autowired
	private CourseRepository courseRepository;

	@Transactional
	public Teacher create(Teacher teacher) {

		teacher.setId(null);
		Teacher newTeacher = Teacher.builder().name(teacher.getName()).img(teacher.getImg())
				.courses(teacher.getCourses()).build();

		Set<Course> coursesToAdd = new HashSet<>();
		for (Course c : newTeacher.getCourses()) {
			Course foundCourse = this.courseService.findById(c.getId());
			coursesToAdd.add(foundCourse);
		}

		newTeacher.setCourses(coursesToAdd);
		teacherRepository.save(newTeacher);
		for (Course c : coursesToAdd) {
			Course foundCourse = this.courseService.findById(c.getId());
			foundCourse.setTeacher(newTeacher);
			courseRepository.save(foundCourse);
		}
		return newTeacher;

	}

	public List<Teacher> findAll() {
		return this.teacherRepository.findAll();
	}

	public Teacher findById(Long id) {
		return this.teacherRepository.findById(id).orElseThrow(() -> new NotFoundException(
				"Nenhum professor com o id: " + id + " foi encontrado"));
	}

	@Transactional
	public Teacher update(Teacher teacher, Long id) {
		findById(id);

		Teacher newTeacher = Teacher.builder().id(id).name(teacher.getName()).img(teacher.getImg())
				.courses(teacher.getCourses()).build();

		Set<Course> coursesToAdd = new HashSet<>();

		for (Course c : newTeacher.getCourses()) {
			Course foundCourse = this.courseService.findById(c.getId());
			coursesToAdd.add(foundCourse);
		}

		newTeacher.setCourses(coursesToAdd);
		teacherRepository.save(newTeacher);
		for (Course c : coursesToAdd) {
			Course foundCourse = this.courseService.findById(c.getId());
			foundCourse.setTeacher(newTeacher);
			courseRepository.save(foundCourse);
		}
		return newTeacher;
	}

	public void delete(Long id) {
		Teacher teacher = findById(id);
		this.teacherRepository.delete(teacher);
	}

	public List<Teacher> findByName(String name) {
		return teacherRepository.findByNameContainingIgnoreCase(name);
	}

}

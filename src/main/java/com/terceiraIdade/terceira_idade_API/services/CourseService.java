package com.terceiraIdade.terceira_idade_API.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.terceiraIdade.terceira_idade_API.exceptions.exceptionsDetails.NotFoundException;
import com.terceiraIdade.terceira_idade_API.models.Course;
import com.terceiraIdade.terceira_idade_API.models.Student;
import com.terceiraIdade.terceira_idade_API.repositories.CourseRepository;

import jakarta.transaction.Transactional;

@Service
public class CourseService {

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private StudentService studentService;

	@Transactional
	public Course create(Course course) {
		try {
			Course newCourse = Course.builder().img(course.getImg()).name(course.getName())
					.students(course.getStudents()).teacher(course.getTeacher())
					.type(course.getType()).build();

			Set<Student> studentsToAdd = new HashSet<Student>();

			for (Student s : newCourse.getStudents()) {
				Student foundStudent = this.studentService.findById(s.getId());
				studentsToAdd.add(foundStudent);
			}

			newCourse.setStudents(studentsToAdd);
			return courseRepository.save(newCourse);

		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Nome já em uso!");
		}

	}

	public List<Course> findAll() {
		return this.courseRepository.findAll();
	}

	public Course findById(Long id) {
		return this.courseRepository.findById(id).orElseThrow(
				() -> new NotFoundException("Nenhum curso com o id: " + id + " foi encontrado"));
	}

	@Transactional
	public Course update(Course course, Long id) {
		try {
			findById(id);
			Course newCourse = Course.builder().id(id).img(course.getImg()).name(course.getName())
					.students(course.getStudents()).teacher(course.getTeacher())
					.type(course.getType()).build();
			Set<Student> studentsToAdd = new HashSet<Student>();
			for (Student s : newCourse.getStudents()) {
				Student foundStudent = this.studentService.findById(s.getId());
				studentsToAdd.add(foundStudent);
			}
			newCourse.setStudents(studentsToAdd);

			return courseRepository.save(newCourse);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Nome já em uso!");
		}
	}

	public void delete(Long id) {
		Course course = findById(id);
		this.courseRepository.delete(course);
	}

}

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
import com.terceiraIdade.terceira_idade_API.repositories.StudentRepository;

import jakarta.transaction.Transactional;

@Service
public class StudentService {

	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private CourseService courseService;
	@Autowired
	private CourseRepository courseRepository;

	@Transactional
	public Student create(Student student) {
		try {
			student.getPersonResponsible().setId(null);
			student.getStudentDocs().setId(null);
			Student newStudent = Student.builder().birth(student.getBirth())
					.studentDocs(student.getStudentDocs()).cpf(student.getCpf())
					.img(student.getImg()).name(student.getName()).courses(student.getCourses())
					.personResponsible(student.getPersonResponsible()).phone(student.getPhone())
					.build();
			Set<Course> coursesToAdd = new HashSet<>();
			for (Course c : newStudent.getCourses()) {
				Course foundCourse = this.courseService.findById(c.getId());
				coursesToAdd.add(foundCourse);
			}

			newStudent.setCourses(coursesToAdd);
			Student createStudent = studentRepository.save(newStudent);

			for (Course c : coursesToAdd) {
				Course foundCourse = this.courseService.findById(c.getId());
				foundCourse.getStudents().add(newStudent);
				courseRepository.save(foundCourse);
			}
			return createStudent;
		} catch (DataIntegrityViolationException e) {
			if (e.getMessage().contains("cpf"))
				throw new DataIntegrityViolationException("Cpf já está em uso");
			throw new DataIntegrityViolationException(e.getMessage());
		}
	}

	public List<Student> findAll() {
		return this.studentRepository.findAll();
	}

	public Student findById(Long id) {

		return this.studentRepository.findById(id).orElseThrow(() -> new NotFoundException(
				"Nenhum estudante com o id: " + id + " foi encontrado"));
	}

	@Transactional
	public Student update(Student student, Long id) {

		findById(id);
		try {
			student.getPersonResponsible().setId(null);
			student.getStudentDocs().setId(null);
			Student newStudent = Student.builder().birth(student.getBirth())
					.studentDocs(student.getStudentDocs()).cpf(student.getCpf())
					.img(student.getImg()).name(student.getName())
					.personResponsible(student.getPersonResponsible()).phone(student.getPhone())
					.build();

			return studentRepository.save(newStudent);

		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Cpf está em uso");
		}
	}

	public void delete(Long id) {
		Student student = findById(id);
		this.studentRepository.delete(student);
	}
}

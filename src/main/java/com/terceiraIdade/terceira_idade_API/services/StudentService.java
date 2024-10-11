package com.terceiraIdade.terceira_idade_API.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.terceiraIdade.terceira_idade_API.exceptions.exceptionsDetails.NotFoundException;
import com.terceiraIdade.terceira_idade_API.models.Student;
import com.terceiraIdade.terceira_idade_API.repositories.StudentRepository;

import jakarta.transaction.Transactional;

@Service
public class StudentService {

	@Autowired
	private StudentRepository studentRepository;

	@Transactional
	public Student create(Student student) {
		student.getPersonResponsible().setId(null);
		student.getStudentDocs().setId(null);
		Student newStudent = Student.builder().birth(student.getBirth())
				.studentDocs(student.getStudentDocs()).courses(student.getCourses())
				.cpf(student.getCpf()).img(student.getImg()).name(student.getName())
				.personResponsible(student.getPersonResponsible()).phone(student.getPhone())
				.build();

		return studentRepository.save(newStudent);
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

		Student newStudent = Student.builder().birth(student.getBirth())
				.studentDocs(student.getStudentDocs()).courses(student.getCourses())
				.cpf(student.getCpf()).img(student.getImg()).name(student.getName())
				.personResponsible(student.getPersonResponsible()).phone(student.getPhone())
				.build();

		studentRepository.save(newStudent);

		return newStudent;
	}

	public void delete(Long id) {
		Student student = findById(id);
		this.studentRepository.delete(student);
	}
}

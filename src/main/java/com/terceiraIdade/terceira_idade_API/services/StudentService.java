package com.terceiraIdade.terceira_idade_API.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.terceiraIdade.terceira_idade_API.exceptions.exceptionsDetails.BadRequestException;
import com.terceiraIdade.terceira_idade_API.exceptions.exceptionsDetails.ForbiddenException;
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
			Student newStudent = Student.builder().birth(student.getBirth()).docs(student.getDocs())
					.cpf(student.getCpf()).img(student.getImg()).name(student.getName())
					.courses(student.getCourses()).personResponsible(student.getPersonResponsible())
					.phone(student.getPhone()).build();
			Set<Course> coursesToAdd = new HashSet<>();

			for (Course c : newStudent.getCourses()) {
				Course foundCourse = this.courseService.findById(c.getId());

				if (foundCourse.isArchived())
					throw new ForbiddenException(
							"Não é permitido adicionar alunos a um curso arquivado!");

				if (foundCourse.getStudents().size() > foundCourse.getMaxStudents())
					throw new BadRequestException("O curso deve ter menos de "
							+ foundCourse.getMaxStudents() + " estudantes");
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
			if (e.getMessage().contains(student.getCpf()))
				throw new DataIntegrityViolationException("Cpf já está em uso");
			throw new DataIntegrityViolationException(e.getMessage());
		}
	}

	public List<Student> findAll() {
		return studentRepository.findAll();
	}

	public List<Student> findAllByIsArchivedFalse() {
		return studentRepository.findAllByIsArchivedFalse();
	}

	public List<Student> findByName(String name) {
		return studentRepository.findByNameContainingIgnoreCase(name);
	}

	public List<Student> findByCpf(String cpf) {
		return studentRepository.findByCpf(cpf);
	}

	public Student findById(Long id) {
		return studentRepository.findById(id).orElseThrow(() -> new NotFoundException(
				"Nenhum estudante com o id: " + id + " foi encontrado"));
	}

	@Transactional
	public Student update(Student student, Long id) {
		Student existingStudent = findById(id);
		existingStudent.getCourses().forEach(c -> {
			var course = courseRepository.findById(c.getId()).orElse(null);
			if (!course.isArchived()) {
				courseRepository.delete(course);
			}
		});
		try {
			Student newStudent = Student.builder().id(id).birth(student.getBirth())
					.docs(student.getDocs()).cpf(student.getCpf()).img(student.getImg())
					.name(student.getName()).courses(student.getCourses())
					.personResponsible(student.getPersonResponsible()).phone(student.getPhone())
					.build();

			Set<Course> coursesToAdd = new HashSet<>();

			for (Course c : newStudent.getCourses()) {
				Course foundCourse = this.courseService.findById(c.getId());

				if (foundCourse.isArchived())
					throw new ForbiddenException(
							"Não é permitido adicionar alunos a um curso arquivado!");

				if (foundCourse.getStudents().size() > foundCourse.getMaxStudents())
					throw new BadRequestException("O curso deve ter menos de "
							+ foundCourse.getMaxStudents() + " estudantes");
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
			throw new DataIntegrityViolationException("Cpf está em uso");
		}
	}

	public void delete(Long id) {
		Student student = findById(id);

		Student savedStudent = null;
		for (Course c : student.getCourses()) {
			if (c.isArchived()) {
				student.setArchived(true);
				savedStudent = studentRepository.save(student);
			}
		}

		if (savedStudent == null)
			studentRepository.delete(student);
	}

}

package com.terceiraIdade.terceira_idade_API.services;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.terceiraIdade.terceira_idade_API.exceptions.exceptionsDetails.BadRequestException;
import com.terceiraIdade.terceira_idade_API.exceptions.exceptionsDetails.ForbiddenException;
import com.terceiraIdade.terceira_idade_API.exceptions.exceptionsDetails.NotFoundException;
import com.terceiraIdade.terceira_idade_API.models.Course;
import com.terceiraIdade.terceira_idade_API.models.Local;
import com.terceiraIdade.terceira_idade_API.models.Student;
import com.terceiraIdade.terceira_idade_API.repositories.CourseRepository;
import com.terceiraIdade.terceira_idade_API.repositories.LocalRepository;

import jakarta.transaction.Transactional;

@Service
public class CourseService {

	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private LocalRepository localRepository;

	@Autowired
	private StudentService studentService;
	@Autowired
	private SemesterService semesterService;

	@Transactional
	public Course create(Course course) {
		try {

			throwExceptions(course, course, "criar");
			addStudents(course);

			loopLocal(course);
			findSemester(course);
			Course courseCreate = courseRepository.save(course);

			course.getLocal().forEach(l -> l.setCourse(courseCreate));

			return courseCreate;

		} catch (DataIntegrityViolationException e) {
			if (e.getMessage().contains(course.getName()))
				throw new DataIntegrityViolationException("Nome já em uso!");
			else
				throw new DataIntegrityViolationException(e.getMessage());
		}

	}

	public List<Course> findAll() {
		return this.courseRepository.findAll();
	}

	public List<Course> findAllByIsArchived(boolean isArchived) {
		return this.courseRepository.findAllByIsArchived(isArchived);
	}

	public Course findById(Long id) {
		return this.courseRepository.findById(id).orElseThrow(
				() -> new NotFoundException("Nenhum curso com o id: " + id + " foi encontrado"));
	}

	@Transactional
	public Course update(Course course, Long id) {
		try {
			Course existingCourse = findById(id);

			Course newCourse = Course.builder().id(id).img(course.getImg()).name(course.getName())
					.students(course.getStudents()).teacher(course.getTeacher())
					.semester(course.getSemester()).local(course.getLocal())
					.maxStudents(course.getMaxStudents()).type(course.getType()).build();

			throwExceptions(existingCourse, newCourse, "atualizar");
			addStudents(newCourse);

			localRepository.findByCourse(newCourse).forEach(l -> localRepository.delete(l));
			newCourse.getLocal().forEach(l -> l.setCourse(newCourse));

			loopLocal(newCourse);

			findSemester(newCourse);

			return courseRepository.save(newCourse);

		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Nome já em uso!");
		}
	}

	public void delete(Long id) {
		Course existingCourse = findById(id);
		throwExceptions(existingCourse, existingCourse, "deletar");
		this.courseRepository.delete(existingCourse);
	}

	public List<Course> findByName(String name) {
		return courseRepository.findByNameContainingIgnoreCase(name);
	}

	public Course archiveCourse(Long id) {
		Course archiveCourse = findById(id);
		archiveCourse.setArchived(!archiveCourse.isArchived());
		return courseRepository.save(archiveCourse);
	}

	private void throwExceptions(Course existingCourse, Course course, String message) {
		if (existingCourse.isArchived())
			throw new ForbiddenException(
					"Não é permitido " + message + " um curso que está arquivado");

		if (course.getStudents().size() > course.getMaxStudents())
			throw new BadRequestException(
					"O curso deve ter menos de " + course.getMaxStudents() + " estudantes");
	}

	private void addStudents(Course course) {
		Set<Student> studentsToAdd = new HashSet<>();

		course.getStudents().forEach(s -> {
			Student foundStudent = this.studentService.findById(s.getId());
			studentsToAdd.add(foundStudent);
		});
		course.setStudents(studentsToAdd);
	}

	private void loopLocal(Course course) {
		course.getLocal().forEach(l -> {
			Optional<Local> repeatedLocal = localRepository.findByDayAndHourAndPlace(l.getDay(),
					l.getHour(), l.getPlace());
			if (repeatedLocal.isPresent())
				throw new BadRequestException("O local já está sendo usado");
		});
	}

	private void findSemester(Course course) {
		semesterService.findById(course.getSemester().getYear());
	}
}

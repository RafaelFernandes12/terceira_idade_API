package com.terceiraIdade.terceira_idade_API.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import com.terceiraIdade.terceira_idade_API.exceptions.exceptionsDetails.NotFoundException;
import com.terceiraIdade.terceira_idade_API.models.Course;
import com.terceiraIdade.terceira_idade_API.models.Student;
import com.terceiraIdade.terceira_idade_API.repositories.CourseRepository;
import com.terceiraIdade.terceira_idade_API.repositories.StudentRepository;
import com.terceiraIdade.terceira_idade_API.utils.CourseObjects;
import com.terceiraIdade.terceira_idade_API.utils.StudentObjects;

import lombok.extern.log4j.Log4j2;

@DisplayName("Tests for StudentService")
@Log4j2
class StudentServiceTest {

	@InjectMocks
	private StudentService studentService;
	@InjectMocks
	private StudentObjects studentObjects;

	@InjectMocks
	private CourseObjects courseObjects;

	@Mock
	private StudentRepository studentRepository;
	@Mock
	private CourseRepository courseRepository;
	@Mock
	private CourseService courseService;

	private Student carla;
	private Student joao;
	private Course math;
	private Set<Course> courses = new HashSet<Course>();

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		carla = studentObjects.carla();
		joao = studentObjects.joao();
		math = courseObjects.mathCourseMock();
	}

	@Test
	void save_createStudent_success() {
		when(studentRepository.save(any(Student.class))).thenReturn(carla);
		when(courseService.findById(anyLong())).thenReturn(math);
		when(courseRepository.save(any(Course.class))).thenReturn(math);

		courses.add(math);
		carla.setCourses(courses);

		Student createStudent = studentService.create(carla);

		assertThat(createStudent).isEqualTo(carla);
		assertThat(createStudent.getCourses()).hasSize(1);
		assertThat(createStudent.getCourses()).contains(math);

		verify(studentRepository).save(any(Student.class));
		verify(courseRepository).save(any(Course.class));
		verify(courseService, times(2)).findById(anyLong());

	}

	@Test
	void save_createStudentThrowDataIntegrityViolationException_exception() {
		try {
			when(courseService.findById(anyLong())).thenReturn(math);
			when(courseRepository.save(any(Course.class))).thenReturn(math);
			when(this.studentRepository.save(any(Student.class)))
					.thenThrow(new DataIntegrityViolationException("Cpf está em uso"));
			courses.add(math);
			carla.setCourses(courses);
			this.studentService.create(carla);
		} catch (Exception e) {
			assertThat(e.getClass()).isEqualTo(DataIntegrityViolationException.class);
			assertThat(e.getMessage()).isEqualTo("Cpf está em uso");
		}
	}

	@Test
	void findAll_findAllStudents_success() {
		List<Student> listOfStudent = studentObjects.listOfStudent();
		when(studentRepository.findAll()).thenReturn(listOfStudent);

		List<Student> all = studentService.findAll();
		assertThat(all).isEqualTo(listOfStudent);
	}

	@Test
	void findById_findStudentById_success() {
		when(studentRepository.findById(anyLong())).thenReturn(Optional.of(carla));
		assertThat(studentService.findById(1L)).isEqualTo(carla);
	}

	@Test
	void findById_throwNotFoundExceptionWhenStudentIsNotFound_exception() {
		assertThatThrownBy(() -> this.studentService.findById(10000L))
				.isInstanceOf(NotFoundException.class)
				.hasMessageContaining("Nenhum estudante com o id: " + 10000L + " foi encontrado");
	}

	@Test
	void delete_deleteStudent_success() {
		when(this.studentRepository.findById(carla.getId())).thenReturn(Optional.of(carla));

		studentService.delete(carla.getId());
		verify(this.studentRepository).delete(carla);

	}

	@Test
	void update_updateStudent_sucess() {
		when(this.studentRepository.findById(anyLong())).thenReturn(Optional.of(carla));
		when(this.studentRepository.save(any(Student.class))).thenReturn(joao);
		Student createStudent = studentService.update(joao, carla.getId());

		assertThat(createStudent).isEqualTo(joao);
	}

	@Test
	void update_updateStudentThrowDataIntegrityViolationException_exception() {
		when(this.studentRepository.findById(anyLong())).thenReturn(Optional.of(carla));
		when(this.studentRepository.save(any(Student.class)))
				.thenThrow(DataIntegrityViolationException.class);

		try {
			this.studentService.update(joao, carla.getId());
		} catch (Exception e) {
			assertThat(e.getClass()).isEqualTo(DataIntegrityViolationException.class);
			assertThat(e.getMessage()).isEqualTo("Cpf está em uso");
		}
	}

}

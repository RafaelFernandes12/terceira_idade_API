package com.terceiraIdade.terceira_idade_API.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.terceiraIdade.terceira_idade_API.exceptions.exceptionsDetails.NotFoundException;
import com.terceiraIdade.terceira_idade_API.models.Course;
import com.terceiraIdade.terceira_idade_API.models.Student;
import com.terceiraIdade.terceira_idade_API.repositories.CourseRepository;
import com.terceiraIdade.terceira_idade_API.repositories.StudentRepository;
import com.terceiraIdade.terceira_idade_API.utils.CourseObjects;
import com.terceiraIdade.terceira_idade_API.utils.StudentObjects;

import lombok.extern.log4j.Log4j2;

@DisplayName("Tests for CourseService")
@Log4j2
class CourseServiceTest {

	@InjectMocks
	private CourseService courseService;

	@InjectMocks
	private CourseObjects courseObjects;

	@InjectMocks
	private StudentObjects studentObjects;

	@Mock
	private StudentService studentService;
	@Mock
	private StudentRepository studentRepository;
	@Mock
	private CourseRepository courseRepository;

	private Course mathCourse;
	private Course scienceCourse;
	private Set<Student> students = new HashSet<>();

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		mathCourse = courseObjects.mathCourseMock();
		scienceCourse = courseObjects.scienceCourseMock();
	}

	@Test
	void save_createCourseWithStudents_success() {
		when(studentService.findById(1L)).thenReturn(studentObjects.carla());

		students.add(studentObjects.carla());
		mathCourse.setStudents(students);

		when(courseRepository.save(any(Course.class))).thenReturn(mathCourse);

		Course createdCourse = this.courseService.create(mathCourse);
		Student carla = createdCourse.getStudents().stream().filter(x -> x.getId().equals(1L))
				.findFirst().orElse(null);

		Assertions.assertThat(createdCourse.getName()).isEqualTo("Math");
		Assertions.assertThat(createdCourse.getStudents()).hasSize(1);
		Assertions.assertThat(carla.getName()).isEqualTo("carla");
		Assertions.assertThat(createdCourse.getStudents()).contains(carla);

		verify(courseRepository).save(any(Course.class));
		verify(studentService).findById(1L);
	}

	@Test
	void findById_returnOneCourse_sucess() {
		when(courseRepository.findById(mathCourse.getId())).thenReturn(Optional.of(mathCourse));

		Course actualCourse = courseService.findById(mathCourse.getId());

		Assertions.assertThat(mathCourse.getName()).isEqualTo("Math");
		Assertions.assertThat(mathCourse).isEqualTo(actualCourse);
	}

	@Test
	void findById_throwNotFoundExceptionWhenCourseIsNotFound_exception() {
		Assertions.assertThatThrownBy(() -> this.courseService.findById(10000L))
				.isInstanceOf(NotFoundException.class)
				.hasMessageContaining("Nenhum curso com o id: " + 10000L + " foi encontrado");
	}

	@Test
	void findAll_returnAListOfCourses_success() {
		List<Course> courses = courseObjects.listOfCoursesMock();
		when(courseService.findAll()).thenReturn(courses);

		List<Course> actualCourses = courseService.findAll();

		Assertions.assertThat(actualCourses).isNotEmpty().isEqualTo(courses);
	}

	@Test
	void findAll_returnAnEmptyArray_success() {
		Assertions.assertThat(this.courseService.findAll()).isEmpty();
	}

	@Test
	void delete_removeCourse_success() {

		when(this.courseRepository.findById(mathCourse.getId()))
				.thenReturn(Optional.of(mathCourse));
		courseService.delete(mathCourse.getId());

		verify(courseRepository).delete(mathCourse);
	}

	@Test
	void update_updateCourse_success() {

		when(this.courseRepository.findById(1L)).thenReturn(Optional.of(mathCourse));
		when(this.studentService.findById(1L)).thenReturn(studentObjects.carla());

		students.add(studentObjects.carla());
		scienceCourse.setStudents(students);

		when(courseRepository.save(any(Course.class))).thenReturn(scienceCourse);

		Course createdCourse = this.courseService.update(scienceCourse, 1L);
		Student carla = createdCourse.getStudents().stream().filter(x -> x.getId().equals(1L))
				.findFirst().orElse(null);

		Assertions.assertThat(createdCourse.getName()).isEqualTo("Science");
		Assertions.assertThat(createdCourse.getStudents()).hasSize(1);
		Assertions.assertThat(carla.getName()).isEqualTo("carla");
		Assertions.assertThat(createdCourse.getStudents()).contains(carla);

		verify(courseRepository).save(any(Course.class));
		verify(studentService).findById(1L);

	}

}

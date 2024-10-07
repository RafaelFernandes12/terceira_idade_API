package com.terceiraIdade.terceira_idade_API.services;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.terceiraIdade.terceira_idade_API.exceptions.exceptionsDetails.NotFoundException;
import com.terceiraIdade.terceira_idade_API.models.Course;
import com.terceiraIdade.terceira_idade_API.repositories.CourseRepository;
import com.terceiraIdade.terceira_idade_API.utils.CourseObjects;

import lombok.extern.log4j.Log4j2;

@DisplayName("Tests for CourseService")
@Log4j2
class CourseServiceTest {

	@InjectMocks
	private CourseService courseService;

	@InjectMocks
	private CourseObjects courseObjects;

	@Mock
	private CourseRepository courseRepository;
	
	private Course mathCourse;
	private Course scienceCourse;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		mathCourse = courseObjects.mathCourseMock();
		scienceCourse = courseObjects.scienceCourseMock();
	}

	@Test
	void save_createCourse_success() {
		Course createdCourse = this.courseService.create(mathCourse);
		log.info(createdCourse);
		Assertions.assertThat(createdCourse).isNotNull().isEqualTo(mathCourse);
	}

	@Test
	void findById_returnOneCourse_sucess() {
		when(courseRepository.findById(mathCourse.getId())).thenReturn(Optional.of(mathCourse));

		Course actualCourse = courseService.findById(mathCourse.getId());

		Assertions.assertThat(mathCourse).isEqualTo(actualCourse);
	}

	@Test
	void findById_throwNotFoundExceptionWhenCourseIsNotFound_exception() {
		Assertions.assertThatThrownBy(() -> this.courseService.findById(10000L)).isInstanceOf(NotFoundException.class);
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

		when(this.courseRepository.findById(mathCourse.getId())).thenReturn(Optional.of(mathCourse));
		courseService.delete(mathCourse.getId());

		verify(courseRepository).delete(mathCourse);
	}
	
//	@Test
//	void update_updateCourse_success() {
//		
//		Course mathCourseSaved = this.courseService.create(mathCourse);
//		
//		
//		
//	}

}

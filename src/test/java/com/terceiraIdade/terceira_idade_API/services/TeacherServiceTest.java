package com.terceiraIdade.terceira_idade_API.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
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

import com.terceiraIdade.terceira_idade_API.exceptions.exceptionsDetails.NotFoundException;
import com.terceiraIdade.terceira_idade_API.models.Course;
import com.terceiraIdade.terceira_idade_API.models.Teacher;
import com.terceiraIdade.terceira_idade_API.repositories.CourseRepository;
import com.terceiraIdade.terceira_idade_API.repositories.TeacherRepository;
import com.terceiraIdade.terceira_idade_API.utils.CourseObjects;
import com.terceiraIdade.terceira_idade_API.utils.TeacherObjects;

@DisplayName("tests for TeacherService")
class TeacherServiceTest {

	@InjectMocks
	private TeacherService teacherService;
	@InjectMocks
	private CourseObjects courseObjects;
	@InjectMocks
	private TeacherObjects teacherObjects;

	@Mock
	private CourseService courseService;
	@Mock
	private TeacherRepository teacherRepository;
	@Mock
	private CourseRepository courseRepository;

	private Course math;
	private Course science;
	private Teacher mario;
	private Set<Course> courses = new HashSet<>();

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		math = courseObjects.mathCourseMock();
		science = courseObjects.scienceCourseMock();
		mario = teacherObjects.mario();
	}

//	@Test
//	void save_createTeacherWithCourse_success() {
//		when(courseService.findById(anyLong())).thenReturn(math);
//		when(courseRepository.save(any(Course.class))).thenReturn(math);
//
//		courses.add(math);
//		mario.setCourses(courses);
//
//		when(teacherRepository.save(any(Teacher.class))).thenReturn(mario);
//
//		Teacher createTeacher = teacherService.create(mario);
//
//		assertThat(createTeacher).isEqualTo(mario);
//		assertThat(createTeacher.getCourses()).hasSize(1);
//		assertThat(createTeacher.getCourses()).contains(math);
//
//		verify(teacherRepository).save(any(Teacher.class));
//		verify(courseService, times(2)).findById(1L);
//		verify(courseRepository).save(math);
//	}

	@Test
	void findById_returnOneTeacher_sucess() {
		when(teacherRepository.findById(anyLong())).thenReturn(Optional.of(mario));

		Teacher actualTeacher = teacherService.findById(mario.getId());
		assertThat(actualTeacher).isEqualTo(mario);
	}

	@Test
	void findById_throwNotFoundExceptionWhenTeacherIsNotFound_exception() {

		assertThatThrownBy(() -> this.teacherService.findById(10000L))
				.isInstanceOf(NotFoundException.class)
				.hasMessageContaining("Nenhum professor com o id: " + 10000L + " foi encontrado");
	}

	@Test
	void findAll_returnAListOfTeacher_success() {
		List<Teacher> teachers = List.of(mario);
		when(teacherService.findAll()).thenReturn(teachers);

		List<Teacher> actualTeacher = teacherService.findAll();

		assertThat(actualTeacher).isNotEmpty().isEqualTo(teachers);
	}

	@Test
	void delete_removeTeacher_success() {

		when(teacherRepository.findById(anyLong())).thenReturn(Optional.of(mario));
		teacherService.delete(mario.getId());

		verify(teacherRepository).delete(mario);
	}

//	@Test
//	void update_updateTeacher_success() {
//		when(teacherRepository.findById(1L)).thenReturn(Optional.of(mario));
//		when(courseService.findById(math.getId())).thenReturn(math);
//		when(courseService.findById(science.getId())).thenReturn(science);
//		when(courseRepository.save(math)).thenReturn(math);
//		when(courseRepository.save(science)).thenReturn(science);
//
//		Teacher maria = teacherObjects.maria();
//		courses.addAll(List.of(math, science));
//		maria.setCourses(courses);
//
//		when(teacherRepository.save(any(Teacher.class))).thenReturn(maria);
//
//		Teacher createTeacher = teacherService.update(maria, 1L);
//		maria.setId(1L);
//		assertThat(createTeacher).isEqualTo(maria);
//		assertThat(createTeacher.getCourses()).hasSize(2);
//		assertThat(createTeacher.getCourses()).contains(math, science);
//
//		verify(teacherRepository).save(any(Teacher.class));
//		verify(courseService, times(2)).findById(anyLong());
//		verify(courseRepository).save(math);
//		verify(courseRepository).save(science);
//
//	}

}

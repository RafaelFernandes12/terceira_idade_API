package com.terceiraIdade.terceira_idade_API.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import com.terceiraIdade.terceira_idade_API.exceptions.exceptionsDetails.BadRequestException;
import com.terceiraIdade.terceira_idade_API.exceptions.exceptionsDetails.ForbiddenException;
import com.terceiraIdade.terceira_idade_API.exceptions.exceptionsDetails.NotFoundException;
import com.terceiraIdade.terceira_idade_API.models.Course;
import com.terceiraIdade.terceira_idade_API.models.Local;
import com.terceiraIdade.terceira_idade_API.models.Semester;
import com.terceiraIdade.terceira_idade_API.models.Student;
import com.terceiraIdade.terceira_idade_API.repositories.CourseRepository;
import com.terceiraIdade.terceira_idade_API.repositories.LocalRepository;
import com.terceiraIdade.terceira_idade_API.repositories.StudentRepository;
import com.terceiraIdade.terceira_idade_API.utils.CourseObjects;
import com.terceiraIdade.terceira_idade_API.utils.LocalObjects;
import com.terceiraIdade.terceira_idade_API.utils.SemesterObjects;
import com.terceiraIdade.terceira_idade_API.utils.StudentObjects;
import com.terceiraIdade.terceira_idade_API.utils.WhenFunctions;

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
	@InjectMocks
	private SemesterObjects semesterObjects;
	@InjectMocks
	private LocalObjects localObjects;

	@InjectMocks
	private WhenFunctions whenFun;

	@Mock
	private StudentService studentService;
	@Mock
	private StudentRepository studentRepository;
	@Mock
	private CourseRepository courseRepository;
	@Mock
	private LocalRepository localRepository;
	@Mock
	private SemesterService semesterService;

	private Course math;
	private Course science;
	private Course english;
	private Student carla;
	private Student joao;
	private Set<Student> students = new HashSet<>();
	private Set<Local> locals = new HashSet<>();
	private Semester today;
	private Local room1;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		math = courseObjects.mathCourseMock();
		science = courseObjects.scienceCourseMock();
		english = courseObjects.englishCourseMock();
		carla = studentObjects.carla();
		joao = studentObjects.joao();
		today = semesterObjects.today();
		room1 = localObjects.ROOM1();
	}

	@Test
	@DisplayName("It should create a course")
	void save_success() {
		whenFun.findStudents(carla);
		whenFun.saveCourse(math);
		whenFun.loopLocal(null);
		students.add(studentObjects.carla());
		locals.add(room1);
		math.setStudents(students);
		math.setLocals(locals);

		Course createdCourse = this.courseService.create(math);
		Student carla = createdCourse.getStudents().stream().filter(x -> x.getId().equals(1L))
				.findFirst().orElse(null);

		assertThat(createdCourse).isEqualTo(math);
		assertThat(createdCourse.getStudents()).hasSize(1);
		assertThat(createdCourse.getStudents()).contains(carla);
		assertThat(createdCourse.getLocals()).hasSize(1);
		assertThat(createdCourse.getLocals()).contains(room1);

		verify(courseRepository).save(any(Course.class));
		verify(studentService).findById(1L);
	}

//	@Test
//	@DisplayName("It should throw a BadRequestException because the local is already in use")
//	void save_exception() {
//		whenFun.findStudents(null);
//		whenFun.loopLocal(room1);
//		whenFun.saveCourse(math);
//
//		locals.add(room1);
//		math.setLocals(locals);
//
//		assertThatThrownBy(() -> courseService.create(math)).isInstanceOf(BadRequestException.class)
//				.hasMessage("O local já está sendo usado");
//
//	}

	@Test
	void save_createCourseThrowsBadRequestWhenMaxStudentsExceedTheCap_exception() {
		students.add(whenFun.findStudents(carla));
		students.add(whenFun.findStudents(joao));
		whenFun.loopLocal(null);
		whenFun.findSemester(today);

		math.setStudents(students);

		assertThatThrownBy(() -> courseService.create(math)).isInstanceOf(BadRequestException.class)
				.hasMessage("O curso deve ter menos de " + math.getMaxStudents() + " estudantes");
	}

	@Test
	@DisplayName("It should throw data integrity violation exception in create or update methods!")
	void save_ThrowDataIntegrityViolationException() {
		when(this.courseRepository.save(any(Course.class)))
				.thenThrow(new DataIntegrityViolationException("Exceção"));

		assertThatThrownBy(() -> this.courseService.create(math))
				.isInstanceOf(DataIntegrityViolationException.class).hasMessage("Exceção");
	}

	@Test
	void findById_returnOneCourse_sucess() {
		whenFun.findCourse(math);

		Course actualCourse = courseService.findById(math.getId());
		assertThat(actualCourse).isEqualTo(math);
	}

	@Test
	void findById_throwNotFoundExceptionWhenCourseIsNotFound_exception() {

		assertThatThrownBy(() -> this.courseService.findById(10000L))
				.isInstanceOf(NotFoundException.class)
				.hasMessageContaining("Nenhum curso com o id: " + 10000L + " foi encontrado");
	}

	@Test
	void findAll_returnAListOfCourses_success() {
		List<Course> courses = courseObjects.listOfCoursesMock();
		when(courseService.findAll()).thenReturn(courses);

		List<Course> actualCourses = courseService.findAll();

		assertThat(actualCourses).isNotEmpty().isEqualTo(courses);
	}

	@Test
	void findAllByIsArchived_returnListOfCourseIsArchivedTrue_sucess() {
		when(courseRepository.findAllByIsArchived(true)).thenReturn(List.of(english));
		List<Course> courses = courseService.findAllByIsArchived(true);
		assertThat(courses).isEqualTo(List.of(english));
	}

	@Test
	void findAllByIsArchived_returnListOfCourseIsArchivedFalse_sucess() {
		when(courseRepository.findAllByIsArchived(false)).thenReturn(List.of(science, math));
		List<Course> courses = courseService.findAllByIsArchived(false);
		assertThat(courses).isEqualTo(List.of(science, math));
	}

	@Test
	void findByName_returnListOfCourseThatIncludesTheLetterTyped_sucess() {
		when(courseRepository.findByNameContainingIgnoreCase("Scie")).thenReturn(List.of(science));
		List<Course> courses = courseService.findByName("Scie");
		assertThat(courses).isEqualTo(List.of(science));
	}

	@Test
	void delete_removeCourse_success() {

		whenFun.findCourse(math);
		courseService.delete(math.getId());

		verify(courseRepository).delete(math);
	}

	@Test
	void update_updateCourse_success() {

		science.setId(math.getId());

		whenFun.findCourse(math);
		whenFun.findStudents(carla);
		whenFun.findSemester(today);
		whenFun.loopLocal(null);
		students.add(carla);
		locals.add(room1);

		science.setStudents(students);
		// stack overflow error, fix it
		science.setLocals(locals);
		science.setSemester(today);
		whenFun.saveCourse(science);

		Course createdCourse = this.courseService.update(science, math.getId());
		Student carla = createdCourse.getStudents().stream().filter(x -> x.getId().equals(1L))
				.findFirst().orElse(null);

		assertThat(createdCourse).isEqualTo(science);
		assertThat(createdCourse.getStudents()).hasSize(1);
		assertThat(carla.getName()).isEqualTo("carla");
		assertThat(createdCourse.getStudents()).contains(carla);
		log.info(createdCourse);
		verify(courseRepository).save(any(Course.class));
		verify(studentService).findById(1L);

	}

	@Test
	@DisplayName("It should throw an forbidden exception because the user is trying to update an archived course")
	void update_ThrowForbiddenException_exception() {
		whenFun.findCourse(english);
		when(courseRepository.save(any(Course.class))).thenThrow(
				new ForbiddenException("Não é permitido atualizar um curso que está arquivado"));

		assertThatThrownBy(() -> courseService.update(english, 3L))
				.isInstanceOf(ForbiddenException.class)
				.hasMessage("Não é permitido atualizar um curso que está arquivado");
	}

	@Test
	@DisplayName("isArchive is false, it should turn it to true")
	void archiveCourse_true_sucess() {
		whenFun.findCourse(math);
		whenFun.saveCourse(math);
		Course archivedCourse = courseService.archiveCourse(math.getId());

		assertThat(archivedCourse.isArchived()).isEqualTo(true);
	}

	@Test
	@DisplayName("isArchive is true, it should turn it to false")
	void archiveCourse_false_sucess() {
		whenFun.findCourse(english);
		whenFun.saveCourse(english);
		Course archivedCourse = courseService.archiveCourse(english.getId());

		assertThat(archivedCourse.isArchived()).isEqualTo(false);
	}

}

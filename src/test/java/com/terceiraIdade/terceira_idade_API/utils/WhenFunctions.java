package com.terceiraIdade.terceira_idade_API.utils;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.mockito.Mock;

import com.terceiraIdade.terceira_idade_API.enums.DaysOfWeek;
import com.terceiraIdade.terceira_idade_API.enums.HoursOfClass;
import com.terceiraIdade.terceira_idade_API.models.Course;
import com.terceiraIdade.terceira_idade_API.models.Local;
import com.terceiraIdade.terceira_idade_API.models.Semester;
import com.terceiraIdade.terceira_idade_API.models.Student;
import com.terceiraIdade.terceira_idade_API.repositories.CourseRepository;
import com.terceiraIdade.terceira_idade_API.repositories.LocalRepository;
import com.terceiraIdade.terceira_idade_API.services.SemesterService;
import com.terceiraIdade.terceira_idade_API.services.StudentService;

public class WhenFunctions {

	@Mock
	private StudentService studentService;
	@Mock
	private CourseRepository courseRepository;
	@Mock
	private LocalRepository localRepository;
	@Mock
	private SemesterService semesterService;

	public Student findStudents(Student student) {
		when(studentService.findById(anyLong())).thenReturn(student);
		return student;
	}

	public Local loopLocal(Local local) {
		if (local == null)
			return null;
		when(localRepository.findByDayAndHourAndPlace(any(DaysOfWeek.class),
				any(HoursOfClass.class), anyString())).thenReturn(Optional.of(local));
		return local;
	}

	public Semester findSemester(Semester semester) {
		when(semesterService.findById(anyDouble())).thenReturn(semester);
		return semester;
	}

	public Course saveCourse(Course course) {
		when(courseRepository.save(any(Course.class))).thenReturn(course);
		return course;
	}

	public Optional<Course> findCourse(Course course) {
		when(courseRepository.findById(anyLong())).thenReturn(Optional.of(course));
		return Optional.of(course);
	}
}

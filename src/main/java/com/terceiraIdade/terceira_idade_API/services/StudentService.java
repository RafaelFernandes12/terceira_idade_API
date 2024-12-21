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
            student.setId(null);
            Set<Course> coursesToAdd = new HashSet<>();

            addCourses(student, coursesToAdd);
            Student newStudent = studentRepository.save(student);

            saveCourse(newStudent, coursesToAdd);

            return newStudent;

        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException(e.getMessage());
        }
    }

    public List<Student> findAll() {
        return studentRepository.findAll();
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
            Course course = courseService.findById(c.getId());
            if (!course.isArchived())
                course.getStudents().remove(existingStudent);
        });
        try {
            student.setId(id);
            Set<Course> coursesToAdd = new HashSet<>();
student.getId();
            addCourses(student, coursesToAdd);
            saveCourse(student, coursesToAdd);

            return studentRepository.save(student);

        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Cpf está em uso");
        }
    }

    public void delete(Long id) {
        Student student = findById(id);

        if (student.getCourses().size() > 0)
            throw new ForbiddenException(
                    "Não é permitido deletar um aluno que esteja matriculado em um curso!");

        studentRepository.delete(student);
    }

    private void addCourses(Student newStudent, Set<Course> coursesToAdd) {
        for (Course c : newStudent.getCourses()) {
            Course foundCourse = this.courseService.findById(c.getId());

            if (foundCourse.isArchived())
                throw new ForbiddenException(
                        "Não é permitido adicionar alunos a um curso arquivado!");

            if (foundCourse.getStudents().size() > foundCourse.getMaxStudents())
                throw new BadRequestException("O curso deve ter menos de "
                        + foundCourse.getMaxStudents() + " estudantes");
            coursesToAdd.add(foundCourse);
            newStudent.setCourses(coursesToAdd);
        }
    }

    private void saveCourse(Student newStudent, Set<Course> coursesToAdd) {
        for (Course c : coursesToAdd) {
            Course foundCourse = this.courseService.findById(c.getId());

            foundCourse.getStudents().add(newStudent);
            courseRepository.saveAndFlush(foundCourse);
        }
        newStudent.setCourses(coursesToAdd);
        studentRepository.saveAndFlush(newStudent);
    }
}

package com.terceiraIdade.terceira_idade_API.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.terceiraIdade.terceira_idade_API.exceptions.exceptionsDetails.BadRequestException;
import com.terceiraIdade.terceira_idade_API.exceptions.exceptionsDetails.NotFoundException;
import com.terceiraIdade.terceira_idade_API.models.AttendanceSheet;
import com.terceiraIdade.terceira_idade_API.models.Course;
import com.terceiraIdade.terceira_idade_API.models.Student;
import com.terceiraIdade.terceira_idade_API.models.StudentAttendance;
import com.terceiraIdade.terceira_idade_API.repositories.AttendanceSheetRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class AttendanceSheetService {

    @Autowired
    private AttendanceSheetRepository sheetRepository;
    @Autowired
    private CourseService courseService;
    @Autowired
    private StudentService studentService;

    public AttendanceSheet createAttendance(AttendanceSheet sheet) {
        Course course = courseService.findById(sheet.getCourse().getId());

        sheet.setCourse(course);

        for (StudentAttendance s : sheet.getStudentsAttendances()) {
            Student student = studentService.findById(s.getStudent().getId());
            if (!student.getCourses().contains(course)) {
                throw new BadRequestException("Este estudante não está matriculado nesse curso");
            }
            s.setStudent(student);
            s.setAttendanceSheet(sheet);
        }

        return sheetRepository.save(sheet);

    }

    public AttendanceSheet getAttendance(LocalDateTime day) {
        return sheetRepository.findById(day)
                .orElseThrow(() -> new NotFoundException("Chamada não encontrada!"));
    }

    public List<AttendanceSheet> findAll() {
        return sheetRepository.findAll();
    }

}

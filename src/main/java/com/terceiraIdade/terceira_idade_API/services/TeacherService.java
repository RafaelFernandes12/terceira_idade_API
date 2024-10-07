package com.terceiraIdade.terceira_idade_API.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.terceiraIdade.terceira_idade_API.models.Teacher;
import com.terceiraIdade.terceira_idade_API.repositories.TeacherRepository;

import jakarta.transaction.Transactional;

@Service
public class TeacherService {
	
	@Autowired
	private TeacherRepository teacherRepository;
	
	@Transactional
	public Teacher create(Teacher teacher) {
		teacher.setId(null);
		
		teacherRepository.save(teacher);
		return teacher;
	}

	public List<Teacher> findAll() {
		return this.teacherRepository.findAll();
	}
	
	public Teacher findById(Long id) {
		Teacher teacher = this.teacherRepository.findById(id).orElseThrow(() -> new RuntimeException("achei nada"));
		return teacher;
	}
    
    @Transactional
    public Teacher update(Teacher teacher) {
        Teacher newTeacher = findById(teacher.getId());
        this.teacherRepository.delete(newTeacher);
        
        return this.teacherRepository.save(newTeacher);
    }
	public void delete(Long id) {
		Teacher teacher = findById(id);
		this.teacherRepository.delete(teacher);
	}
}

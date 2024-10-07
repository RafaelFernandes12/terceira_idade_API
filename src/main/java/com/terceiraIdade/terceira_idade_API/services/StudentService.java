package com.terceiraIdade.terceira_idade_API.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.terceiraIdade.terceira_idade_API.exceptions.exceptionsDetails.NotFoundException;
import com.terceiraIdade.terceira_idade_API.models.Student;
import com.terceiraIdade.terceira_idade_API.repositories.StudentRepository;

@Service
public class StudentService {

	@Autowired
	private StudentRepository studentRepository;

	public Student create(Student student) {

		studentRepository.save(student);
		return student;
	}

	public List<Student> findAll() {
		return this.studentRepository.findAll();
	}

	public Student findById(Long id) {
		return this.studentRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Nenhum estudante foi encontrado!"));
	}

	public Student update(Student student) {

		Student existingStudent = findById(student.getId());
		
		existingStudent.setName(student.getName());
	    existingStudent.setBirth(student.getBirth());
	    existingStudent.setImg(student.getImg());
	    existingStudent.setCardioImg(student.getCardioImg());
	    existingStudent.setCpf(student.getCpf());
	    existingStudent.setDermaImg(student.getDermaImg());
	    existingStudent.setPhone(student.getPhone());
	    existingStudent.setResidenceImg(student.getResidenceImg());
	    existingStudent.setRgBackImg(student.getRgBackImg());
	    existingStudent.setRgFrontImg(student.getRgFrontImg());

	    if (student.getPersonResponsible() != null) {
	        existingStudent.setPersonResponsible(student.getPersonResponsible());
	    }

	    studentRepository.save(existingStudent);
		
		return existingStudent;
	}

	public void delete(Long id) {
		Student student = findById(id);
		this.studentRepository.delete(student);
	}
}

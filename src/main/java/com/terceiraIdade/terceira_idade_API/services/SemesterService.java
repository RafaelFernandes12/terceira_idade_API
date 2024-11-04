package com.terceiraIdade.terceira_idade_API.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.terceiraIdade.terceira_idade_API.exceptions.exceptionsDetails.BadRequestException;
import com.terceiraIdade.terceira_idade_API.exceptions.exceptionsDetails.NotFoundException;
import com.terceiraIdade.terceira_idade_API.models.Semester;
import com.terceiraIdade.terceira_idade_API.repositories.SemesterRepository;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class SemesterService {

	@Autowired
	private SemesterRepository semesterRepository;

	@Transactional
	public Semester create(Semester semester) {

		repeatedSemester(semester);
		rightTime(semester);
		return semesterRepository.save(semester);

	}

	public List<Semester> findAll() {
		return this.semesterRepository.findAll();
	}

	public Semester findById(Double year) {
		return this.semesterRepository.findById(year).orElseThrow(() -> new NotFoundException(
				"Nenhum semestre com o ano: " + year + " foi encontrado"));
	}

	@Transactional
	public Semester update(Semester semester, Double year) {
		findById(year);
		rightTime(semester);
		repeatedSemester(semester);
		return semesterRepository.save(semester);
	}

	public void delete(Double year) {
		Semester semester = findById(year);
		this.semesterRepository.delete(semester);
	}

	private void rightTime(Semester semester) {
		if (semester.getEnd().isBefore(semester.getStart()))
			throw new BadRequestException(
					"O fim do semestre não pode começar antes do começo do semestre!");
	}

	private void repeatedSemester(Semester semester) {
		Optional<Semester> repeatedSemester = semesterRepository.findByYear(semester.getYear());
		if (repeatedSemester.isPresent())
			throw new DataIntegrityViolationException("Um semestre com o mesmo ano já existe");

	}
}

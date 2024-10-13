package com.terceiraIdade.terceira_idade_API.utils;

import java.util.HashSet;
import java.util.List;

import com.terceiraIdade.terceira_idade_API.models.PersonResponsible;
import com.terceiraIdade.terceira_idade_API.models.Student;
import com.terceiraIdade.terceira_idade_API.models.StudentDocs;

public class StudentObjects {

	public Student carla() {
		Student carla = Student.builder().id(1L).name("carla").cpf("a23d33g5r14")
				.birth("09/08/2004").phone("1236-7890").img(null)
				.personResponsible(
						PersonResponsible.builder().id(5L).name("John").phone("123456789").build())
				.studentDocs(
						StudentDocs.builder().id(5L).vaccineImg("https://example.com/vaccine.jpg")
								.rgFrontImg("https://example.com/rg_front.jpg")
								.rgBackImg("https://example.com/rg_back.jpg")
								.residenceImg("https://example.com/residence.jpg")
								.cardioImg("https://example.com/cardio.jpg")
								.dermaImg("https://example.com/derma.jpg").build())
				.courses(new HashSet<>()).build();

		return carla;
	}

	public Student joao() {
		Student joao = Student.builder().id(2L).name("joao").cpf("a23d33g5r14").birth("09/08/2004")
				.phone("1236-7890").img(null)
				.personResponsible(
						PersonResponsible.builder().id(5L).name("John").phone("123456789").build())
				.studentDocs(
						StudentDocs.builder().id(5L).vaccineImg("https://example.com/vaccine.jpg")
								.rgFrontImg("https://example.com/rg_front.jpg")
								.rgBackImg("https://example.com/rg_back.jpg")
								.residenceImg("https://example.com/residence.jpg")
								.cardioImg("https://example.com/cardio.jpg")
								.dermaImg("https://example.com/derma.jpg").build())
				.courses(new HashSet<>()).build();

		return joao;
	}

	public List<Student> listOfStudent() {
		return List.of(carla(), joao());
	}

}

package com.terceiraIdade.terceira_idade_API.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.terceiraIdade.terceira_idade_API.models.Local;
import com.terceiraIdade.terceira_idade_API.repositories.LocalRepository;

import jakarta.transaction.Transactional;

@Service
public class LocalService {
	
	@Autowired
	private LocalRepository localRepository;
	
	@Transactional
	public Local create(Local LocalObj) {
		LocalObj.setId(null);
		
		localRepository.save(LocalObj);
		return LocalObj;
	}

	public List<Local> findAll() {
		return this.localRepository.findAll();
	}
	
	public Local findById(Long id) {
		Local Local = this.localRepository.findById(id).orElseThrow(() -> new RuntimeException("achei nada"));
		return Local;
	}
    
    @Transactional
    public Local updateCourse(Local obj) {
        Local newLocal = findById(obj.getId());
        newLocal.setCourse(obj.getCourse());
        return this.localRepository.save(newLocal);
    }
}

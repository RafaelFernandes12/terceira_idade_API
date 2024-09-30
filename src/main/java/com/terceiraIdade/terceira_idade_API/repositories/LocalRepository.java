package com.terceiraIdade.terceira_idade_API.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.terceiraIdade.terceira_idade_API.models.Local;

@Repository
public interface LocalRepository extends JpaRepository<Local, Long> {


}
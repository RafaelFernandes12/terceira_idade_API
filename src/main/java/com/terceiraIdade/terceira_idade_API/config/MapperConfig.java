package com.terceiraIdade.terceira_idade_API.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class MapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}

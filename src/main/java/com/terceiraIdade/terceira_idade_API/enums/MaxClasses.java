package com.terceiraIdade.terceira_idade_API.enums;

import com.terceiraIdade.terceira_idade_API.exceptions.exceptionsDetails.BadRequestException;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum MaxClasses {

	THIRTY(30), SIXTY(60), NINETY(90);

	private int time;

	public static Object fromTime(int time) {
		for (MaxClasses classes : MaxClasses.values()) {
			if (classes.getTime() == time)
				return time;
		}
		return new BadRequestException("Não existe essa opção de horario!");

	}

}

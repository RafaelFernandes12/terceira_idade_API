package com.terceiraIdade.terceira_idade_API.utils;

import com.terceiraIdade.terceira_idade_API.enums.DaysOfWeek;
import com.terceiraIdade.terceira_idade_API.enums.HoursOfClass;
import com.terceiraIdade.terceira_idade_API.models.Local;

public class LocalObjects {
	public Local ROOM1() {
		return Local.builder().id(1L).day(DaysOfWeek.FRIDAY).hour(HoursOfClass.H07_00_TO_08_00)
				.place("ROOM1").build();
	}
}

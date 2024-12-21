package com.terceiraIdade.terceira_idade_API.enums;

import lombok.Getter;

@Getter
public enum HoursOfClass {
	H07_00_TO_08_00(1, "07:00_08:00"), H08_00_TO_09_00(2, "08:00-09:00"),
	H09_00_TO_10_00(3, "09:00-10:00");

	private int code;
	private String description;

	HoursOfClass(int code, String description) {
		this.code = code;
		this.description = description;
	}

}

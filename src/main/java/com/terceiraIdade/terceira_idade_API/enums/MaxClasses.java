package com.terceiraIdade.terceira_idade_API.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum MaxClasses {

    THIRTY(30), SIXTY(60), NINETY(90);

    private int time;

    public static MaxClasses fromValue(int value) {
        for (MaxClasses maxClass : MaxClasses.values()) {
            if (maxClass.getTime() == value) {
                return maxClass;
            }
        }
        throw new IllegalArgumentException("Unexpected value: " + value);
    }

}

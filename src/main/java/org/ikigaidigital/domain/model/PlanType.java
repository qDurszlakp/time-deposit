package org.ikigaidigital.domain.model;

import java.util.Arrays;

public enum PlanType {
    BASIC("basic"),
    STUDENT("student"),
    PREMIUM("premium");

    private final String code;

    PlanType(String code) {
        this.code = code;
    }

    public static PlanType fromCode(String code) {
        if (code == null) {
            throw new IllegalArgumentException("Plan type code cannot be null");
        }
        return Arrays.stream(values())
                .filter(plan -> plan.code.equalsIgnoreCase(code.trim()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown plan type: " + code));
    }
}

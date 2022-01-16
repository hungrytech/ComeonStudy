package com.comeon.study.member.domain.role;

public enum Role {

    ADMIN("ROLE_ADMIN"),
    ACTIVITY("ROLE_ACTIVITY"),
    REST("ROLE_REST");

    private final String value;

    Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

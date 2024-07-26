package com.mrm.app.constants;

public enum Roles {

    USER("USER"),
    ADMIN("USER,ADMIN");

    private final String value;

    Roles(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}

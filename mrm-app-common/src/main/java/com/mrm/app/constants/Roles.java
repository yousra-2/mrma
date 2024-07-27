package com.mrm.app.constants;

public enum Roles {

    USER("USER"),
    DOCTOR("DOCTOR"),
    HR("HR");

    private final String value;

    Roles(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static Roles fromRaw(String raw) {
        for (Roles value : values()) {
            if (value.value.equals(raw)) {
                return value;
            }
        }
        return USER;
    }
}

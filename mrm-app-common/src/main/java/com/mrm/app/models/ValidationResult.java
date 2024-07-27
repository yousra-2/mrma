package com.mrm.app.models;

import java.util.List;

public class ValidationResult {
    private final boolean valid;
    private final List<String> failures;

    public ValidationResult(boolean valid, List<String> failures) {
        this.valid = valid;
        this.failures = failures;
    }

    public boolean isValid() {
        return valid;
    }

    public List<String> getFailures() {
        return failures;
    }
}

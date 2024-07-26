package com.mrm.app.services.auth.models;

import java.time.Duration;
import java.time.LocalDateTime;

public class Token {

    private final String value;
    private final LocalDateTime issuingDate;
    private final LocalDateTime expirationDate;

    public Token(String value, LocalDateTime issuingDate, LocalDateTime expirationDate) {
        this.value = value;
        this.issuingDate = issuingDate;
        this.expirationDate = expirationDate;
    }

    public String getAccessToken() {
        return value;
    }

    public LocalDateTime getExpiresAt() {
        return expirationDate;
    }

    public Duration getExpiresIn() {
        return Duration.between(issuingDate, expirationDate);
    }
}

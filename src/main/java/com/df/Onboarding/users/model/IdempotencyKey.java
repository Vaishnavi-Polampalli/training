package com.df.Onboarding.users.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class IdempotencyKey {
    @Id
    public String key;
    public String response;
    public LocalDateTime expiryDate;

    public IdempotencyKey() {
    }

    public IdempotencyKey(String key, String response, LocalDateTime expiryDate) {
        this.key = key;
        this.response = response;
        this.expiryDate = expiryDate;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }
}
package com.example.kalaa;

import java.sql.Timestamp;

/**
 * Model class representing an email verification OTP record.
 */
public class EmailVerification {
    private int id;
    private String email;
    private String code;
    private Timestamp expiryTime;
    private boolean isUsed;
    private Timestamp createdAt;

    public EmailVerification() {
    }

    public EmailVerification(int id, String email, String code, Timestamp expiryTime, boolean isUsed) {
        this.id = id;
        this.email = email;
        this.code = code;
        this.expiryTime = expiryTime;
        this.isUsed = isUsed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Timestamp getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(Timestamp expiryTime) {
        this.expiryTime = expiryTime;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isExpired() {
        return expiryTime != null && expiryTime.before(new Timestamp(System.currentTimeMillis()));
    }
}

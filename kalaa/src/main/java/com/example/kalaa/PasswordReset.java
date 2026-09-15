package com.example.kalaa;

import java.sql.Timestamp;

/**
 * Model class for password_resets table.
 */
public class PasswordReset {
    private int id;
    private String email;
    private String token;
    private Timestamp expiryTime;
    private boolean isUsed;

    public PasswordReset() {
    }

    public PasswordReset(int id, String email, String token, Timestamp expiryTime, boolean isUsed) {
        this.id = id;
        this.email = email;
        this.token = token;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public boolean isExpired() {
        return expiryTime != null && expiryTime.before(new Timestamp(System.currentTimeMillis()));
    }
}

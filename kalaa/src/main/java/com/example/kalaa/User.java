package com.example.kalaa;

public class User {
    private int id;
    private String username;
    private String email;
    private String password;
    private boolean isVerified;

    public User(int id, String username, String email, String password, boolean isVerified) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.isVerified = isVerified;
    }

    public User(int id, String username, String email, String password) {
        this(id, username, email, password, false);
    }

    public User(String username, String email, String password) {
        this(0, username, email, password, false);
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public boolean isVerified() { return isVerified; }
    public void setVerified(boolean verified) { this.isVerified = verified; }
}
package com.example.kalaa;

public class Author {
    private int authorId;
    private String name;
    private String email;
    private String bio;

    public Author() {}

    public Author(String name, String email, String bio) {
        this.name = name;
        this.email = email;
        this.bio = bio;
    }

    public Author(int authorId, String name, String email, String bio) {
        this.authorId = authorId;
        this.name = name;
        this.email = email;
        this.bio = bio;
    }

    public int getAuthorId() { return authorId; }
    public void setAuthorId(int authorId) { this.authorId = authorId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
}



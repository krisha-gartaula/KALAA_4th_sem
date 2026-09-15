package com.example.kalaa;

public class Story {
    private int storyId;
    private String title;
    private int authorId;
    private String authorName;
    private String category;
    private String content;
    private String imagePath;

    public Story() {}
    public Story(String title, int authorId, String category) {
        this.title = title;
        this.authorId = authorId;
        this.category = category;
    }
    public Story(String title, int authorId, String category, String content) {
        this.title = title;
        this.authorId = authorId;
        this.category = category;
        this.content = content;
    }
    public Story(int storyId, String title, int authorId, String category) {
        this.storyId = storyId;
        this.title = title;
        this.authorId = authorId;
        this.category = category;
    }
    public Story(int storyId, String title, int authorId, String category, String content) {
        this.storyId = storyId;
        this.title = title;
        this.authorId = authorId;
        this.category = category;
        this.content = content;
    }

    public Story(int storyId, String title, int authorId, String authorName, String category, String content) {
        this.storyId = storyId;
        this.title = title;
        this.authorId = authorId;
        this.authorName = authorName;
        this.category = category;
        this.content = content;
    }

    public int getStoryId() { return storyId; }
    public void setStoryId(int storyId) { this.storyId = storyId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public int getAuthorId() { return authorId; }
    public void setAuthorId(int authorId) { this.authorId = authorId; }
    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
}
package com.example.kalaa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StoryDAO {
    private final String jdbcURL = "jdbc:mysql://localhost:3306/kalaa?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private final String dbUser = "root";
    private final String dbPassword = ""; // Replace with your actual password

    public StoryDAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ignored) {}
    }

    public void addStory(Story story) throws SQLException {
        String sql = "INSERT INTO stories (title, author_id, category, content, image_path) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, story.getTitle());
            stmt.setInt(2, story.getAuthorId());
            stmt.setInt(3, Integer.parseInt(story.getCategory()));
            stmt.setString(4, story.getContent());
            stmt.setString(5, story.getImagePath());
            stmt.executeUpdate();
        }
    }

    public List<Story> getAllStories() throws SQLException {
        List<Story> stories = new ArrayList<>();
        String sql = "SELECT s.*, u.username as author_name, c.name as category_name " +
                    "FROM stories s " +
                    "LEFT JOIN user u ON s.author_id = u.id " +
                    "LEFT JOIN categories c ON s.category = c.category_id " +
                    "ORDER BY s.story_id DESC";
        try (Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                stories.add(mapStory(rs));
            }
        }
        return stories;
    }

    public int getStoryCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS cnt FROM stories";
        try (Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("cnt");
            }
        }
        return 0;
    }

    public List<Story> getStoriesByCategoryIdForAdmin(int categoryId) throws SQLException {
        List<Story> stories = new ArrayList<>();
        String sql = "SELECT s.*, a.name as author_name, c.name as category_name " +
                     "FROM stories s " +
                     "LEFT JOIN authors a ON s.author_id = a.author_id " +
                     "LEFT JOIN categories c ON s.category = c.category_id " +
                     "WHERE s.category = ? " +
                     "ORDER BY s.story_id DESC";
        try (Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, categoryId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    stories.add(mapStory(rs));
                }
            }
        }
        return stories;
    }

    public List<Story> getStoriesByCategoryId(int categoryId) throws SQLException {
        List<Story> stories = new ArrayList<>();
        String sql = "SELECT s.*, a.name as author_name, c.name as category_name " +
                     "FROM stories s " +
                     "LEFT JOIN authors a ON s.author_id = a.author_id " +
                     "LEFT JOIN categories c ON s.category = c.category_id " +
                     "WHERE s.category = ? " +
                     "ORDER BY s.story_id DESC";
        try (Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, categoryId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    stories.add(mapStory(rs));
                }
            }
        }
        return stories;
    }

    public Story getStoryById(int storyId) throws SQLException {
        String sql = "SELECT s.*, u.username as author_name, c.name as category_name " +
                    "FROM stories s " +
                    "LEFT JOIN user u ON s.author_id = u.id " +
                    "LEFT JOIN categories c ON s.category = c.category_id " +
                    "WHERE s.story_id = ?";
        try (Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, storyId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapStory(rs);
            }
        }
        return null;
    }

    public void deleteStory(int storyId) throws SQLException {
        String sql = "DELETE FROM stories WHERE story_id = ?";
        try (Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, storyId);
            stmt.executeUpdate();
        }
    }

    public void updateStory(Story story) throws SQLException {
        String sql = "UPDATE stories SET title = ?, author_id = ?, category = ?, content = ?, image_path = ? WHERE story_id = ?";
        try (Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, story.getTitle());
            stmt.setInt(2, story.getAuthorId());
            stmt.setInt(3, Integer.parseInt(story.getCategory()));
            stmt.setString(4, story.getContent());
            stmt.setString(5, story.getImagePath());
            stmt.setInt(6, story.getStoryId());
            stmt.executeUpdate();
        }
    }

    private Story mapStory(ResultSet rs) throws SQLException {
        Story story = new Story(
                rs.getInt("story_id"),
                rs.getString("title"),
                rs.getInt("author_id"),
                rs.getString("author_name"),
                rs.getString("category_name"),
                rs.getString("content")
        );
        story.setImagePath(rs.getString("image_path"));
        return story;
    }
}

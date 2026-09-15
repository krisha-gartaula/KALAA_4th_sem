package com.example.kalaa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
    private final String jdbcURL = "jdbc:mysql://localhost:3306/kalaa?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private final String dbUser = "root";
    private final String dbPassword = ""; // Replace with your actual password

    public CategoryDAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ignored) {}
    }

    public void addCategory(Category category) throws SQLException {
        String sql = "INSERT INTO categories (name, description) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, category.getName());
            stmt.setString(2, category.getDescription());
            stmt.executeUpdate();
        }
    }

    public List<Category> getAllCategories() throws SQLException {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM categories ORDER BY name";
        try (Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int categoryId = rs.getInt("category_id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                categories.add(new Category(categoryId, name, description));
            }
        }
        return categories;
    }

    public int getCategoryCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS cnt FROM categories";
        try (Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("cnt");
            }
        }
        return 0;
    }

    public Category getCategoryById(int categoryId) throws SQLException {
        String sql = "SELECT * FROM categories WHERE category_id = ?";
        try (Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, categoryId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("category_id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                return new Category(id, name, description);
            }
        }
        return null;
    }

    public void deleteCategory(int categoryId) throws SQLException {
        String sql = "DELETE FROM categories WHERE category_id = ?";
        try (Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, categoryId);
            stmt.executeUpdate();
        }
    }

    public void updateCategory(Category category) throws SQLException {
        String sql = "UPDATE categories SET name = ?, description = ? WHERE category_id = ?";
        try (Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, category.getName());
            stmt.setString(2, category.getDescription());
            stmt.setInt(3, category.getCategoryId());
            stmt.executeUpdate();
        }
    }
}

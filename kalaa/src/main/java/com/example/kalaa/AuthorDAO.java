package com.example.kalaa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorDAO {
    private final String jdbcURL = "jdbc:mysql://localhost:3306/kalaa?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private final String dbUser = "root";
    private final String dbPassword = "";

    public AuthorDAO() {
        try { Class.forName("com.mysql.cj.jdbc.Driver"); } catch (ClassNotFoundException ignored) {}
    }

    public void addAuthor(Author author) throws SQLException {
        String sql = "INSERT INTO authors (name, email, bio) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, author.getName());
            ps.setString(2, author.getEmail());
            ps.setString(3, author.getBio());
            ps.executeUpdate();
        }
    }

    public List<Author> getAllAuthors() throws SQLException {
        List<Author> list = new ArrayList<>();
        String sql = "SELECT * FROM authors ORDER BY author_id";
        try (Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Author(
                        rs.getInt("author_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("bio")
                ));
            }
        }
        return list;
    }

    public int getAuthorCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS cnt FROM authors";
        try (Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("cnt");
            }
        }
        return 0;
    }

    public void updateAuthor(Author author) throws SQLException {
        String sql = "UPDATE authors SET name = ?, email = ?, bio = ? WHERE author_id = ?";
        try (Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, author.getName());
            ps.setString(2, author.getEmail());
            ps.setString(3, author.getBio());
            ps.setInt(4, author.getAuthorId());
            ps.executeUpdate();
        }
    }

    public void deleteAuthor(int authorId) throws SQLException {
        String sql = "DELETE FROM authors WHERE author_id = ?";
        try (Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, authorId);
            ps.executeUpdate();
        }
    }
}



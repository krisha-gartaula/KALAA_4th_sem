package com.example.kalaa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private final String jdbcURL = "jdbc:mysql://localhost:3306/kalaa?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private final String dbUser = "root";
    private final String dbPassword = ""; // Replace with your actual password

    public UserDAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ignored) {
            // Driver auto-registration should work, but we try explicitly for compatibility
        }
    }

    public void addUser(User user) throws SQLException {
        String passwordToStore = user.getPassword();
        if (!PasswordUtil.isBCryptHash(passwordToStore)) {
            passwordToStore = PasswordUtil.hashPassword(passwordToStore);
        }
        String sql = "INSERT INTO user (username, email, password, is_verified) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, passwordToStore);
            stmt.setBoolean(4, user.isVerified());
            stmt.executeUpdate();
        }
    }

    public User getUserByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM user WHERE email = ?";
        try (Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String username = rs.getString("username");
                    String userEmail = rs.getString("email");
                    String userPassword = rs.getString("password");
                    boolean isVerified = rs.getBoolean("is_verified");
                    return new User(id, username, userEmail, userPassword, isVerified);
                } else {
                    return null;
                }
            }
        }
    }

    public User getUserByEmailAndPassword(String email, String password) throws SQLException {
        User user = getUserByEmail(email);
        if (user != null && PasswordUtil.checkPassword(password, user.getPassword())) {
            // Transparently upgrade legacy plain-text password to BCrypt hash
            if (PasswordUtil.needsRehash(user.getPassword())) {
                String newHash = PasswordUtil.hashPassword(password);
                updateUserPassword(user.getId(), newHash);
                user.setPassword(newHash);
            }
            return user;
        }
        return null;
    }

    public void updateUserPassword(int userId, String newPassword) throws SQLException {
        String passwordToStore = newPassword;
        if (!PasswordUtil.isBCryptHash(passwordToStore)) {
            passwordToStore = PasswordUtil.hashPassword(passwordToStore);
        }
        String sql = "UPDATE user SET password = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, passwordToStore);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        }
    }

    public void updatePasswordByEmail(String email, String newPassword) throws SQLException {
        String passwordToStore = newPassword;
        if (!PasswordUtil.isBCryptHash(passwordToStore)) {
            passwordToStore = PasswordUtil.hashPassword(passwordToStore);
        }
        String sql = "UPDATE user SET password = ? WHERE email = ?";
        try (Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, passwordToStore);
            stmt.setString(2, email);
            stmt.executeUpdate();
        }
    }

    public void deleteUser(int id) throws SQLException {
        String sql = "DELETE FROM user WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public User getAdminByUsernameAndPassword(String username, String password) throws SQLException {
        User admin = getAdminByUsername(username);
        if (admin != null && PasswordUtil.checkPassword(password, admin.getPassword())) {
            // Transparently upgrade legacy plain-text password to BCrypt hash
            if (PasswordUtil.needsRehash(admin.getPassword())) {
                String newHash = PasswordUtil.hashPassword(password);
                updateAdminPassword(admin.getId(), newHash);
                admin.setPassword(newHash);
            }
            return admin;
        }
        return null;
    }

    public User getAdminByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM user WHERE username = ? AND role = 'admin'";
        try (Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String userUsername = rs.getString("username");
                String email = rs.getString("email");
                String userPassword = rs.getString("password");
                boolean isVerified = rs.getBoolean("is_verified");
                return new User(id, userUsername, email, userPassword, isVerified);
            } else {
                return null;
            }
        }
    }

    public void markUserVerified(String email) throws SQLException {
        String sql = "UPDATE user SET is_verified = 1 WHERE email = ?";
        try (Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.executeUpdate();
        }
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM user ORDER BY id";
        try (Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String email = rs.getString("email");
                String password = rs.getString("password");
                boolean isVerified = rs.getBoolean("is_verified");
                users.add(new User(id, username, email, password, isVerified));
            }
        }
        return users;
    }

    public int getUserCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS cnt FROM user";
        try (Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("cnt");
            }
        }
        return 0;
    }

    public void updateUser(User user) throws SQLException {
        String passwordToStore = user.getPassword();
        if (!PasswordUtil.isBCryptHash(passwordToStore)) {
            passwordToStore = PasswordUtil.hashPassword(passwordToStore);
        }
        String sql = "UPDATE user SET username = ?, email = ?, password = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, passwordToStore);
            stmt.setInt(4, user.getId());
            stmt.executeUpdate();
        }
    }

    public void updateAdminPassword(int adminId, String newPassword) throws SQLException {
        String passwordToStore = newPassword;
        if (!PasswordUtil.isBCryptHash(passwordToStore)) {
            passwordToStore = PasswordUtil.hashPassword(passwordToStore);
        }
        String sql = "UPDATE user SET password = ? WHERE id = ? AND role = 'admin'";
        try (Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, passwordToStore);
            stmt.setInt(2, adminId);
            stmt.executeUpdate();
        }
    }
}
package com.example.kalaa;

import java.sql.*;

/**
 * Data Access Object for password_resets table.
 */
public class PasswordResetDAO {
    private final String jdbcURL = "jdbc:mysql://localhost:3306/kalaa?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private final String dbUser = "root";
    private final String dbPassword = "";

    public PasswordResetDAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ignored) {
        }
    }

    /**
     * Stores a new password reset token. Inactivates any previously unconsumed tokens for the same email.
     *
     * @param email      user email address
     * @param token      secure random token string
     * @param expiryTime expiration timestamp
     * @throws SQLException on database errors
     */
    public void createResetToken(String email, String token, Timestamp expiryTime) throws SQLException {
        String inactivateSql = "UPDATE password_resets SET is_used = 1 WHERE email = ? AND is_used = 0";
        String insertSql = "INSERT INTO password_resets (email, token, expiry_time, is_used) VALUES (?, ?, ?, 0)";

        try (Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword)) {
            conn.setAutoCommit(false);
            try {
                try (PreparedStatement inactivateStmt = conn.prepareStatement(inactivateSql)) {
                    inactivateStmt.setString(1, email);
                    inactivateStmt.executeUpdate();
                }

                try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                    insertStmt.setString(1, email);
                    insertStmt.setString(2, token);
                    insertStmt.setTimestamp(3, expiryTime);
                    insertStmt.executeUpdate();
                }

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }

    /**
     * Retrieves an active, unexpired, and unused reset token.
     *
     * @param token the token string
     * @return PasswordReset object if valid, null otherwise
     * @throws SQLException on database errors
     */
    public PasswordReset getValidResetToken(String token) throws SQLException {
        String sql = "SELECT * FROM password_resets WHERE token = ? AND is_used = 0";
        try (Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, token);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String email = rs.getString("email");
                    String tokenStr = rs.getString("token");
                    Timestamp expiry = rs.getTimestamp("expiry_time");
                    boolean isUsed = rs.getBoolean("is_used");

                    if (!isUsed && expiry != null && expiry.after(new Timestamp(System.currentTimeMillis()))) {
                        return new PasswordReset(id, email, tokenStr, expiry, isUsed);
                    }
                }
            }
        }
        return null;
    }

    /**
     * Marks a reset token as used so it cannot be reused.
     *
     * @param token the token string
     * @throws SQLException on database errors
     */
    public void markTokenAsUsed(String token) throws SQLException {
        String sql = "UPDATE password_resets SET is_used = 1 WHERE token = ?";
        try (Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, token);
            stmt.executeUpdate();
        }
    }
}

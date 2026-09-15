package com.example.kalaa;

import java.sql.*;

/**
 * Data Access Object for email_verifications table.
 */
public class EmailVerificationDAO {
    private final String jdbcURL = "jdbc:mysql://localhost:3306/kalaa?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private final String dbUser = "root";
    private final String dbPassword = "";

    public EmailVerificationDAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ignored) {
        }
    }

    /**
     * Stores a new verification code for the email.
     * Invalidates any previous unused codes for this email.
     */
    public void createVerificationCode(String email, String code, Timestamp expiryTime) throws SQLException {
        String inactivateSql = "UPDATE email_verifications SET is_used = 1 WHERE email = ? AND is_used = 0";
        String insertSql = "INSERT INTO email_verifications (email, code, expiry_time, is_used) VALUES (?, ?, ?, 0)";

        try (Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword)) {
            conn.setAutoCommit(false);
            try {
                try (PreparedStatement inactivateStmt = conn.prepareStatement(inactivateSql)) {
                    inactivateStmt.setString(1, email);
                    inactivateStmt.executeUpdate();
                }

                try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                    insertStmt.setString(1, email);
                    insertStmt.setString(2, code);
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
     * Retrieves a valid, unexpired, and unused verification record for the given email and code.
     */
    public EmailVerification getValidVerification(String email, String code) throws SQLException {
        String sql = "SELECT * FROM email_verifications WHERE email = ? AND code = ? AND is_used = 0 ORDER BY id DESC LIMIT 1";
        try (Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, code);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String userEmail = rs.getString("email");
                    String userCode = rs.getString("code");
                    Timestamp expiry = rs.getTimestamp("expiry_time");
                    boolean isUsed = rs.getBoolean("is_used");

                    if (!isUsed && expiry != null && expiry.after(new Timestamp(System.currentTimeMillis()))) {
                        return new EmailVerification(id, userEmail, userCode, expiry, isUsed);
                    }
                }
            }
        }
        return null;
    }

    /**
     * Marks the verification code as consumed.
     */
    public void markCodeAsUsed(String email, String code) throws SQLException {
        String sql = "UPDATE email_verifications SET is_used = 1 WHERE email = ? AND code = ?";
        try (Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, code);
            stmt.executeUpdate();
        }
    }
}

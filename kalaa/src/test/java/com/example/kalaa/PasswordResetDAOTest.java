package com.example.kalaa;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class PasswordResetDAOTest {

    private PasswordResetDAO passwordResetDAO;
    private UserDAO userDAO;
    private String testEmail;
    private int testUserId;

    @BeforeEach
    public void setUp() throws SQLException {
        passwordResetDAO = new PasswordResetDAO();
        userDAO = new UserDAO();

        // Create a test user to satisfy the foreign key constraint
        testEmail = "reset_test_" + System.currentTimeMillis() + "@example.com";
        User testUser = new User("resetUser", testEmail, "initialPassword123");
        userDAO.addUser(testUser);

        User created = userDAO.getUserByEmail(testEmail);
        assertNotNull(created);
        testUserId = created.getId();
    }

    @AfterEach
    public void tearDown() throws SQLException {
        if (testUserId > 0) {
            userDAO.deleteUser(testUserId);
        }
    }

    @Test
    public void testCreateAndRetrieveValidResetToken() throws SQLException {
        String token = UUID.randomUUID().toString();
        Timestamp expiry = new Timestamp(System.currentTimeMillis() + (30 * 60 * 1000L));

        passwordResetDAO.createResetToken(testEmail, token, expiry);

        PasswordReset reset = passwordResetDAO.getValidResetToken(token);
        assertNotNull(reset, "Reset token should be retrievable from database");
        assertEquals(testEmail, reset.getEmail());
        assertEquals(token, reset.getToken());
        assertFalse(reset.isUsed());
        assertFalse(reset.isExpired());
    }

    @Test
    public void testMarkTokenAsUsed() throws SQLException {
        String token = UUID.randomUUID().toString();
        Timestamp expiry = new Timestamp(System.currentTimeMillis() + (30 * 60 * 1000L));

        passwordResetDAO.createResetToken(testEmail, token, expiry);

        // Verify it's initially valid
        assertNotNull(passwordResetDAO.getValidResetToken(token));

        // Mark as used
        passwordResetDAO.markTokenAsUsed(token);

        // Should no longer be returned as a valid token
        assertNull(passwordResetDAO.getValidResetToken(token), "Used token should not be returned as valid");
    }

    @Test
    public void testExpiredTokenNotReturned() throws SQLException {
        String token = UUID.randomUUID().toString();
        // Expired 10 seconds ago
        Timestamp expiry = new Timestamp(System.currentTimeMillis() - 10000L);

        passwordResetDAO.createResetToken(testEmail, token, expiry);

        // Should not be valid
        assertNull(passwordResetDAO.getValidResetToken(token), "Expired token should not be returned as valid");
    }

    @Test
    public void testFullPasswordResetWorkflow() throws SQLException {
        String token = UUID.randomUUID().toString();
        Timestamp expiry = new Timestamp(System.currentTimeMillis() + (30 * 60 * 1000L));

        // 1. Create reset token
        passwordResetDAO.createResetToken(testEmail, token, expiry);

        // 2. Validate token
        PasswordReset reset = passwordResetDAO.getValidResetToken(token);
        assertNotNull(reset);

        // 3. Update password
        String newPassword = "newSuperSecretPassword456";
        userDAO.updatePasswordByEmail(reset.getEmail(), newPassword);

        // 4. Mark token used
        passwordResetDAO.markTokenAsUsed(token);
        assertNull(passwordResetDAO.getValidResetToken(token));

        // 5. Verify user can log in with new password
        User loggedInUser = userDAO.getUserByEmailAndPassword(testEmail, newPassword);
        assertNotNull(loggedInUser, "User should be able to log in with new password");

        // 6. Verify user cannot log in with old password
        User failedLogin = userDAO.getUserByEmailAndPassword(testEmail, "initialPassword123");
        assertNull(failedLogin, "User should not be able to log in with old password");
    }

    @Test
    public void testEmailServiceMockFallback() {
        // In local development without credentials configured, sending should gracefully log and succeed
        boolean result = EmailService.sendPasswordResetEmail("recipient@example.com", "http://localhost:8080/kalaa/resetpassword?token=dummy");
        assertTrue(result, "EmailService should safely succeed in local development mode without throwing");
    }
}

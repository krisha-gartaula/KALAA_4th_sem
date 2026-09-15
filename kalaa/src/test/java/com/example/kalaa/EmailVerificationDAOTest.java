package com.example.kalaa;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

public class EmailVerificationDAOTest {

    private EmailVerificationDAO emailVerificationDAO;
    private UserDAO userDAO;
    private String testEmail;
    private int testUserId;

    @BeforeEach
    public void setUp() throws SQLException {
        emailVerificationDAO = new EmailVerificationDAO();
        userDAO = new UserDAO();

        testEmail = "otp_test_" + System.currentTimeMillis() + "@example.com";
        User testUser = new User(0, "otpUser", testEmail, "initialPassword123", false);
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
    public void testGenerate6DigitCodeFormat() {
        for (int i = 0; i < 50; i++) {
            String code = EmailService.generate6DigitCode();
            assertNotNull(code);
            assertEquals(6, code.length());
            assertTrue(code.matches("^[0-9]{6}$"), "Code must consist of exactly 6 numeric digits");
        }
    }

    @Test
    public void testCreateAndVerifyCode() throws SQLException {
        String code = EmailService.generate6DigitCode();
        Timestamp expiry = new Timestamp(System.currentTimeMillis() + (15 * 60 * 1000L));

        emailVerificationDAO.createVerificationCode(testEmail, code, expiry);

        EmailVerification verification = emailVerificationDAO.getValidVerification(testEmail, code);
        assertNotNull(verification, "Verification record must be found in database");
        assertEquals(testEmail, verification.getEmail());
        assertEquals(code, verification.getCode());
        assertFalse(verification.isUsed());
        assertFalse(verification.isExpired());

        // Mark as used
        emailVerificationDAO.markCodeAsUsed(testEmail, code);
        assertNull(emailVerificationDAO.getValidVerification(testEmail, code), "Used code must not be valid");
    }

    @Test
    public void testExpiredVerificationCode() throws SQLException {
        String code = EmailService.generate6DigitCode();
        Timestamp pastExpiry = new Timestamp(System.currentTimeMillis() - 5000L);

        emailVerificationDAO.createVerificationCode(testEmail, code, pastExpiry);

        assertNull(emailVerificationDAO.getValidVerification(testEmail, code), "Expired code must not be returned");
    }

    @Test
    public void testUserVerificationLifecycle() throws SQLException {
        User user = userDAO.getUserByEmail(testEmail);
        assertNotNull(user);
        assertFalse(user.isVerified(), "User should initially be unverified");

        userDAO.markUserVerified(testEmail);

        User verifiedUser = userDAO.getUserByEmail(testEmail);
        assertNotNull(verifiedUser);
        assertTrue(verifiedUser.isVerified(), "User should be verified after markUserVerified");
    }
}

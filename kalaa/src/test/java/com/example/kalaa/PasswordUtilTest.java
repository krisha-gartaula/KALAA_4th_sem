package com.example.kalaa;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PasswordUtilTest {

    @Test
    public void testHashPasswordSuccess() {
        String raw = "mySecurePassword123";
        String hash1 = PasswordUtil.hashPassword(raw);
        String hash2 = PasswordUtil.hashPassword(raw);

        assertNotNull(hash1);
        assertEquals(60, hash1.length());
        assertTrue(PasswordUtil.isBCryptHash(hash1));

        // BCrypt uses random salts so two hashes of the same password should not be identical
        assertNotEquals(hash1, hash2);
    }

    @Test
    public void testHashPasswordInvalidInput() {
        assertThrows(IllegalArgumentException.class, () -> PasswordUtil.hashPassword(null));
        assertThrows(IllegalArgumentException.class, () -> PasswordUtil.hashPassword(""));
    }

    @Test
    public void testCheckPasswordWithHashedPassword() {
        String raw = "admin123";
        String hash = PasswordUtil.hashPassword(raw);

        assertTrue(PasswordUtil.checkPassword("admin123", hash));
        assertFalse(PasswordUtil.checkPassword("wrongpassword", hash));
        assertFalse(PasswordUtil.checkPassword("", hash));
    }

    @Test
    public void testCheckPasswordLegacyPlainTextFallback() {
        String legacyStoredPassword = "admin";

        // Plain text match works for backward compatibility
        assertTrue(PasswordUtil.checkPassword("admin", legacyStoredPassword));
        assertFalse(PasswordUtil.checkPassword("wrongpassword", legacyStoredPassword));
    }

    @Test
    public void testIsBCryptHash() {
        String validHash = PasswordUtil.hashPassword("secret");
        assertTrue(PasswordUtil.isBCryptHash(validHash));

        assertFalse(PasswordUtil.isBCryptHash("admin"));
        assertFalse(PasswordUtil.isBCryptHash(null));
        assertFalse(PasswordUtil.isBCryptHash(""));
        assertFalse(PasswordUtil.isBCryptHash("$2a$12$short"));
    }

    @Test
    public void testNeedsRehash() {
        String validHash = PasswordUtil.hashPassword("secret");
        assertFalse(PasswordUtil.needsRehash(validHash));

        assertTrue(PasswordUtil.needsRehash("plainTextPassword"));
        assertTrue(PasswordUtil.needsRehash("admin"));
    }

    @Test
    public void testCheckPasswordNullSafety() {
        assertFalse(PasswordUtil.checkPassword(null, "somehash"));
        assertFalse(PasswordUtil.checkPassword("somepassword", null));
        assertFalse(PasswordUtil.checkPassword(null, null));
    }

    @Test
    public void testAdminHashGeneration() {
        String adminHash = "$2a$12$LWhSTdmfz7wl61wrxFlWre9gToHwDaq8JNqNSvW7kSaJymJP/7W2C";
        assertTrue(PasswordUtil.checkPassword("admin", adminHash));
        assertFalse(PasswordUtil.checkPassword("wrongadmin", adminHash));
    }
}

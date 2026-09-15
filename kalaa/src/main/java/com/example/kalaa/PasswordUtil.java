package com.example.kalaa;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Utility class for hashing and verifying passwords using BCrypt.
 * Supports backward compatibility for existing plain-text passwords
 * and transparent migration to BCrypt.
 */
public final class PasswordUtil {

    private static final int BCRYPT_LOG_ROUNDS = 12;

    private PasswordUtil() {
        // Utility class
    }

    /**
     * Hashes a plain-text password using BCrypt with a secure random salt.
     *
     * @param plainPassword the plain-text password
     * @return the 60-character BCrypt hash string
     * @throws IllegalArgumentException if plainPassword is null or empty
     */
    public static String hashPassword(String plainPassword) {
        if (plainPassword == null || plainPassword.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(BCRYPT_LOG_ROUNDS));
    }

    /**
     * Verifies a plain-text password against a stored password string.
     * If the stored password is a BCrypt hash, it uses BCrypt verification.
     * If the stored password is legacy plain text, it performs a fallback check.
     *
     * @param plainPassword the plain-text password entered by the user
     * @param storedPassword the stored password (BCrypt hash or legacy plain text)
     * @return true if the password matches, false otherwise
     */
    public static boolean checkPassword(String plainPassword, String storedPassword) {
        if (plainPassword == null || storedPassword == null) {
            return false;
        }

        if (isBCryptHash(storedPassword)) {
            try {
                return BCrypt.checkpw(plainPassword, storedPassword);
            } catch (IllegalArgumentException e) {
                return false;
            }
        }

        // Backward compatibility fallback for legacy plain text passwords in database
        return plainPassword.equals(storedPassword);
    }

    /**
     * Checks if a string appears to be a valid 60-character BCrypt hash.
     *
     * @param candidate the string to test
     * @return true if candidate matches BCrypt structure ($2a$, $2b$, or $2y$ prefix and length 60)
     */
    public static boolean isBCryptHash(String candidate) {
        if (candidate == null || candidate.length() != 60) {
            return false;
        }
        return candidate.startsWith("$2a$") || candidate.startsWith("$2b$") || candidate.startsWith("$2y$");
    }

    /**
     * Determines whether a stored password needs to be re-hashed to BCrypt.
     * Returns true for legacy plain text passwords.
     *
     * @param storedPassword the stored password string
     * @return true if re-hashing is required
     */
    public static boolean needsRehash(String storedPassword) {
        return !isBCryptHash(storedPassword);
    }
}

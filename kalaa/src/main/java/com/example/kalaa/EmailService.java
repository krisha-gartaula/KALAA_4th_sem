package com.example.kalaa;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Service to manage SMTP configuration and email delivery for OTP verification and password reset.
 */
public class EmailService {

    private static final Logger LOGGER = Logger.getLogger(EmailService.class.getName());
    private static final Properties config = new Properties();
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    static {
        loadConfiguration();
    }

    private static void loadConfiguration() {
        try (InputStream in = EmailService.class.getClassLoader().getResourceAsStream("email.properties")) {
            if (in != null) {
                config.load(in);
            }
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Failed to load email.properties from classpath", e);
        }

        // Support environment variable overrides
        overrideFromEnv("SMTP_HOST", "mail.smtp.host");
        overrideFromEnv("SMTP_PORT", "mail.smtp.port");
        overrideFromEnv("SMTP_USERNAME", "mail.smtp.username");
        overrideFromEnv("SMTP_PASSWORD", "mail.smtp.password");
        overrideFromEnv("SMTP_FROM", "mail.smtp.from");
        overrideFromEnv("SMTP_FROM_NAME", "mail.smtp.from.name");
    }

    private static void overrideFromEnv(String envKey, String propKey) {
        String envVal = System.getenv(envKey);
        if (envVal != null && !envVal.trim().isEmpty()) {
            config.setProperty(propKey, envVal.trim());
        }
    }

    /**
     * Generates a cryptographically strong 6-digit numeric OTP.
     */
    public static String generate6DigitCode() {
        int code = 100000 + SECURE_RANDOM.nextInt(900000);
        return String.valueOf(code);
    }

    /**
     * Checks whether valid SMTP credentials are configured.
     */
    public static boolean isConfigured() {
        String username = config.getProperty("mail.smtp.username");
        String password = config.getProperty("mail.smtp.password");
        return username != null && !username.trim().isEmpty()
                && password != null && !password.trim().isEmpty();
    }

    /**
     * Sends an email verification code asynchronously.
     */
    public static CompletableFuture<Boolean> sendSignupVerificationEmailAsync(String toEmail, String code) {
        return CompletableFuture.supplyAsync(() -> sendSignupVerificationEmail(toEmail, code));
    }

    /**
     * Sends a 6-digit email verification OTP to the user.
     */
    public static boolean sendSignupVerificationEmail(String toEmail, String code) {
        if (!isConfigured()) {
            LOGGER.warning("================================================================================");
            LOGGER.warning("[EmailService] SMTP credentials are not configured in email.properties.");
            LOGGER.warning("[EmailService] 6-digit SIGNUP verification code for " + toEmail + " is: " + code);
            LOGGER.warning("================================================================================");
            return true;
        }

        try {
            Session session = createMailSession();
            MimeMessage message = new MimeMessage(session);
            setupSenderAndRecipient(message, toEmail, "KALAA - Verify Your Email Address");

            String htmlBody = ""
                    + "<div style=\"font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; max-width: 520px; margin: 0 auto; padding: 28px; border: 1px solid #e2e8f0; border-radius: 10px; background-color: #ffffff;\">"
                    + "  <h2 style=\"color: #1a202c; text-align: center; margin-bottom: 8px; letter-spacing: 1px;\">KALAA</h2>"
                    + "  <p style=\"color: #718096; font-size: 14px; text-align: center; margin-top: 0;\">Storytelling & Community Platform</p>"
                    + "  <hr style=\"border: none; border-top: 1px solid #edf2f7; margin: 20px 0;\">"
                    + "  <p style=\"color: #2d3748; font-size: 15px; line-height: 1.6;\">Hello,</p>"
                    + "  <p style=\"color: #2d3748; font-size: 15px; line-height: 1.6;\">Thank you for registering on KALAA. Please enter the following 6-digit verification code to complete your signup:</p>"
                    + "  <div style=\"text-align: center; margin: 28px 0;\">"
                    + "    <span style=\"display: inline-block; font-size: 34px; font-weight: 700; letter-spacing: 8px; color: #1a202c; background-color: #f7fafc; padding: 14px 28px; border: 2px dashed #cbd5e0; border-radius: 8px;\">"
                    + code
                    + "    </span>"
                    + "  </div>"
                    + "  <p style=\"color: #718096; font-size: 13px; text-align: center;\">This code expires in <strong>15 minutes</strong>.</p>"
                    + "  <hr style=\"border: none; border-top: 1px solid #edf2f7; margin: 24px 0;\">"
                    + "  <p style=\"color: #a0aec0; font-size: 12px; text-align: center;\">If you did not sign up for a KALAA account, please disregard this email.</p>"
                    + "</div>";

            message.setContent(htmlBody, "text/html; charset=UTF-8");
            Transport.send(message);
            LOGGER.info("[EmailService] Verification code successfully sent to " + toEmail);
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "[EmailService] Failed to send verification code to " + toEmail, e);
            return false;
        }
    }

    /**
     * Sends password reset code and link asynchronously.
     */
    public static CompletableFuture<Boolean> sendPasswordResetEmailAsync(String toEmail, String code, String resetLink) {
        return CompletableFuture.supplyAsync(() -> sendPasswordResetEmail(toEmail, code, resetLink));
    }

    public static CompletableFuture<Boolean> sendPasswordResetEmailAsync(String toEmail, String resetLink) {
        return CompletableFuture.supplyAsync(() -> sendPasswordResetEmail(toEmail, resetLink));
    }

    public static boolean sendPasswordResetEmail(String toEmail, String resetLink) {
        return sendPasswordResetEmail(toEmail, null, resetLink);
    }

    /**
     * Sends password reset email with 6-digit code and one-click reset link.
     */
    public static boolean sendPasswordResetEmail(String toEmail, String code, String resetLink) {
        if (!isConfigured()) {
            LOGGER.warning("================================================================================");
            LOGGER.warning("[EmailService] SMTP credentials are not configured in email.properties.");
            if (code != null) {
                LOGGER.warning("[EmailService] 6-digit PASSWORD RESET code for " + toEmail + " is: " + code);
            }
            LOGGER.warning("[EmailService] Reset link for " + toEmail + ": " + resetLink);
            LOGGER.warning("================================================================================");
            return true;
        }

        try {
            Session session = createMailSession();
            MimeMessage message = new MimeMessage(session);
            setupSenderAndRecipient(message, toEmail, "KALAA - Reset Your Password");

            String codeSection = "";
            if (code != null && !code.trim().isEmpty()) {
                codeSection = ""
                        + "  <p style=\"color: #2d3748; font-size: 15px; line-height: 1.6;\">Your 6-digit password reset code is:</p>"
                        + "  <div style=\"text-align: center; margin: 20px 0;\">"
                        + "    <span style=\"display: inline-block; font-size: 32px; font-weight: 700; letter-spacing: 8px; color: #1a202c; background-color: #f7fafc; padding: 12px 24px; border: 2px dashed #cbd5e0; border-radius: 8px;\">"
                        + code
                        + "    </span>"
                        + "  </div>"
                        + "  <p style=\"color: #718096; font-size: 13px; text-align: center;\">Or click the button below to reset directly:</p>";
            }

            String htmlBody = ""
                    + "<div style=\"font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; max-width: 520px; margin: 0 auto; padding: 28px; border: 1px solid #e2e8f0; border-radius: 10px; background-color: #ffffff;\">"
                    + "  <h2 style=\"color: #1a202c; text-align: center; margin-bottom: 8px;\">KALAA</h2>"
                    + "  <p style=\"color: #718096; font-size: 14px; text-align: center; margin-top: 0;\">Password Reset Request</p>"
                    + "  <hr style=\"border: none; border-top: 1px solid #edf2f7; margin: 20px 0;\">"
                    + "  <p style=\"color: #2d3748; font-size: 15px; line-height: 1.6;\">Hello,</p>"
                    + "  <p style=\"color: #2d3748; font-size: 15px; line-height: 1.6;\">We received a request to reset your KALAA password.</p>"
                    + codeSection
                    + "  <div style=\"text-align: center; margin: 24px 0;\">"
                    + "    <a href=\"" + resetLink + "\" style=\"background-color: #2b2b2b; color: #ffffff; text-decoration: none; padding: 12px 28px; font-size: 15px; border-radius: 6px; display: inline-block; font-weight: 600;\">Reset Password</a>"
                    + "  </div>"
                    + "  <p style=\"color: #718096; font-size: 13px; line-height: 1.5;\">If the button does not work, copy and paste this link:<br>"
                    + "    <a href=\"" + resetLink + "\" style=\"color: #3182ce; word-break: break-all;\">" + resetLink + "</a>"
                    + "  </p>"
                    + "  <p style=\"color: #718096; font-size: 13px; text-align: center;\">This code and link expire in <strong>15 minutes</strong>.</p>"
                    + "  <hr style=\"border: none; border-top: 1px solid #edf2f7; margin: 24px 0;\">"
                    + "  <p style=\"color: #a0aec0; font-size: 12px; text-align: center;\">If you did not request a password reset, please ignore this email.</p>"
                    + "</div>";

            message.setContent(htmlBody, "text/html; charset=UTF-8");
            Transport.send(message);
            LOGGER.info("[EmailService] Password reset email sent to " + toEmail);
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "[EmailService] Failed to send password reset email to " + toEmail, e);
            return false;
        }
    }

    private static Session createMailSession() {
        final String username = config.getProperty("mail.smtp.username").trim();
        final String password = config.getProperty("mail.smtp.password").trim();

        Properties mailProps = new Properties();
        mailProps.put("mail.smtp.host", config.getProperty("mail.smtp.host", "smtp.gmail.com"));
        mailProps.put("mail.smtp.port", config.getProperty("mail.smtp.port", "587"));
        mailProps.put("mail.smtp.auth", config.getProperty("mail.smtp.auth", "true"));
        mailProps.put("mail.smtp.starttls.enable", config.getProperty("mail.smtp.starttls.enable", "true"));
        mailProps.put("mail.smtp.ssl.protocols", config.getProperty("mail.smtp.ssl.protocols", "TLSv1.2"));

        return Session.getInstance(mailProps, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }

    private static void setupSenderAndRecipient(MimeMessage message, String toEmail, String subject) throws Exception {
        final String username = config.getProperty("mail.smtp.username").trim();
        final String fromAddress = config.getProperty("mail.smtp.from", username).trim();
        final String fromName = config.getProperty("mail.smtp.from.name", "KALAA Stories").trim();

        message.setFrom(new InternetAddress(fromAddress, fromName));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
        message.setSubject(subject);
    }
}

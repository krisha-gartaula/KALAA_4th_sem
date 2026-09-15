package com.example.kalaa;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/verifyemail")
public class VerifyEmailServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(VerifyEmailServlet.class.getName());
    private static final int CODE_EXPIRY_MINUTES = 15;

    private UserDAO userDAO;
    private EmailVerificationDAO emailVerificationDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
        emailVerificationDAO = new EmailVerificationDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("verify-email.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        String email = request.getParameter("email");

        if (email == null || email.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        email = email.trim();
        String encodedEmail = URLEncoder.encode(email, StandardCharsets.UTF_8.toString());

        try {
            if ("resend".equalsIgnoreCase(action)) {
                handleResend(request, response, email, encodedEmail);
            } else {
                handleVerification(request, response, email, encodedEmail);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database error during email verification for " + email, e);
            response.sendRedirect(request.getContextPath() + "/verify-email.jsp?email=" + encodedEmail + "&error=databaseerror");
        }
    }

    private void handleVerification(HttpServletRequest request, HttpServletResponse response, String email, String encodedEmail)
            throws SQLException, IOException {

        String code = request.getParameter("code");

        if (code == null || code.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/verify-email.jsp?email=" + encodedEmail + "&error=emptycode");
            return;
        }

        code = code.trim();

        EmailVerification verification = emailVerificationDAO.getValidVerification(email, code);

        if (verification != null) {
            // Mark verification code as used
            emailVerificationDAO.markCodeAsUsed(email, code);

            // Mark user as verified in database
            userDAO.markUserVerified(email);

            // Retrieve updated user to establish session
            User user = userDAO.getUserByEmail(email);
            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                session.setAttribute("userId", user.getId());
                session.setAttribute("username", user.getUsername());
                session.setAttribute("userEmail", user.getEmail());
            }

            response.sendRedirect(request.getContextPath() + "/index.jsp?success=verified");
        } else {
            response.sendRedirect(request.getContextPath() + "/verify-email.jsp?email=" + encodedEmail + "&error=invalidcode");
        }
    }

    private void handleResend(HttpServletRequest request, HttpServletResponse response, String email, String encodedEmail)
            throws SQLException, IOException {

        User user = userDAO.getUserByEmail(email);
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/signup.jsp?error=usernotfound");
            return;
        }

        if (user.isVerified()) {
            response.sendRedirect(request.getContextPath() + "/login.jsp?success=alreadyverified");
            return;
        }

        String newCode = EmailService.generate6DigitCode();
        Timestamp expiry = new Timestamp(System.currentTimeMillis() + (CODE_EXPIRY_MINUTES * 60 * 1000L));

        emailVerificationDAO.createVerificationCode(email, newCode, expiry);
        EmailService.sendSignupVerificationEmailAsync(email, newCode);

        response.sendRedirect(request.getContextPath() + "/verify-email.jsp?email=" + encodedEmail + "&success=resent");
    }
}

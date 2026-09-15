package com.example.kalaa;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/forgotpassword")
public class ForgotPasswordServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(ForgotPasswordServlet.class.getName());
    private static final int EXPIRY_MINUTES = 30;

    private UserDAO userDAO;
    private PasswordResetDAO passwordResetDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
        passwordResetDAO = new PasswordResetDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("forgotpassword.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");

        if (email == null || email.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/forgotpassword.jsp?error=emptyemail");
            return;
        }

        email = email.trim();

        try {
            User user = userDAO.getUserByEmail(email);

            if (user != null) {
                String code = EmailService.generate6DigitCode();
                Timestamp expiryTime = new Timestamp(System.currentTimeMillis() + (EXPIRY_MINUTES * 60 * 1000L));

                passwordResetDAO.createResetToken(email, code, expiryTime);

                String scheme = request.getScheme();
                String serverName = request.getServerName();
                int serverPort = request.getServerPort();
                String contextPath = request.getContextPath();
                String portPart = (("http".equalsIgnoreCase(scheme) && serverPort == 80)
                        || ("https".equalsIgnoreCase(scheme) && serverPort == 443))
                        ? ""
                        : (":" + serverPort);

                String resetLink = scheme + "://" + serverName + portPart + contextPath + "/resetpassword?token=" + code;

                // Send email with 6-digit code and direct reset link
                EmailService.sendPasswordResetEmailAsync(email, code, resetLink);
            }

            String encodedEmail = java.net.URLEncoder.encode(email, java.nio.charset.StandardCharsets.UTF_8.toString());
            response.sendRedirect(request.getContextPath() + "/resetpassword.jsp?email=" + encodedEmail + "&status=sent");

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database error during password reset request for: " + email, e);
            response.sendRedirect(request.getContextPath() + "/forgotpassword.jsp?error=databaseerror");
        }
    }
}

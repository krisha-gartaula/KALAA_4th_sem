package com.example.kalaa;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/resetpassword")
public class ResetPasswordServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(ResetPasswordServlet.class.getName());

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

        String token = request.getParameter("token");

        if (token != null && !token.trim().isEmpty()) {
            token = token.trim();
            try {
                PasswordReset reset = passwordResetDAO.getValidResetToken(token);
                if (reset != null) {
                    request.setAttribute("token", token);
                    request.setAttribute("email", reset.getEmail());
                } else {
                    response.sendRedirect(request.getContextPath() + "/resetpassword.jsp?error=invalidcode");
                    return;
                }
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Database error validating reset token", e);
                response.sendRedirect(request.getContextPath() + "/resetpassword.jsp?error=databaseerror");
                return;
            }
        }

        request.getRequestDispatcher("resetpassword.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String token = request.getParameter("token");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        if (token == null || token.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/forgotpassword.jsp?error=invalidtoken");
            return;
        }

        token = token.trim();

        if (password == null || password.trim().isEmpty() || confirmPassword == null) {
            response.sendRedirect(request.getContextPath() + "/resetpassword?token=" + token + "&error=emptypassword");
            return;
        }

        if (password.length() < 6) {
            response.sendRedirect(request.getContextPath() + "/resetpassword?token=" + token + "&error=shortpassword");
            return;
        }

        if (!password.equals(confirmPassword)) {
            response.sendRedirect(request.getContextPath() + "/resetpassword?token=" + token + "&error=mismatch");
            return;
        }

        try {
            PasswordReset reset = passwordResetDAO.getValidResetToken(token);
            if (reset == null) {
                response.sendRedirect(request.getContextPath() + "/resetpassword.jsp?error=invalidcode");
                return;
            }

            // Update user password with secure BCrypt hash
            userDAO.updatePasswordByEmail(reset.getEmail(), password);

            // Invalidate the used token
            passwordResetDAO.markTokenAsUsed(token);

            response.sendRedirect(request.getContextPath() + "/login.jsp?success=passwordreset");

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database error completing password reset", e);
            response.sendRedirect(request.getContextPath() + "/resetpassword?token=" + token + "&error=databaseerror");
        }
    }
}

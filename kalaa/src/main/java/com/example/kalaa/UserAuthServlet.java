package com.example.kalaa;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

@WebServlet("/login")
public class UserAuthServlet extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if ("login".equals(action)) {
            handleLogin(request, response);
        } else if ("add".equals(action)) {
            handleSignup(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/login.jsp?error=invalidaction");
        }
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            User user = userDAO.getUserByEmailAndPassword(email, password);
            
            if (user != null) {
                if (!user.isVerified()) {
                    // User has not verified their email address
                    String code = EmailService.generate6DigitCode();
                    Timestamp expiry = new Timestamp(System.currentTimeMillis() + (15 * 60 * 1000L));
                    new EmailVerificationDAO().createVerificationCode(email, code, expiry);
                    EmailService.sendSignupVerificationEmailAsync(email, code);

                    String encodedEmail = java.net.URLEncoder.encode(email, java.nio.charset.StandardCharsets.UTF_8.toString());
                    response.sendRedirect(request.getContextPath() + "/verify-email.jsp?email=" + encodedEmail + "&error=unverified");
                    return;
                }

                // User login successful
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                session.setAttribute("userId", user.getId());
                session.setAttribute("username", user.getUsername());
                session.setAttribute("userEmail", user.getEmail());
                
                response.sendRedirect(request.getContextPath() + "/index.jsp?success=loginsuccess");
            } else {
                response.sendRedirect(request.getContextPath() + "/login.jsp?error=invalidcredentials");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/login.jsp?error=databaseerror");
        }
    }

    private void handleSignup(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        if (!password.equals(confirmPassword)) {
            response.sendRedirect(request.getContextPath() + "/signup.jsp?error=passwordmismatch");
            return;
        }

        try {
            User existingUser = userDAO.getUserByEmail(email);
            if (existingUser != null) {
                if (!existingUser.isVerified()) {
                    // Account exists but was never verified: send fresh code and allow verification
                    String code = EmailService.generate6DigitCode();
                    Timestamp expiry = new Timestamp(System.currentTimeMillis() + (15 * 60 * 1000L));
                    new EmailVerificationDAO().createVerificationCode(email, code, expiry);
                    EmailService.sendSignupVerificationEmailAsync(email, code);

                    String encodedEmail = java.net.URLEncoder.encode(email, java.nio.charset.StandardCharsets.UTF_8.toString());
                    response.sendRedirect(request.getContextPath() + "/verify-email.jsp?email=" + encodedEmail + "&info=alreadyregistered");
                    return;
                }
                response.sendRedirect(request.getContextPath() + "/signup.jsp?error=userexists");
                return;
            }

            // Create user with is_verified = false
            User newUser = new User(0, username, email, password, false);
            userDAO.addUser(newUser);

            // Generate 6-digit OTP
            String code = EmailService.generate6DigitCode();
            Timestamp expiry = new Timestamp(System.currentTimeMillis() + (15 * 60 * 1000L));
            new EmailVerificationDAO().createVerificationCode(email, code, expiry);

            // Send verification code to user's real email
            EmailService.sendSignupVerificationEmailAsync(email, code);

            String encodedEmail = java.net.URLEncoder.encode(email, java.nio.charset.StandardCharsets.UTF_8.toString());
            response.sendRedirect(request.getContextPath() + "/verify-email.jsp?email=" + encodedEmail);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/signup.jsp?error=databaseerror");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
    }
}

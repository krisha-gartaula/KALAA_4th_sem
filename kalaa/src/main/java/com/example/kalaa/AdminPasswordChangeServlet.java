package com.example.kalaa;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/adminpasswordchange")
public class AdminPasswordChangeServlet extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        
        // Check if admin is logged in
        if (session.getAttribute("admin") == null) {
            response.sendRedirect(request.getContextPath() + "/adminlogin");
            return;
        }

        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        // Validate input
        if (currentPassword == null || currentPassword.trim().isEmpty() ||
            newPassword == null || newPassword.trim().isEmpty() ||
            confirmPassword == null || confirmPassword.trim().isEmpty()) {
            request.setAttribute("error", "All fields are required");
            request.getRequestDispatcher("Setting.jsp").forward(request, response);
            return;
        }

        // Check if new password and confirm password match
        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("error", "New password and confirm password do not match");
            request.getRequestDispatcher("Setting.jsp").forward(request, response);
            return;
        }

        // Check minimum password length
        if (newPassword.length() < 6) {
            request.setAttribute("error", "New password must be at least 6 characters long");
            request.getRequestDispatcher("Setting.jsp").forward(request, response);
            return;
        }

        try {
            User admin = (User) session.getAttribute("admin");
            
            if (!PasswordUtil.checkPassword(currentPassword, admin.getPassword())) {
                request.setAttribute("error", "Current password is incorrect");
                request.getRequestDispatcher("Setting.jsp").forward(request, response);
                return;
            }

            userDAO.updateAdminPassword(admin.getId(), newPassword);
            admin.setPassword(PasswordUtil.hashPassword(newPassword));
            session.setAttribute("admin", admin);
            
            request.setAttribute("success", "Password updated successfully");
            request.getRequestDispatcher("Setting.jsp").forward(request, response);
            
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Database error occurred while updating password");
            request.getRequestDispatcher("Setting.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/Setting.jsp");
    }
}

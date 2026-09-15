package com.example.kalaa;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/edituser")
public class EditUserServlet extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Check if admin is logged in
        HttpSession session = request.getSession();
        if (session.getAttribute("admin") == null) {
            response.sendRedirect(request.getContextPath() + "/adminlogin");
            return;
        }

        String action = request.getParameter("action");
        String userIdStr = request.getParameter("userId");

        if (userIdStr == null || userIdStr.trim().isEmpty()) {
            response.sendRedirect("usermanagement?error=invaliduserid");
            return;
        }

        try {
            int userId = Integer.parseInt(userIdStr);

            if ("update".equals(action)) {
                // Update user
                String username = request.getParameter("username");
                String email = request.getParameter("email");
                String password = request.getParameter("password");

                User user = new User(userId, username, email, password);
                userDAO.updateUser(user);
                response.sendRedirect("usermanagement?success=userupdated");

            } else if ("delete".equals(action)) {
                // Delete user
                userDAO.deleteUser(userId);
                response.sendRedirect("usermanagement?success=userdeleted");
            } else {
                response.sendRedirect("usermanagement?error=invalidaction");
            }

        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            response.sendRedirect("usermanagement?error=databaseerror");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.sendRedirect("usermanagement");
    }
}

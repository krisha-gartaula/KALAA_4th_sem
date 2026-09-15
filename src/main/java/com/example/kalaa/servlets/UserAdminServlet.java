package com.example.kalaa.servlets;

import com.example.kalaa.PasswordUtil;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/useradminservlet")
public class UserAdminServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/kalaa";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
                String sql = "INSERT INTO user (username, email, password, role) VALUES (?, ?, ?, ?)";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, req.getParameter("username"));
                ps.setString(2, req.getParameter("email"));
                String rawPassword = req.getParameter("password");
                String passwordToStore = (rawPassword != null && !rawPassword.isEmpty()) 
                        ? PasswordUtil.hashPassword(rawPassword) 
                        : "";
                ps.setString(3, passwordToStore);
                ps.setString(4, req.getParameter("role"));
                ps.executeUpdate();
            }
            resp.sendRedirect(req.getContextPath() + "/user.jsp");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}

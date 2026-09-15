
package com.example.kalaa.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/categoryadminservlet")
public class CategoryAdminServlet extends HttpServlet {
    // IMPORTANT: update DB_URL, DB_USER, DB_PASS in your local environment or move to a config file
    private static final String DB_URL = "jdbc:mysql://localhost:3306/kalaa";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
                String sql = "INSERT INTO categories (name, description) VALUES (?, ?)";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, req.getParameter("name"));
                ps.setString(2, req.getParameter("description"));
                ps.executeUpdate();
            }
            resp.sendRedirect(req.getContextPath() + "/admin/categories.jsp");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}

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

@WebServlet("/storyadminservlet")
public class StoryAdminServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/kalaa";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
                String sql = "INSERT INTO stories (title, author_id, category) VALUES (?, ?, ?)";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, req.getParameter("title"));
                ps.setString(2, req.getParameter("author_id"));
                ps.setString(3, req.getParameter("category"));
                ps.executeUpdate();
            }
            resp.sendRedirect(req.getContextPath() + "/story.jsp");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}

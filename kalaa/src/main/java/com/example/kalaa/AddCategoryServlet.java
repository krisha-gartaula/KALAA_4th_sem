package com.example.kalaa;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class AddCategoryServlet extends HttpServlet {
    private CategoryDAO categoryDAO;

    @Override
    public void init() throws ServletException {
        categoryDAO = new CategoryDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        if (session.getAttribute("admin") == null) {
            response.sendRedirect(request.getContextPath() + "/adminlogin");
            return;
        }

        String name = request.getParameter("name");
        String description = request.getParameter("description");

        if (name == null || name.trim().isEmpty() || description == null || description.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/categorymanagement?error=Please provide name and description");
            return;
        }

        try {
            Category category = new Category(name.trim(), description.trim());
            categoryDAO.addCategory(category);
            response.sendRedirect(request.getContextPath() + "/categorymanagement?success=Category added successfully");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/categorymanagement?error=Failed to add category");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/categorymanagement");
    }
}



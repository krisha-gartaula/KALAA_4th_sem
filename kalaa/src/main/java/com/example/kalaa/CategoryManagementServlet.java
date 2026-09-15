package com.example.kalaa;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/categorymanagement")
public class CategoryManagementServlet extends HttpServlet {
    private CategoryDAO categoryDAO;

    @Override
    public void init() throws ServletException {
        categoryDAO = new CategoryDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        if (session.getAttribute("admin") == null) {
            response.sendRedirect(request.getContextPath() + "/adminlogin");
            return;
        }

        try {
            List<Category> categories = categoryDAO.getAllCategories();
            request.setAttribute("categories", categories);
            request.getRequestDispatcher("Category.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Failed to load categories: " + e.getMessage());
            request.getRequestDispatcher("Category.jsp").forward(request, response);
        }
    }
}

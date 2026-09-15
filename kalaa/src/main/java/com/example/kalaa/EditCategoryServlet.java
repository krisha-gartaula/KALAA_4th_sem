package com.example.kalaa;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/editcategory")
public class EditCategoryServlet extends HttpServlet {
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

        String action = request.getParameter("action");
        String categoryIdStr = request.getParameter("categoryId");

        if (categoryIdStr == null || categoryIdStr.trim().isEmpty()) {
            response.sendRedirect("categorymanagement?error=invalidcategoryid");
            return;
        }

        try {
            int categoryId = Integer.parseInt(categoryIdStr);

            if ("update".equals(action)) {
                // Update category
                String name = request.getParameter("name");
                String description = request.getParameter("description");

                Category category = new Category(categoryId, name, description);
                categoryDAO.updateCategory(category);
                response.sendRedirect("categorymanagement?success=categoryupdated");

            } else if ("delete".equals(action)) {
                // Delete category
                categoryDAO.deleteCategory(categoryId);
                response.sendRedirect("categorymanagement?success=categorydeleted");
            } else {
                response.sendRedirect("categorymanagement?error=invalidaction");
            }

        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            response.sendRedirect("categorymanagement?error=databaseerror");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.sendRedirect("categorymanagement");
    }
}

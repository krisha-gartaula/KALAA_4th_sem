package com.example.kalaa;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/submitstory")
public class StorySubmissionServlet extends HttpServlet {
    private StoryDAO storyDAO;
    private CategoryDAO categoryDAO;

    @Override
    public void init() throws ServletException {
        storyDAO = new StoryDAO();
        categoryDAO = new CategoryDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        try {
            var categories = categoryDAO.getAllCategories();
            request.setAttribute("categories", categories);
            request.getRequestDispatcher("submitstory.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Failed to load categories");
            request.getRequestDispatcher("submitstory.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String title = request.getParameter("story-title");
        String content = request.getParameter("story-content");
        String categoryIdStr = request.getParameter("category");

        // Get user ID from session
        User user = (User) session.getAttribute("user");
        int authorId = user.getId();

        if (title == null || title.trim().isEmpty() || 
            content == null || content.trim().isEmpty() ||
            categoryIdStr == null || categoryIdStr.trim().isEmpty()) {
            
            request.setAttribute("error", "Please fill all fields correctly!");
            try {
                var categories = categoryDAO.getAllCategories();
                request.setAttribute("categories", categories);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            request.getRequestDispatcher("submitstory.jsp").forward(request, response);
            return;
        }

        try {
            Story story = new Story(title, authorId, categoryIdStr, content);
            storyDAO.addStory(story);
            
            response.sendRedirect(request.getContextPath() + "/storysubmitted.jsp?success=Story submitted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Failed to submit story: " + e.getMessage());
            try {
                var categories = categoryDAO.getAllCategories();
                request.setAttribute("categories", categories);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            request.getRequestDispatcher("submitstory.jsp").forward(request, response);
        }
    }
}

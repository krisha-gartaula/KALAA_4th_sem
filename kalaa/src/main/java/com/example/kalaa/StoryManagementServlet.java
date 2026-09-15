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

@WebServlet("/storymanagement")
public class StoryManagementServlet extends HttpServlet {
    private StoryDAO storyDAO;
    private CategoryDAO categoryDAO;
    private UserDAO userDAO;
    private AuthorDAO authorDAO;

    @Override
    public void init() throws ServletException {
        storyDAO = new StoryDAO();
        categoryDAO = new CategoryDAO();
        userDAO = new UserDAO();
        authorDAO = new AuthorDAO();
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
            List<Story> stories = storyDAO.getAllStories();
            List<Category> categories = categoryDAO.getAllCategories();
            List<User> users = userDAO.getAllUsers();
            
            request.setAttribute("stories", stories);
            request.setAttribute("categories", categories);
            request.setAttribute("users", users);
            request.setAttribute("authors", authorDAO.getAllAuthors());
            request.getRequestDispatcher("story.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Failed to load stories: " + e.getMessage());
            request.getRequestDispatcher("story.jsp").forward(request, response);
        }
    }
}

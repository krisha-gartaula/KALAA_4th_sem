package com.example.kalaa;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/genres")
public class GenreServlet extends HttpServlet {
    private StoryDAO storyDAO;
    private CategoryDAO categoryDAO;

    @Override
    public void init() throws ServletException {
        storyDAO = new StoryDAO();
        categoryDAO = new CategoryDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String categoryIdParam = request.getParameter("categoryId");
        try {
            request.setAttribute("categories", categoryDAO.getAllCategories());
            if (categoryIdParam != null && !categoryIdParam.isEmpty()) {
                int categoryId = Integer.parseInt(categoryIdParam);
                List<Story> stories = storyDAO.getStoriesByCategoryId(categoryId);
                request.setAttribute("stories", stories);
                request.setAttribute("activeCategoryId", categoryId);
            } else {
                // When no category is selected, show latest stories so new admin stories appear
                List<Story> allStories = storyDAO.getAllStories();
                request.setAttribute("stories", allStories);
            }
        } catch (SQLException e) {
            request.setAttribute("error", "Failed to load genres: " + e.getMessage());
        }
        request.getRequestDispatcher("genre.jsp").forward(request, response);
    }
}



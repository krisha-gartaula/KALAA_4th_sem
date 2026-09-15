package com.example.kalaa;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/read")
public class ReadStoryServlet extends HttpServlet {
    private StoryDAO storyDAO;

    @Override
    public void init() throws ServletException {
        storyDAO = new StoryDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String storyIdParam = request.getParameter("storyId");
        if (storyIdParam == null || storyIdParam.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/genres");
            return;
        }

        try {
            int storyId = Integer.parseInt(storyIdParam);
            Story story = storyDAO.getStoryById(storyId);
            if (story == null) {
                response.sendRedirect(request.getContextPath() + "/genres");
                return;
            }
            request.setAttribute("story", story);
            request.getRequestDispatcher("read.jsp").forward(request, response);
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/genres");
        }
    }
}

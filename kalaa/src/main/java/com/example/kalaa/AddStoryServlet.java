package com.example.kalaa;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/addstory")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 5 * 1024 * 1024,
        maxRequestSize = 10 * 1024 * 1024
)
public class AddStoryServlet extends HttpServlet {
    private StoryDAO storyDAO;

    @Override
    public void init() throws ServletException {
        storyDAO = new StoryDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        if (session.getAttribute("admin") == null) {
            response.sendRedirect(request.getContextPath() + "/adminlogin");
            return;
        }

        String title = request.getParameter("title");
        String authorIdStr = request.getParameter("author_id");
        String categoryIdStr = request.getParameter("category");
        String content = request.getParameter("content");

        try {
            int authorId = Integer.parseInt(authorIdStr);
            Story story = new Story(title, authorId, categoryIdStr, content);

            String contextPath = getServletContext().getRealPath("/");
            if (contextPath == null) {
                throw new ServletException("Upload path is not available.");
            }

            String imagePath = StoryImageUtil.saveStoryImage(request, "image", contextPath);
            story.setImagePath(imagePath);

            storyDAO.addStory(story);
            response.sendRedirect(request.getContextPath() + "/storymanagement?success=Story added successfully");
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/storymanagement?error=Failed to add story: " + e.getMessage());
        } catch (ServletException e) {
            response.sendRedirect(request.getContextPath() + "/storymanagement?error=" + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/storymanagement");
    }
}

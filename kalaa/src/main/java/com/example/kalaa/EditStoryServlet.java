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

@WebServlet("/editstory")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 5 * 1024 * 1024,
        maxRequestSize = 10 * 1024 * 1024
)
public class EditStoryServlet extends HttpServlet {
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

        String action = request.getParameter("action");
        String storyIdStr = request.getParameter("storyId");

        if (storyIdStr == null || storyIdStr.trim().isEmpty()) {
            response.sendRedirect("storymanagement?error=invalidstoryid");
            return;
        }

        try {
            int storyId = Integer.parseInt(storyIdStr);
            String contextPath = getServletContext().getRealPath("/");

            if ("update".equals(action)) {
                String title = request.getParameter("title");
                String authorIdStr = request.getParameter("author_id");
                String categoryIdStr = request.getParameter("category");
                String content = request.getParameter("content");

                int authorId = Integer.parseInt(authorIdStr);
                Story existingStory = storyDAO.getStoryById(storyId);
                if (existingStory == null) {
                    response.sendRedirect("storymanagement?error=storynotfound");
                    return;
                }

                Story story = new Story(storyId, title, authorId, categoryIdStr, content);
                story.setImagePath(existingStory.getImagePath());

                if (contextPath != null) {
                    String newImagePath = StoryImageUtil.saveStoryImage(request, "image", contextPath);
                    if (newImagePath != null) {
                        StoryImageUtil.deleteStoryImage(existingStory.getImagePath(), contextPath);
                        story.setImagePath(newImagePath);
                    }
                }

                storyDAO.updateStory(story);
                response.sendRedirect("storymanagement?success=storyupdated");

            } else if ("delete".equals(action)) {
                Story existingStory = storyDAO.getStoryById(storyId);
                if (existingStory != null && contextPath != null) {
                    StoryImageUtil.deleteStoryImage(existingStory.getImagePath(), contextPath);
                }
                storyDAO.deleteStory(storyId);
                response.sendRedirect("storymanagement?success=storydeleted");
            } else {
                response.sendRedirect("storymanagement?error=invalidaction");
            }

        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            response.sendRedirect("storymanagement?error=databaseerror");
        } catch (ServletException e) {
            response.sendRedirect("storymanagement?error=" + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("storymanagement");
    }
}

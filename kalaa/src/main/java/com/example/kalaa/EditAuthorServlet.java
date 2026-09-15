package com.example.kalaa;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class EditAuthorServlet extends HttpServlet {
    private AuthorDAO authorDAO;

    @Override
    public void init() throws ServletException {
        authorDAO = new AuthorDAO();
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
        String idStr = request.getParameter("authorId");
        if (idStr == null || idStr.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/authormanagement?error=invalidauthorid");
            return;
        }
        try {
            int id = Integer.parseInt(idStr);
            if ("update".equals(action)) {
                String name = request.getParameter("name");
                String email = request.getParameter("email");
                String bio = request.getParameter("bio");
                authorDAO.updateAuthor(new Author(id, name, email, bio));
                response.sendRedirect(request.getContextPath() + "/authormanagement?success=authorupdated");
            } else if ("delete".equals(action)) {
                authorDAO.deleteAuthor(id);
                response.sendRedirect(request.getContextPath() + "/authormanagement?success=authordeleted");
            } else {
                response.sendRedirect(request.getContextPath() + "/authormanagement?error=invalidaction");
            }
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/authormanagement?error=databaseerror");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/authormanagement");
    }
}



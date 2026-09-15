package com.example.kalaa;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class AddAuthorServlet extends HttpServlet {
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

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String bio = request.getParameter("bio");

        try {
            authorDAO.addAuthor(new Author(name, email, bio));
            response.sendRedirect(request.getContextPath() + "/authormanagement?success=authoradded");
        } catch (SQLException e) {
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



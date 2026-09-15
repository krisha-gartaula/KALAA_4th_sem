package com.example.kalaa;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AuthorManagementServlet extends HttpServlet {
    private AuthorDAO authorDAO;

    @Override
    public void init() throws ServletException {
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
            List<Author> authors = authorDAO.getAllAuthors();
            request.setAttribute("authors", authors);
            request.getRequestDispatcher("author.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Failed to load authors: " + e.getMessage());
            request.getRequestDispatcher("author.jsp").forward(request, response);
        }
    }
}



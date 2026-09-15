package com.example.kalaa;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public class AdminLoginServlet extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            User admin = userDAO.getAdminByUsernameAndPassword(username, password);

            if (admin != null) {
                HttpSession session = request.getSession();
                session.setAttribute("admin", admin);
                session.setAttribute("adminUsername", admin.getUsername());

                try {
                    StoryDAO storyDAO = new StoryDAO();
                    CategoryDAO categoryDAO = new CategoryDAO();
                    UserDAO userDAO = new UserDAO();
                    AuthorDAO authorDAO = new AuthorDAO();
                    session.setAttribute("countStories", storyDAO.getStoryCount());
                    session.setAttribute("countCategories", categoryDAO.getCategoryCount());
                    session.setAttribute("countUsers", userDAO.getUserCount());
                    session.setAttribute("countAuthors", authorDAO.getAuthorCount());
                } catch (SQLException ignored) {}

                response.sendRedirect(request.getContextPath() + "/admin.jsp");
            } else {
                response.sendRedirect(request.getContextPath() + "/adminlogin?error=invalidcredentials");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/adminlogin?error=databaseerror");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String error = request.getParameter("error");
        String errorMessage = null;
        if ("invalidcredentials".equals(error)) {
            errorMessage = "Invalid admin credentials";
        } else if ("databaseerror".equals(error)) {
            errorMessage = "Database error occurred";
        }
        renderLoginPage(request, response, errorMessage);
    }

    private void renderLoginPage(HttpServletRequest request, HttpServletResponse response, String errorMessage)
            throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        String contextPath = request.getContextPath();
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"en\">");
        out.println("<head>");
        out.println("  <meta charset=\"UTF-8\">");
        out.println("  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
        out.println("  <title>Admin Login - KALAA</title>");
        out.println("  <link rel=\"stylesheet\" href=\"" + contextPath + "/AdminLogin.css\">");
        out.println("</head>");
        out.println("<body>");
        out.println("  <div class=\"login-container\">");
        out.println("    <div class=\"login-card\">");
        out.println("      <h2>Admin Login</h2>");
        out.println("      <form action=\"" + contextPath + "/adminlogin\" method=\"post\">");
        out.println("        <div class=\"form-group\">");
        out.println("          <label for=\"username\">Username</label>");
        out.println("          <input type=\"text\" id=\"username\" name=\"username\" placeholder=\"Enter username\" required>");
        out.println("        </div>");
        out.println("        <div class=\"form-group\">");
        out.println("          <label for=\"password\">Password</label>");
        out.println("          <input type=\"password\" id=\"password\" name=\"password\" placeholder=\"Enter password\" required>");
        out.println("        </div>");
        out.println("        <button type=\"submit\" class=\"btn-login\">Login</button>");
        out.println("      </form>");
        if (errorMessage != null) {
            out.println("      <div style=\"color: red; margin-top: 10px; text-align: center;\">"
                    + escapeHtml(errorMessage) + "</div>");
        }
        out.println("    </div>");
        out.println("  </div>");
        out.println("</body>");
        out.println("</html>");
    }

    private String escapeHtml(String value) {
        return value.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;");
    }
}

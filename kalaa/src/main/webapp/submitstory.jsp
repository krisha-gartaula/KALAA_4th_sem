<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.example.kalaa.Category" %>
<%
    // Redirect guests to login
    if (session == null || session.getAttribute("user") == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Submit Your Story - Kalaa</title>
    <link rel="stylesheet" href="community.css">
</head>
<body>
<header class="header">
    <div class="hamburger" id="hamburger-menu" style="display:none;cursor:pointer;">&#9776;</div>
    <div class="logo">KALAA</div>
    <nav>
        <ul class="nav-menu">
            <li><a href="index.jsp">Home</a></li>
            <li><a href="community.jsp">Community</a></li>
            <li><a href="genre.jsp">Genre</a></li>
            <li><a href="Aboutus.jsp">About us</a></li>
            <li><a href="termandcondition.jsp">Terms and Conditions</a></li>
        </ul>
    </nav>
    <div class="auth-buttons">
        <span style="color: #28a745; font-weight: bold;">Welcome, <%= session.getAttribute("username") %></span>
        <a href="userlogout">Logout</a>
    </div>
</header>

<main class="submit-story-container">
    <section class="submit-section">
        <h1>Submit Your Story</h1>

        <% if(request.getParameter("error") != null) { %>
        <div style="color: red; margin-bottom: 10px; padding: 10px; background-color: #f8d7da; border: 1px solid #f5c6cb; border-radius: 4px;">
          <%= request.getParameter("error") %>
        </div>
        <% } %>

        <form action="submitstory" method="post" class="story-form">
            <div class="form-group">
                <label for="story-title">Title:</label><br>
                <input type="text" id="story-title" name="story-title" required style="width: 100%; padding: 8px; margin: 5px 0;">
            </div>
            
            <div class="form-group">
                <label for="category">Category:</label><br>
                <select id="category" name="category" required style="width: 100%; padding: 8px; margin: 5px 0;">
                    <option value="">Select a category</option>
                    <% 
                    List<Category> categories = (List<Category>) request.getAttribute("categories");
                    if (categories != null) {
                        for (Category category : categories) {
                    %>
                    <option value="<%= category.getCategoryId() %>"><%= category.getName() %></option>
                    <% 
                        }
                    }
                    %>
                </select>
            </div>
            
            <div class="form-group">
                <label for="story-content">Your Story:</label><br>
                <textarea id="story-content" name="story-content" rows="15" cols="50" required 
                         style="width: 100%; padding: 8px; margin: 5px 0; resize: vertical;"></textarea>
            </div>
            
            <button type="submit" class="submit-btn" style="background-color: #007bff; color: white; padding: 10px 20px; border: none; border-radius: 4px; cursor: pointer;">
                Submit Story
            </button>
        </form>
    </section>
</main>

<footer class="footer">
    <!-- Your footer here -->
</footer>

<script>
    const hamburger = document.getElementById('hamburger-menu');
    const navMenu = document.querySelector('.nav-menu');
    hamburger.addEventListener('click', function() {
        navMenu.classList.toggle('nav-open');
    });
    function handleResize() {
        if(window.innerWidth <= 768){
            hamburger.style.display = 'block';
        } else {
            hamburger.style.display = 'none';
            navMenu.classList.remove('nav-open');
        }
    }
    window.addEventListener('resize', handleResize);
    window.addEventListener('DOMContentLoaded', handleResize);
</script>
</body>
</html>

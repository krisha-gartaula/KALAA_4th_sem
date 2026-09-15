<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // Implicit session object
    boolean isLoggedIn = session != null && session.getAttribute("user") != null;
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>KALAA - Community</title>
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
    <div class="search-container">
        <input type="text" class="search-input" placeholder="SEARCH">
        <span>🔍</span>
    </div>
        <div class="auth-buttons">
        <% if (isLoggedIn) { %>
        <span style="color: #28a745; font-weight: bold;">Welcome, <%= session.getAttribute("username") %></span>
        <a href="userlogout">Logout</a>
        <% } else { %>
        <a href="login.jsp">Login</a>
        <a href="signup.jsp">Sign Up</a>
        <% } %>
    </div>
</header>

<main class="community-container">
    <section class="welcome-section">
        <h1>Welcome to the Kalaa Community</h1>
        <p>Connect with other storytellers, join fun writing challenges, and grow your creativity together.</p>
    </section>

    <section class="challenge-section">
        <h2>🏆 Monthly Story Challenge</h2>
        <p>This month’s theme: <strong>“Twists of Fate”</strong></p>
        <% if (isLoggedIn) { %>
        <a href="submitstory.jsp" class="join-btn">Submit Your Story</a>
        <% } else { %>
        <a href="login.jsp" class="join-btn">Login to submit your story</a>
        <% } %>
    </section>

    <!-- Add other sections like featured members here -->

</main>

<footer class="footer">
    <!-- Your existing footer content -->
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

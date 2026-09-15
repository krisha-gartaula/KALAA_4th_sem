<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% if (request.getAttribute("categories") == null && request.getAttribute("stories") == null) { %>
<jsp:forward page="/genres" />
<% return; } %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>KALAA - Genres</title>
    <link rel="stylesheet" href="genre.css">
</head>
<body>
    <header class="header">
        <div class="hamburger" id="hamburger-menu" style="display:none;cursor:pointer;">
            &#9776;
        </div>
        <div class="logo">KALAA</div>
        
         <nav>
            <ul class="nav-menu">
                <li><a href="index.jsp">Home</a></li>
                <li><a href="community.jsp">Community</a></li>
                <li><a href="<%= request.getContextPath() %>/genres">Genre</a></li>
                <li><a href="Aboutus.jsp">About us</a></li>
                <li><a href="termandcondition.jsp">Terms and Conditions</a></li>
            </ul>
        </nav>
        <div class="search-container">
            <input type="text" class="search-input" placeholder="SEARCH">
            <span>🔍</span>
        </div>

        <div class="auth-buttons">
            <a href="login.jsp">Login</a>
            <a href="signup.jsp">Sign Up</a>
        </div>
    </header>

    <main class="genre-container">
        <section class="welcome-section">
            <h1>Explore Stories by Genre</h1>
            <p>Discover stories published by admin, organized by category.</p>
        </section>

        <section class="genre-grid">
            <% 
            java.util.List<com.example.kalaa.Category> categories = (java.util.List<com.example.kalaa.Category>) request.getAttribute("categories");
            Integer activeCategoryId = (Integer) request.getAttribute("activeCategoryId");
            if (categories != null) {
                for (com.example.kalaa.Category cat : categories) {
            %>
            <div class="genre-card">
                <h2><%= cat.getName() %></h2>
                <p><%= cat.getDescription() != null ? cat.getDescription() : "" %></p>
                <a href="<%= request.getContextPath() %>/genres?categoryId=<%= cat.getCategoryId() %>" class="explore-btn">View</a>
            </div>
            <% 
                }
            }
            %>
        </section>

        <section class="genre-stories">
            <%
            java.util.List<com.example.kalaa.Story> stories = (java.util.List<com.example.kalaa.Story>) request.getAttribute("stories");
            String sectionTitle = activeCategoryId != null ? "Stories in Selected Category" : "Latest Stories";
            if (stories != null && !stories.isEmpty()) {
            %>
                <h2><%= sectionTitle %></h2>
                <div class="books-grid" style="display:grid;grid-template-columns:repeat(auto-fill,minmax(220px,1fr));gap:20px;margin-top:20px;">
                <% for (com.example.kalaa.Story s : stories) { %>
                    <div class="book-card" style="background:#fff7f0;border-radius:12px;padding:12px;box-shadow:0 4px 12px rgba(0,0,0,0.08);">
                        <div class="book-image" style="height:180px;margin-bottom:10px;">
                            <% if (s.getImagePath() != null && !s.getImagePath().isEmpty()) { %>
                                <img src="<%= s.getImagePath() %>" alt="<%= s.getTitle() %>" style="width:100%;height:100%;object-fit:cover;border-radius:8px;">
                            <% } else { %>
                                <div style="width:100%;height:100%;background:#e8dccf;border-radius:8px;display:flex;align-items:center;justify-content:center;color:#8b4513;">No cover</div>
                            <% } %>
                        </div>
                        <div class="book-info">
                            <h3 style="margin:0 0 6px;color:#8b4513;"><%= s.getTitle() %></h3>
                            <p class="author" style="margin:0;color:#666;">By <%= s.getAuthorName() != null ? s.getAuthorName() : "Admin" %></p>
                            <% if (s.getCategory() != null) { %>
                                <p style="margin:6px 0;color:#8b4513;font-size:0.9rem;"><%= s.getCategory() %></p>
                            <% } %>
                            <p class="description" style="margin:8px 0;color:#333;font-size:0.9rem;">
                                <%= s.getContent() != null && s.getContent().length() > 100 ? s.getContent().substring(0,100) + "..." : (s.getContent() != null ? s.getContent() : "") %>
                            </p>
                            <a href="<%= request.getContextPath() %>/read?storyId=<%= s.getStoryId() %>" class="explore-btn">Read Now</a>
                        </div>
                    </div>
                <% } %>
                </div>
            <% } else if (activeCategoryId != null) { %>
                <h2>Stories in Selected Category</h2>
                <p style="color:#8b4513;">No stories in this category yet.</p>
            <% } %>
        </section>
    </main>

    <footer class="footer">
        <div class="footer-content">
            <div class="footer-section">
                <h3>Categories</h3>
                <ul>
                    <li><a href="#fiction">Fiction</a></li>
                    <li><a href="#horror">Horror</a></li>
                </ul>
            </div>

            <div class="footer-section">
                <h3>Shopping</h3>
                <ul>
                    <li><a href="#payments">Payments</a></li>
                    <li><a href="#delivery">Delivery options</a></li>
                    <li><a href="#buyer">Buyer protection</a></li>
                </ul>
            </div>

            <div class="footer-section">
                <h3>Customer care</h3>
                <ul>
                    <li><a href="#help">Help center</a></li>
                    <li><a href="#terms">Terms & Conditions</a></li>
                    <li><a href="#privacy">Privacy policy</a></li>
                    <li><a href="#returns">Returns & refund</a></li>
                    <li><a href="#survey">Survey & feedback</a></li>
                </ul>
            </div>

            <div class="footer-section">
                <h3>Pages</h3>
                <ul>
                    <li><a href="#about">About us</a></li>
                    <li><a href="#shop">Shop</a></li>
                    <li><a href="#contact">Contact Us</a></li>
                    <li><a href="#services">Services</a></li>
                    <li><a href="#blog">Blog</a></li>
                </ul>
            </div>
        </div>

        <div class="social-icons">
            <a href="#" class="social-icon twitter">T</a>
            <a href="#" class="social-icon facebook">f</a>
            <a href="#" class="social-icon linkedin">in</a>
            <a href="#" class="social-icon instagram">📷</a>
        </div>
    </footer>
    <script>
    // Hamburger menu toggle for mobile
    const hamburger = document.getElementById('hamburger-menu');
    const navMenu = document.querySelector('.nav-menu');
    hamburger.addEventListener('click', function() {
        navMenu.classList.toggle('nav-open');
    });
    // Show hamburger only on small screens
    function handleResize() {
        if(window.innerWidth <= 768) {
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
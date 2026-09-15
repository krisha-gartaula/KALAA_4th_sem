<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.example.kalaa.User" %>
<%
    boolean isLoggedIn = session != null && session.getAttribute("user") != null;
    String username = (String) session.getAttribute("username");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>KALAA - Home</title>
    <link rel="stylesheet" href="home.css">
    
    <!-- Include Toastify -->
    <jsp:include page="includes/header.jsp" />
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
                <span style="color: #28a745; font-weight: bold;">Welcome, <%= username %></span>
                <a href="userlogout">Logout</a>
            <% } else { %>
                <a href="<%= request.getContextPath() %>/login.jsp">Login</a>
                <a href="<%= request.getContextPath() %>/signup.jsp">Sign Up</a>
            <% } %>
<%--            <a href="<%= request.getContextPath() %>/adminlogin" style="color: #ff6b6b; font-weight: bold;">Admin</a>--%>
        </div>
    </header>

    <main class="main-content">
        <section class="hero-section">
            <div class="welcome-section">
                <h1 class="welcome-title">HI,<br>WELCOME TO KALAA</h1>
                <p class="welcome-subtitle">
                    Home to people who love original stories, Kalaa has democratized 
                    storytelling for a new generation of diverse Gen Z writers and their fans.
                </p>
            </div>
            
           <div class="hero-image">
            <img src="./assests/kalaa.png" alt="KALAA decorative element" style="width: 500px; height: 350px;" />
            </div>

        </section>

        <section class="categories-section">
            <h2 className="categories-title">
            <a >Genre</a>
            </h2>            
            <div class="categories-grid">

                <div class="category-section">
                    <div class="category-header">Fantasy ></div>
                    <div class="books-grid">
                        <div class="book-cover">
                            <img src="https://tse1.mm.bing.net/th?id=OIP.n6YZ1ovtk1FR_hg4R92DJgHaM5&pid=Api&P=0&h=220" alt="Fantasy Book 1" />
                        </div>
                        <div class="book-cover">
                            <img src="https://images.unsplash.com/photo-1544716278-ca5e3f4abd8c?w=200&h=300&fit=crop&crop=center" alt="Fantasy Book 2" />
                        </div>
                        <div class="book-cover">
                            <img src="https://images.unsplash.com/photo-1481627834876-b7833e8f5570?w=200&h=300&fit=crop&crop=center" alt="Fantasy Book 3" />
                        </div>
                        <div class="book-cover">
                            <img src="https://images.unsplash.com/photo-1589829085413-56de8ae18c73?w=200&h=300&fit=crop&crop=center" alt="Fantasy Book 4" />
                        </div>
                    </div>
                </div>

                <!-- Horror Section -->
                <div class="category-section">
                    <div class="category-header horror">Horror ></div>
                    <div class="books-grid">
                        <div class="book-cover">
                            <img src="https://s3.scoopwhoop.com/anj/Horror_novels/83730625-7a70-4854-ba10-11edd8d30444.jpg" alt="Horror Book 1" />
                        </div>
                        <div class="book-cover">
                            <img src="https://miblart.com/wp-content/uploads/2020/10/2LTd4fOY.jpeg" alt="Horror Book 2" />
                        </div>
                        <div class="book-cover">
                            <img src="https://miblart.com/wp-content/uploads/2020/10/ZP6x4iTg-scaled.jpeg" alt="Horror Book 3" />
                        </div>
                        <div class="book-cover">
                            <img src="https://tse3.mm.bing.net/th?id=OIP.flCfO21DrBsZHZ0ZbKikJwHaLY&pid=Api&P=0&h=220" alt="Horror Book 4" />
                        </div>
                    </div>
                </div>
            </div>

            <div class="action-buttons">
                <a href="startreading.jsp" class="action-btn">Start Reading</a>
                <a href="startwriting.jsp" class="action-btn">Start Writing</a>
            </div>
        </section>
    </main>

    <!-- Footer -->
    <footer class="footer">
        <div class="footer-content">
            <div class="footer-section">
                <h3>Genre</h3>
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
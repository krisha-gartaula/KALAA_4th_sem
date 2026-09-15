<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>KALAA - About Us</title>
    <link rel="stylesheet" href="about.css"> 

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
                <li><a href="community.html">Community</a></li>
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
            <a href="login.jsp">Login</a>
            <a href="signup.jsp">Sign Up</a>
        </div>
    </header>

    <!-- Main Content -->
    <main class="main-content">
        <!-- About Header Section -->
        <section class="about-header">
            <h1 class="about-title">About Kalaa</h1>
            <p class="about-subtitle">
                Democratizing storytelling for a new generation of diverse Gen Z writers and their fans. We believe every voice deserves to be heard, every story deserves to be told.
            </p>
        </section>

        <!-- Stats Section -->
        <section class="stats-section">
            <button class="stat-button">👥 20k+ Writers</button>
            <button class="stat-button">📚 50k+ Writers</button>
        </section>

        <!-- Mission Section -->
        <section class="mission-section">
            <h2 class="mission-title">OUR MISSION</h2>
            <div class="mission-content">
                <div class="mission-text">
                    <p>At KALAA, we're on a mission to break down the barriers that have traditionally kept diverse voices out of mainstream storytelling. We provide a platform where emerging writers can share their authentic stories, connect with readers who crave fresh perspectives, and build sustainable creative careers.</p>
                    <a href="#write" class="start-writing-btn">Start Writing Today</a>
                </div>
                 <div class="mission-image">
                    <img src="https://hebbkx1anhila5yf.public.blob.vercel-storage.com/image-NKrcKF1RHZBZazJOHj1tDQk6DhHGKW.png" alt="Book illustration" style="width: 400px; height: auto;" />
                    </div>

            </div>
        </section>

        <!-- Call to Action Section -->
        <section class="cta-section">
            <h2 class="cta-title">Ready to Share Your Story ?</h2>
            <a href="#join" class="join-btn">Join Us</a>
        </section>
    </main>

    <!-- Footer -->
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
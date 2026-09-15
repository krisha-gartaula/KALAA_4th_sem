<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>KALAA - Terms and Conditions</title>
    <link rel="stylesheet" href="termandcondition.css">
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
            <a href="login.jsp">Login</a>
            <a href="signup.jsp">Sign Up</a>
        </div>
    </header>

    <main class="terms-container">
        <section class="welcome-section">
            <h1>Terms and Conditions</h1>
            <p>Please read these terms carefully before using our platform</p>
        </section>

        <section class="terms-content">
            <div class="terms-section">
                <h2>1. Acceptance of Terms</h2>
                <p>By accessing and using Kalaa, you agree to be bound by these Terms and Conditions. If you do not agree to these terms, please do not use our platform.</p>
            </div>

            <div class="terms-section">
                <h2>2. User Accounts</h2>
                <p>To access certain features of Kalaa, you must create an account. You are responsible for maintaining the confidentiality of your account information and for all activities that occur under your account.</p>
            </div>

            <div class="terms-section">
                <h2>3. Content Guidelines</h2>
                <p>Users are responsible for the content they post. Content must not:</p>
                <ul>
                    <li>Violate any laws or regulations</li>
                    <li>Infringe on intellectual property rights</li>
                    <li>Contain hate speech or discriminatory content</li>
                    <li>Include explicit or inappropriate material</li>
                </ul>
            </div>

            <div class="terms-section">
                <h2>4. Intellectual Property</h2>
                <p>All content on Kalaa, including but not limited to text, graphics, logos, and software, is the property of Kalaa or its content suppliers and is protected by international copyright laws.</p>
            </div>

            <div class="terms-section">
                <h2>5. Privacy Policy</h2>
                <p>Your use of Kalaa is also governed by our Privacy Policy. Please review our Privacy Policy to understand our practices.</p>
            </div>

            <div class="terms-section">
                <h2>6. Modifications to Terms</h2>
                <p>Kalaa reserves the right to modify these terms at any time. We will notify users of any material changes via email or through the platform.</p>
            </div>

            <div class="terms-section">
                <h2>7. Contact Information</h2>
                <p>If you have any questions about these Terms and Conditions, please contact us at support@kalaa.com</p>
            </div>
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
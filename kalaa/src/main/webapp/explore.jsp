<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>KALAA - Explore Books</title>
    <link rel="stylesheet" href="explore.css">
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

    <main class="explore-container">
        <section class="genre-header">
            <h1>Romance Books</h1>
            <p>Discover our collection of heartwarming romance stories</p>
        </section>

      

        <section class="books-grid">
            <div class="book-card">
                <div class="book-image">
                    <img src="assests/lovestory.jpg" alt="Book Cover">
                </div>
                <div class="book-info">
                    <h3>The Love Story</h3>
                    <p class="author">By Sarah Johnson</p>
                    <div class="rating">★★★★☆</div>
                    <p class="description">A beautiful tale of love and friendship that will warm your heart.</p>
                    <a href="read.html" class="read-btn">Read Now</a>
                </div>
            </div>

            <div class="book-card">
                <div class="book-image">
                    <img src="assests/Forever-Yours-400x600.jpg" alt="Book Cover">
                </div>
                <div class="book-info">
                    <h3>Forever Yours</h3>
                    <p class="author">By Michael Brown</p>
                    <div class="rating">★★★★★</div>
                    <p class="description">An epic romance that spans generations and continents.</p>
                    <a href="read.html" class="read-btn">Read Now</a>
                </div>
            </div>

            <div class="book-card">
                <div class="book-image">
                    <img src="assests/love in paris.jpg" alt="Book Cover">
                </div>
                <div class="book-info">
                    <h3>Love in Paris</h3>
                    <p class="author">By Emma Wilson</p>
                    <div class="rating">★★★★☆</div>
                    <p class="description">A romantic adventure set in the city of love.</p>
                    <a href="read.html" class="read-btn">Read Now</a>
                </div>
            </div>

            <div class="book-card">
                <div class="book-image">
                    <img src="assests/hearts desire.jpg" alt="Book Cover">
                </div>
                <div class="book-info">
                    <h3>Heart's Desire</h3>
                    <p class="author">By James Anderson</p>
                    <div class="rating">★★★★★</div>
                    <p class="description">A story of love, loss, and second chances.</p>
                    <a href="read.html" class="read-btn">Read Now</a>
                </div>
            </div>

            <div class="book-card">
                <div class="book-image">
                    <img src="assests/sweet romance.jpg" alt="Book Cover">
                </div>
                <div class="book-info">
                    <h3>Sweet Romance</h3>
                    <p class="author">By Lisa Chen</p>
                    <div class="rating">★★★★☆</div>
                    <p class="description">A sweet and charming love story that will make you smile.</p>
                    <a href="read.html" class="read-btn">Read Now</a>
                </div>
            </div>

            <div class="book-card">
                <div class="book-image">
                    <img src="assests/love letter.jpeg" alt="Book Cover">
                </div>
                <div class="book-info">
                    <h3>Love Letters</h3>
                    <p class="author">By David Miller</p>
                    <div class="rating">★★★★★</div>
                    <p class="description">A collection of love letters that tell a beautiful story.</p>
                    <a href="read.html" class="read-btn">Read Now</a>
                </div>
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
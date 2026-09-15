<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>KALAA - Signup</title>
  <link rel="stylesheet" href="main.css">
  
  <!-- Include Toastify -->
  <jsp:include page="includes/header.jsp" />
</head>
<body>
<div class="container">
  <!-- Welcome Section -->
  <div class="welcome-section">
    <div class="welcome-text">Welcome to KALAA!
      Don’t have an account?</div>
  </div>

  <!-- Signup Section -->
  <div class="signup-section">
    <div class="signup-container">
      <h1 class="signup-title">Sign Up</h1>
      <form action="login" method="post">
        <input type="hidden" name="action" value="add">

        <div class="form-group">
          <input type="text" class="form-input" name="username" placeholder="Username" required />
        </div>

        <div class="form-group">
          <input type="text" class="form-input" name="email" placeholder="Email" required />
        </div>

        <div class="form-group">
          <input type="password" class="form-input" name="password" placeholder="Password" required oninput="checkPasswordMatch()" />
        </div>

        <div class="form-group">
          <input type="password" class="form-input" name="confirmPassword" placeholder="Confirm Password" required oninput="checkPasswordMatch()" />
        </div>

        <button type="submit" class="signup-button" disabled>Sign Up</button>
      </form>

      <div class="social-buttons">
        <button class="social-button google">G</button>
        <button class="social-button facebook">f</button>
        <button class="social-button github">⚡</button>
      </div>

      <div class="login-link">
        Already have an account? <a href="login.jsp">Login here</a>
      </div>
    </div>
  </div>
</div>

<script>
  function checkPasswordMatch() {
    const password = document.querySelector('input[name="password"]').value;
    const confirmPassword = document.querySelector('input[name="confirmPassword"]').value;
    const submitButton = document.querySelector('.signup-button');
    if (password === confirmPassword && password !== "") {
      submitButton.disabled = false;
    } else {
      submitButton.disabled = true;
    }
  }
</script>
</body>
</html>
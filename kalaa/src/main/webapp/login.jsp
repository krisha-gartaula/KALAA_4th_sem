<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>KALAA - Login</title>
  <link rel="stylesheet" href="main.css">
  
  <!-- Include Toastify -->
  <jsp:include page="includes/header.jsp" />
</head>
<body>
<div class="container">
  <div class="welcome-section">
    <div class="welcome-text">Welcome to KALAA</div>
  </div>

  <div class="login-section">
    <div class="login-container">
      <h1 class="login-title">Login</h1>

      <!-- Display success or error message -->
      <%
        String success = request.getParameter("success");
        String error = request.getParameter("error");
        String message = "";
        String alertClass = "";
        if (success != null) {
          if ("registered".equals(success)) {
            message = "Registration successful! Please login.";
            alertClass = "alert-success";
          } else if ("passwordreset".equals(success)) {
            message = "Password reset successful! Please log in with your new password.";
            alertClass = "alert-success";
          }
        } else if (error != null) {
          message = "invalidcredentials".equals(error) ? "Invalid email or password." : "An error occurred. Please try again.";
          alertClass = "alert-danger";
        }
      %>

      <% if (!message.isEmpty()) { %>
        <div class="alert <%= alertClass %>" style="padding: 10px 14px; margin-bottom: 16px; border-radius: 6px; font-size: 14px; text-align: center;">
          <%= message %>
        </div>
      <% } %>

      <form action="<%= request.getContextPath() %>/login" method="post">
        <input type="hidden" name="action" value="login">

        <div class="form-group">
          <input type="email" class="form-input" name="email" placeholder="Email" required />
        </div>

        <div class="form-group">
          <div class="password-container">
            <input type="password" class="form-input" name="password" placeholder="Password" id="password" required />
            <span class="password-toggle">👁</span>
          </div>
        </div>

        <div class="remember-container">
          <input type="checkbox" class="remember-checkbox" id="remember" />
          <label for="remember" class="remember-label">Remember me</label>
        </div>

        <button type="submit" class="login-button">Login</button>
      </form>

      <div class="forgot-password">
        <a href="<%= request.getContextPath() %>/forgotpassword">Forgot password ?</a>
      </div>

      <div class="divider">
        <div class="divider-line"></div>
        <span class="divider-text">Or</span>
        <div class="divider-line"></div>
      </div>

      <div class="social-login">
        <button class="social-button google">G</button>
        <button class="social-button facebook">f</button>
        <button class="social-button github">⚡</button>
      </div>

      <div class="signup-link">
        <a href="signup.jsp">Don't have an account? Sign up</a>
      </div>

      <div class="footer-links">
        <a href="<%= request.getContextPath() %>/Aboutus.jsp">Privacy Policy</a>
        <a href="<%= request.getContextPath() %>/termandcondition.jsp">Terms of Service</a>
        <a href="<%= request.getContextPath() %>/Aboutus.jsp">Help</a>
      </div>
    </div>
  </div>
</div>

<!-- CSS for alert messages -->
<style>
  .message { padding: 10px; margin-bottom: 10px; text-align: center; }
  .success { color: green; }
  .error { color: red; }
  .alert-success { background-color: #d4edda; color: #155724; border: 1px solid #c3e6cb; }
  .alert-danger { background-color: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; }
</style>

<!-- Password toggle script -->
<script>
  const toggle = document.querySelector('.password-toggle');
  const password = document.querySelector('#password');
  toggle.addEventListener('click', () => {
    const type = password.getAttribute('type') === 'password' ? 'text' : 'password';
    password.setAttribute('type', type);
    toggle.textContent = type === 'password' ? '👁' : '👁‍🗨';
  });
</script>
</body>
</html>
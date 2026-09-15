<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>KALAA - Forgot Password</title>
  <link rel="stylesheet" href="main.css">
  
  <!-- Include Toastify / Header scripts -->
  <jsp:include page="includes/header.jsp" />
</head>
<body>
<div class="container">
  <div class="welcome-section">
    <div class="welcome-text">Forgot Your Password?</div>
  </div>

  <div class="login-section">
    <div class="login-container">
      <h1 class="login-title">Forgot Password</h1>

      <%
        String status = request.getParameter("status");
        String error = request.getParameter("error");
      %>

      <% if ("sent".equals(status)) { %>
        <div class="alert alert-success">
          If this email is registered with KALAA, a password reset link has been sent. Please check your inbox (and spam folder).
        </div>
      <% } else if ("invalidtoken".equals(error)) { %>
        <div class="alert alert-danger">
          The reset link was invalid, already used, or expired. Please request a new one below.
        </div>
      <% } else if ("emptyemail".equals(error)) { %>
        <div class="alert alert-danger">
          Please enter a valid email address.
        </div>
      <% } else if ("databaseerror".equals(error)) { %>
        <div class="alert alert-danger">
          A server error occurred. Please try again later.
        </div>
      <% } %>

      <p style="color: #666; font-size: 14px; margin-bottom: 20px; text-align: center; line-height: 1.5;">
        Enter your registered email address and we'll send you a secure link to reset your password.
      </p>

      <form action="<%= request.getContextPath() %>/forgotpassword" method="post">
        <div class="form-group">
          <input type="email" class="form-input" name="email" placeholder="Enter your registered email" required />
        </div>

        <button type="submit" class="login-button">Send Reset Link</button>
      </form>

      <div class="signup-link" style="margin-top: 25px;">
        <a href="<%= request.getContextPath() %>/login.jsp">← Remember your password? Back to Login</a>
      </div>

      <div class="footer-links">
        <a href="<%= request.getContextPath() %>/Aboutus.jsp">Privacy Policy</a>
        <a href="<%= request.getContextPath() %>/termandcondition.jsp">Terms of Service</a>
        <a href="<%= request.getContextPath() %>/Aboutus.jsp">Help</a>
      </div>
    </div>
  </div>
</div>

<style>
  .alert {
    padding: 12px 16px;
    margin-bottom: 18px;
    border-radius: 6px;
    font-size: 14px;
    line-height: 1.5;
    text-align: center;
  }
  .alert-success {
    background-color: #d4edda;
    color: #155724;
    border: 1px solid #c3e6cb;
  }
  .alert-danger {
    background-color: #f8d7da;
    color: #721c24;
    border: 1px solid #f5c6cb;
  }
</style>
</body>
</html>

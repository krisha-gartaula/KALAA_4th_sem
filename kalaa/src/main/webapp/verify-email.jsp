<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
  String email = request.getParameter("email");
  if (email == null) {
    email = "";
  }
  String error = request.getParameter("error");
  String success = request.getParameter("success");
  String info = request.getParameter("info");
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>KALAA - Verify Your Email</title>
  <link rel="stylesheet" href="main.css">
  
  <jsp:include page="includes/header.jsp" />
</head>
<body>
<div class="container">
  <div class="welcome-section">
    <div class="welcome-text">Verify Your Account</div>
  </div>

  <div class="login-section">
    <div class="login-container">
      <h1 class="login-title">Email Verification</h1>

      <% if ("invalidcode".equals(error)) { %>
        <div class="alert alert-danger">
          Invalid or expired verification code. Please try again or request a new code.
        </div>
      <% } else if ("emptycode".equals(error)) { %>
        <div class="alert alert-danger">
          Please enter the 6-digit verification code.
        </div>
      <% } else if ("unverified".equals(error)) { %>
        <div class="alert alert-warning">
          Please verify your email address before logging in. A new code has been sent to your inbox.
        </div>
      <% } else if ("alreadyregistered".equals(info)) { %>
        <div class="alert alert-info">
          This email was registered but not yet verified. A fresh verification code was sent to your email!
        </div>
      <% } else if ("resent".equals(success)) { %>
        <div class="alert alert-success">
          A new 6-digit verification code has been dispatched to your email address.
        </div>
      <% } else { %>
        <div class="alert alert-info">
          We sent a 6-digit verification code to:<br>
          <strong><%= email.isEmpty() ? "your email" : email %></strong>
        </div>
      <% } %>

      <p style="color: #666; font-size: 14px; margin-bottom: 20px; text-align: center; line-height: 1.5;">
        Check your inbox (and spam/junk folder) for the 6-digit OTP and enter it below.
      </p>

      <form action="<%= request.getContextPath() %>/verifyemail" method="post">
        <input type="hidden" name="action" value="verify">
        <input type="hidden" name="email" value="<%= email %>">

        <div class="form-group">
          <input 
            type="text" 
            class="form-input" 
            name="code" 
            placeholder="000000" 
            maxlength="6" 
            pattern="[0-9]{6}" 
            required 
            autofocus 
            style="text-align: center; font-size: 26px; letter-spacing: 10px; font-weight: 700; height: 52px;" 
            oninput="this.value = this.value.replace(/[^0-9]/g, '')"
          />
        </div>

        <button type="submit" class="login-button">Verify Email</button>
      </form>

      <!-- Resend Code Form -->
      <form action="<%= request.getContextPath() %>/verifyemail" method="post" style="margin-top: 15px; text-align: center;">
        <input type="hidden" name="action" value="resend">
        <input type="hidden" name="email" value="<%= email %>">
        <p style="font-size: 13px; color: #777;">
          Didn't receive the code? 
          <button type="submit" style="background: none; border: none; color: #0066cc; cursor: pointer; text-decoration: underline; font-size: 13px; padding: 0;">
            Resend Code
          </button>
        </p>
      </form>

      <div class="signup-link" style="margin-top: 20px;">
        <a href="<%= request.getContextPath() %>/login.jsp">← Back to Login</a>
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
  .alert-info {
    background-color: #d1ecf1;
    color: #0c5460;
    border: 1px solid #bee5eb;
  }
  .alert-warning {
    background-color: #fff3cd;
    color: #856404;
    border: 1px solid #ffeeba;
  }
</style>
</body>
</html>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
  String token = (String) request.getAttribute("token");
  if (token == null) {
    token = request.getParameter("token");
  }
  if (token == null) {
    token = "";
  }
  String email = (String) request.getAttribute("email");
  if (email == null) {
    email = request.getParameter("email");
  }
  if (email == null) {
    email = "";
  }
  String status = request.getParameter("status");
  String error = request.getParameter("error");
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>KALAA - Reset Password</title>
  <link rel="stylesheet" href="main.css">
  
  <jsp:include page="includes/header.jsp" />
</head>
<body>
<div class="container">
  <div class="welcome-section">
    <div class="welcome-text">Create New Password</div>
  </div>

  <div class="login-section">
    <div class="login-container">
      <h1 class="login-title">Reset Password</h1>

      <% if ("sent".equals(status)) { %>
        <div class="alert alert-success">
          We sent a 6-digit reset code to <%= email.isEmpty() ? "your email" : ("<strong>" + email + "</strong>") %>. Enter it below.
        </div>
      <% } else if ("invalidcode".equals(error) || "invalidtoken".equals(error)) { %>
        <div class="alert alert-danger">
          The reset code is invalid, already used, or has expired. Please <a href="<%= request.getContextPath() %>/forgotpassword" style="color: #721c24; text-decoration: underline;">request a new one</a>.
        </div>
      <% } else if ("emptypassword".equals(error)) { %>
        <div class="alert alert-danger">Please enter your new password.</div>
      <% } else if ("shortpassword".equals(error)) { %>
        <div class="alert alert-danger">Password must be at least 6 characters long.</div>
      <% } else if ("mismatch".equals(error)) { %>
        <div class="alert alert-danger">Passwords do not match. Please try again.</div>
      <% } else if ("databaseerror".equals(error)) { %>
        <div class="alert alert-danger">A server error occurred. Please try again.</div>
      <% } %>

      <p style="color: #666; font-size: 14px; margin-bottom: 20px; text-align: center; line-height: 1.5;">
        Enter the 6-digit code sent to your email along with your new password.
      </p>

      <form action="<%= request.getContextPath() %>/resetpassword" method="post" id="resetForm">
        
        <!-- 6-digit Reset Code -->
        <div class="form-group">
          <label style="display: block; font-size: 13px; color: #555; margin-bottom: 5px; font-weight: 500;">6-Digit Reset Code</label>
          <input 
            type="text" 
            class="form-input" 
            name="token" 
            id="resetToken" 
            value="<%= token %>" 
            placeholder="Enter 6-digit code" 
            maxlength="6" 
            required 
            style="text-align: center; font-size: 20px; letter-spacing: 6px; font-weight: bold;" 
            oninput="this.value = this.value.replace(/[^0-9]/g, '')"
          />
        </div>

        <div class="form-group">
          <div class="password-container">
            <input type="password" class="form-input" name="password" id="newPassword" placeholder="New Password (min 6 chars)" required minlength="6" oninput="validatePasswords()" />
            <span class="password-toggle" onclick="toggleVisibility('newPassword', this)">👁</span>
          </div>
        </div>

        <div class="form-group">
          <div class="password-container">
            <input type="password" class="form-input" name="confirmPassword" id="confirmPassword" placeholder="Confirm New Password" required minlength="6" oninput="validatePasswords()" />
            <span class="password-toggle" onclick="toggleVisibility('confirmPassword', this)">👁</span>
          </div>
        </div>

        <div id="matchMessage" style="font-size: 13px; margin-top: -10px; margin-bottom: 15px; text-align: left;"></div>

        <button type="submit" class="login-button" id="submitBtn">Update Password</button>
      </form>

      <div class="signup-link" style="margin-top: 25px;">
        <a href="<%= request.getContextPath() %>/forgotpassword">Didn't get a code? Request new code</a>
        <br><br>
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
</style>

<script>
  function toggleVisibility(inputId, icon) {
    const input = document.getElementById(inputId);
    if (input.type === 'password') {
      input.type = 'text';
      icon.textContent = '👁‍🗨';
    } else {
      input.type = 'password';
      icon.textContent = '👁';
    }
  }

  function validatePasswords() {
    const pass = document.getElementById('newPassword').value;
    const confirm = document.getElementById('confirmPassword').value;
    const msg = document.getElementById('matchMessage');
    const btn = document.getElementById('submitBtn');

    if (!confirm) {
      msg.textContent = '';
      btn.disabled = false;
      return;
    }

    if (pass === confirm) {
      if (pass.length < 6) {
        msg.textContent = 'Password must be at least 6 characters.';
        msg.style.color = '#dc3545';
        btn.disabled = true;
      } else {
        msg.textContent = 'Passwords match!';
        msg.style.color = '#28a745';
        btn.disabled = false;
      }
    } else {
      msg.textContent = 'Passwords do not match.';
      msg.style.color = '#dc3545';
      btn.disabled = true;
    }
  }
</script>
</body>
</html>

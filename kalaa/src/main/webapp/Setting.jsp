<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    // Check if admin is logged in
    if (session.getAttribute("admin") == null) {
        response.sendRedirect(request.getContextPath() + "/adminlogin");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Settings - KALAA Admin Panel</title>
  <link rel="stylesheet" href="Setting.css">
  <link rel="stylesheet" href="Admin.css">
</head>
<body>
  <div class="sidebar">
    <h2>KALAA</h2>
    <a href="<%= request.getContextPath() %>/admin.jsp">Dashboard</a>
    <a href="<%= request.getContextPath() %>/storymanagement">Story Management</a>
    <a href="<%= request.getContextPath() %>/categorymanagement">Category Management</a>
    <a href="<%= request.getContextPath() %>/usermanagement">User Management</a>
    <a href="<%= request.getContextPath() %>/Setting.jsp" class="active">Settings</a>
  </div>

  <div class="main-content">
    <div class="topbar">
      <h1>Settings</h1>
      <div class="admin-info">
        Welcome, <%= session.getAttribute("adminUsername") != null ? session.getAttribute("adminUsername") : "Admin" %>
        <button class="btn-logout" onclick="logout()">Logout</button>
      </div>
    </div>

    <div class="form-container">
      <h2>Change Admin Password</h2>
      
      <!-- Display success/error messages -->
      <% if (request.getAttribute("success") != null) { %>
        <div style="color: green; margin-bottom: 20px; padding: 10px; background-color: #d4edda; border: 1px solid #c3e6cb; border-radius: 4px;">
          <%= request.getAttribute("success") %>
        </div>
      <% } %>
      
      <% if (request.getAttribute("error") != null) { %>
        <div style="color: red; margin-bottom: 20px; padding: 10px; background-color: #f8d7da; border: 1px solid #f5c6cb; border-radius: 4px;">
          <%= request.getAttribute("error") %>
        </div>
      <% } %>
      
      <form action="adminpasswordchange" method="post">
        <div class="form-group">
          <label for="currentPassword">Current Password</label>
          <input type="password" id="currentPassword" name="currentPassword" placeholder="Enter current password" required>
        </div>
        <div class="form-group">
          <label for="newPassword">New Password</label>
          <input type="password" id="newPassword" name="newPassword" placeholder="Enter new password" required minlength="6">
        </div>
        <div class="form-group">
          <label for="confirmPassword">Confirm New Password</label>
          <input type="password" id="confirmPassword" name="confirmPassword" placeholder="Confirm new password" required minlength="6">
        </div>
        <button type="submit" class="btn-submit">Change Password</button>
      </form>
    </div>
  </div>

<script>
  function logout() {
    if (confirm("Are you sure you want to logout?")) {
      window.location.href = "<%= request.getContextPath() %>/logout"; 
    }
  }

  // Client-side password validation
  document.addEventListener('DOMContentLoaded', function() {
    const form = document.querySelector('form');
    const newPassword = document.getElementById('newPassword');
    const confirmPassword = document.getElementById('confirmPassword');

    form.addEventListener('submit', function(e) {
      // Check if passwords match
      if (newPassword.value !== confirmPassword.value) {
        e.preventDefault();
        alert('New password and confirm password do not match!');
        confirmPassword.focus();
        return false;
      }

      // Check minimum length
      if (newPassword.value.length < 6) {
        e.preventDefault();
        alert('New password must be at least 6 characters long!');
        newPassword.focus();
        return false;
      }

      // Check if new password is different from current password
      const currentPassword = document.getElementById('currentPassword').value;
      if (newPassword.value === currentPassword) {
        e.preventDefault();
        alert('New password must be different from current password!');
        newPassword.focus();
        return false;
      }
    });

    // Real-time password confirmation validation
    confirmPassword.addEventListener('input', function() {
      if (this.value !== newPassword.value) {
        this.setCustomValidity('Passwords do not match');
      } else {
        this.setCustomValidity('');
      }
    });
  });
</script>

</body>
</html>
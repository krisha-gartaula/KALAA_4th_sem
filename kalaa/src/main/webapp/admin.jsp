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
  <title>KALAA Admin Panel</title>
    <link rel="stylesheet" href="Admin.css">

</head>
<body>
  <div class="sidebar">
    <h2>KALAA</h2>
    <a href="<%= request.getContextPath() %>/admin.jsp" class="active">Dashboard</a>
    <a href="<%= request.getContextPath() %>/storymanagement">Story Management</a>
    <a href="<%= request.getContextPath() %>/authormanagement">Author Management</a>
    <a href="<%= request.getContextPath() %>/categorymanagement">Category Management</a>
    <a href="<%= request.getContextPath() %>/usermanagement">User Management</a>
    <a href="<%= request.getContextPath() %>/Setting.jsp">Settings</a>
  </div>

  <div class="main-content">
    <div class="topbar">
      <h1>Admin Dashboard</h1>
      <div class="admin-info">
        Welcome, <%= session.getAttribute("adminUsername") != null ? session.getAttribute("adminUsername") : "Admin" %>
        <button class="btn-logout" onclick="logout()" style="margin-left: 10px; padding: 5px 10px; background-color: #dc3545; color: white; border: none; border-radius: 4px; cursor: pointer;">Logout</button>
      </div>
    </div>

    <div class="cards">
      <div class="card">
        <h3>Total Stories</h3>
        <p><%= session.getAttribute("countStories") != null ? session.getAttribute("countStories") : 0 %></p>
      </div>
      <div class="card">
        <h3>Categories</h3>
        <p><%= session.getAttribute("countCategories") != null ? session.getAttribute("countCategories") : 0 %></p>
      </div>
      <div class="card">
        <h3>Users</h3>
        <p><%= session.getAttribute("countUsers") != null ? session.getAttribute("countUsers") : 0 %></p>
      </div>
      <div class="card">
        <h3>Authors</h3>
        <p><%= session.getAttribute("countAuthors") != null ? session.getAttribute("countAuthors") : 0 %></p>
      </div>
    </div>
  </div>

<script>
  function logout() {
    if (confirm("Are you sure you want to logout?")) {
      window.location.href = "<%= request.getContextPath() %>/logout"; 
    }
  }
</script>

</body>
</html>
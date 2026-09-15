<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.kalaa.Category" %>
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
  <title>Category Management - KALAA Admin Panel</title>
  <link rel="stylesheet" href="Category.css">
  <link rel="stylesheet" href="Admin.css">
</head>
<body>
  <div class="sidebar">
    <h2>KALAA</h2>
    <a href="<%= request.getContextPath() %>/admin.jsp">Dashboard</a>
    <a href="<%= request.getContextPath() %>/storymanagement">Story Management</a>
    <a href="<%= request.getContextPath() %>/authormanagement">Author Management</a>
    <a href="<%= request.getContextPath() %>/categorymanagement" class="active">Category Management</a>
    <a href="<%= request.getContextPath() %>/usermanagement">User Management</a>
    <a href="<%= request.getContextPath() %>/Setting.jsp">Settings</a>
  </div>

  <div class="main-content">
    <div class="topbar">
      <h1>Category Management</h1>
      <div class="admin-info">
        Welcome, <%= session.getAttribute("adminUsername") != null ? session.getAttribute("adminUsername") : "Admin" %>
        <button class="btn-logout" onclick="logout()" style="margin-left: 10px; padding: 5px 10px; background-color: #dc3545; color: white; border: none; border-radius: 4px; cursor: pointer;">Logout</button>
      </div>
    </div>

    <div class="form-container">
      <h2>Add New Category</h2>
      <% if (request.getParameter("success") != null) { %>
        <div style="color: green; margin-bottom: 10px; padding: 10px; background-color: #d4edda; border: 1px solid #c3e6cb; border-radius: 4px;">
          <%= request.getParameter("success") %>
        </div>
      <% } %>
      <% if (request.getParameter("error") != null) { %>
        <div style="color: red; margin-bottom: 10px; padding: 10px; background-color: #f8d7da; border: 1px solid #f5c6cb; border-radius: 4px;">
          <%= request.getParameter("error") %>
        </div>
      <% } %>
      <form action="addcategory" method="post">
        <div class="form-group">
          <label for="catName">Category Name</label>
          <input type="text" id="catName" name="name" placeholder="Enter category name" required>
        </div>
        <div class="form-group">
          <label for="description">Description</label>
          <textarea id="description" name="description" placeholder="Write a brief description..." required></textarea>
        </div>
        <button type="submit" class="btn-submit">Add Category</button>
      </form>
    </div>

    <div class="table-container">
      <h2>Existing Categories</h2>
      <table>
        <thead>
          <tr>
            <th>Category Name</th>
            <th>Description</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          <% 
          List<Category> categories = (List<Category>) request.getAttribute("categories");
          if (categories != null && !categories.isEmpty()) {
            for (Category category : categories) {
          %>
          <tr>
            <td><%= category.getName() %></td>
            <td><%= category.getDescription() %></td>
            <td>
              <button class="btn-edit">Edit</button>
              <button class="btn-delete">Delete</button>
            </td>
          </tr>
          <% 
            }
          } else {
          %>
          <tr>
            <td colspan="3" style="text-align: center;">No categories found</td>
          </tr>
          <% } %>
        </tbody>
      </table>
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

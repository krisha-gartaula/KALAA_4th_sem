<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.kalaa.User" %>
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
  <title>User Management - KALAA Admin Panel</title>
  <link rel="stylesheet" href="User.css">
  <link rel="stylesheet" href="Admin.css">
</head>
<body>
  <div class="sidebar">
    <h2>KALAA</h2>
    <a href="<%= request.getContextPath() %>/admin.jsp">Dashboard</a>
    <a href="<%= request.getContextPath() %>/storymanagement">Story Management</a>
    <a href="<%= request.getContextPath() %>/authormanagement">Author Management</a>
    <a href="<%= request.getContextPath() %>/categorymanagement">Category Management</a>
    <a href="<%= request.getContextPath() %>/usermanagement" class="active">User Management</a>
    <a href="<%= request.getContextPath() %>/Setting.jsp">Settings</a>
  </div>

  <div class="main-content">
    <div class="topbar">
      <h1>User Management</h1>
      <div class="admin-info">
        Welcome, <%= session.getAttribute("adminUsername") != null ? session.getAttribute("adminUsername") : "Admin" %>
        <button class="btn-logout" onclick="logout()" style="margin-left: 10px; padding: 5px 10px; background-color: #dc3545; color: white; border: none; border-radius: 4px; cursor: pointer;">Logout</button>
      </div>
    </div>

    <div class="form-container">
      <h2>Add New User</h2>
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
      <form action="adduser" method="post">
        <div class="form-group">
          <label for="username">Username</label>
          <input type="text" id="username" name="username" placeholder="Enter username" required>
        </div>
        <div class="form-group">
          <label for="email">Email</label>
          <input type="email" id="email" name="email" placeholder="Enter email" required>
        </div>
        <div class="form-group">
          <label for="role">Role</label>
          <select id="role" name="role" required>
            <option value="">Select role</option>
            <option value="admin">Admin</option>
            <option value="user">User</option>
          </select>
        </div>
        <div class="form-group">
          <label for="password">Password</label>
          <input type="password" id="password" name="password" placeholder="Enter password" required>
        </div>
        <button type="submit" class="btn-submit">Add User</button>
      </form>
    </div>

    <div class="table-container">
      <h2>Existing Users</h2>
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Username</th>
            <th>Email</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          <% 
          List<User> users = (List<User>) request.getAttribute("users");
          if (users != null && !users.isEmpty()) {
            for (User user : users) {
          %>
          <tr>
            <td><%= user.getId() %></td>
            <td><%= user.getUsername() %></td>
            <td><%= user.getEmail() %></td>
            <td>
              <button class="btn-edit" onclick="editUser(<%= user.getId() %>, '<%= user.getUsername() %>', '<%= user.getEmail() %>')">Edit</button>
              <button class="btn-delete" onclick="deleteUser(<%= user.getId() %>, '<%= user.getUsername() %>')">Delete</button>
            </td>
          </tr>
          <% 
            }
          } else {
          %>
          <tr>
            <td colspan="4" style="text-align: center;">No users found</td>
          </tr>
          <% } %>
        </tbody>
      </table>
    </div>
  </div>

  <!-- Edit User Modal -->
  <div id="editUserModal" class="modal" style="display: none;">
    <div class="modal-content">
      <span class="close" onclick="closeEditModal()">&times;</span>
      <h2>Edit User</h2>
      <form id="editUserForm" action="edituser" method="post">
        <input type="hidden" name="action" value="update">
        <input type="hidden" name="userId" id="editUserId">
        
        <div class="form-group">
          <label for="editUsername">Username</label>
          <input type="text" id="editUsername" name="username" required>
        </div>
        
        <div class="form-group">
          <label for="editEmail">Email</label>
          <input type="email" id="editEmail" name="email" required>
        </div>
        
        <div class="form-group">
          <label for="editPassword">Password</label>
          <input type="password" id="editPassword" name="password" required>
        </div>
        
        <button type="submit" class="btn-submit">Update User</button>
      </form>
    </div>
  </div>

<script>
  function logout() {
    if (confirm("Are you sure you want to logout?")) {
      window.location.href = "<%= request.getContextPath() %>/logout"; 
    }
  }

  function editUser(id, username, email) {
    document.getElementById('editUserId').value = id;
    document.getElementById('editUsername').value = username;
    document.getElementById('editEmail').value = email;
    document.getElementById('editUserModal').style.display = 'block';
  }

  function deleteUser(id, username) {
    if (confirm('Are you sure you want to delete user "' + username + '"?')) {
      const form = document.createElement('form');
      form.method = 'POST';
      form.action = 'edituser';
      
      const actionInput = document.createElement('input');
      actionInput.type = 'hidden';
      actionInput.name = 'action';
      actionInput.value = 'delete';
      
      const userIdInput = document.createElement('input');
      userIdInput.type = 'hidden';
      userIdInput.name = 'userId';
      userIdInput.value = id;
      
      form.appendChild(actionInput);
      form.appendChild(userIdInput);
      document.body.appendChild(form);
      form.submit();
    }
  }

  function closeEditModal() {
    document.getElementById('editUserModal').style.display = 'none';
  }

  // Close modal when clicking outside
  window.onclick = function(event) {
    const modal = document.getElementById('editUserModal');
    if (event.target == modal) {
      modal.style.display = 'none';
    }
  }
</script>

<style>
.modal {
  position: fixed;
  z-index: 1000;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0,0,0,0.5);
}

.modal-content {
  background-color: #fefefe;
  margin: 15% auto;
  padding: 20px;
  border: 1px solid #888;
  width: 50%;
  border-radius: 8px;
}

.close {
  color: #aaa;
  float: right;
  font-size: 28px;
  font-weight: bold;
  cursor: pointer;
}

.close:hover {
  color: black;
}
</style>

</body>
</html>

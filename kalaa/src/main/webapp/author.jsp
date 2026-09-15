<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.kalaa.Author" %>
<%
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
  <title>Author Management - KALAA Admin Panel</title>
  <link rel="stylesheet" href="Category.css">
  <link rel="stylesheet" href="Admin.css">
</head>
<body>
<div class="sidebar">
  <h2>KALAA</h2>
  <a href="<%= request.getContextPath() %>/admin.jsp">Dashboard</a>
  <a href="<%= request.getContextPath() %>/storymanagement">Story Management</a>
  <a href="<%= request.getContextPath() %>/authormanagement"class="active">Author Management</a>
  <a href="<%= request.getContextPath() %>/categorymanagement" >Category Management</a>
  <a href="<%= request.getContextPath() %>/usermanagement">User Management</a>
  <a href="<%= request.getContextPath() %>/Setting.jsp">Settings</a>
</div>

<div class="main-content">
  <div class="topbar">
    <h1>Author Management</h1>
    <div class="admin-info">
      Welcome, <%= session.getAttribute("adminUsername") != null ? session.getAttribute("adminUsername") : "Admin" %>
      <button class="btn-logout" onclick="logout()" style="margin-left: 10px; padding: 5px 10px; background-color: #dc3545; color: white; border: none; border-radius: 4px; cursor: pointer;">Logout</button>
    </div>
  </div>

    <div class="form-container">
      <h2>Add New Author</h2>
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
      <form action="addauthor" method="post">
        <div class="form-group">
          <label for="name">Name</label>
          <input type="text" id="name" name="name" placeholder="Enter author name" required>
        </div>
        <div class="form-group">
          <label for="email">Email</label>
          <input type="email" id="email" name="email" placeholder="Enter author email" required>
        </div>
        <div class="form-group">
          <label for="bio">Bio</label>
          <textarea id="bio" name="bio" placeholder="Short bio..."></textarea>
        </div>
        <button type="submit" class="btn-submit">Add Author</button>
      </form>
    </div>

    <div class="table-container">
      <h2>Existing Authors</h2>
      <table>
        <thead>
          <tr>
            <th>Name</th>
            <th>Email</th>
            <th>Bio</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          <%
            List<Author> authors = (List<Author>) request.getAttribute("authors");
            if (authors != null && !authors.isEmpty()) {
              for (Author a : authors) {
          %>
          <tr>
            <td><%= a.getName() %></td>
            <td><%= a.getEmail() %></td>
            <td><%= a.getBio() != null ? a.getBio() : "-" %></td>
            <td>
              <button class="btn-edit" onclick="openEdit(<%= a.getAuthorId() %>,'<%= a.getName() %>','<%= a.getEmail() %>','<%= a.getBio() != null ? a.getBio().replace("'","\\'") : "" %>')">Edit</button>
              <button class="btn-delete" onclick="deleteAuthor(<%= a.getAuthorId() %>)">Delete</button>
            </td>
          </tr>
          <%
              }
            } else {
          %>
          <tr><td colspan="4" style="text-align:center;">No authors found</td></tr>
          <% } %>
        </tbody>
      </table>
    </div>
  </div>

  <div id="editModal" class="modal" style="display:none;">
    <div class="modal-content">
      <span class="close" onclick="closeEdit()">&times;</span>
      <h2>Edit Author</h2>
      <form id="editForm" method="post" action="editauthor">
        <input type="hidden" name="action" value="update">
        <input type="hidden" name="authorId" id="editId">
        <div class="form-group">
          <label for="editName">Name</label>
          <input type="text" id="editName" name="name" required>
        </div>
        <div class="form-group">
          <label for="editEmail">Email</label>
          <input type="email" id="editEmail" name="email" required>
        </div>
        <div class="form-group">
          <label for="editBio">Bio</label>
          <textarea id="editBio" name="bio"></textarea>
        </div>
        <button type="submit" class="btn-submit">Update</button>
      </form>
    </div>
  </div>

  <script>
    function openEdit(id, name, email, bio) {
      document.getElementById('editId').value = id;
      document.getElementById('editName').value = name;
      document.getElementById('editEmail').value = email;
      document.getElementById('editBio').value = bio || '';
      document.getElementById('editModal').style.display = 'block';
    }
    function closeEdit() { document.getElementById('editModal').style.display = 'none'; }
    function deleteAuthor(id) {
      if (!confirm('Delete this author?')) return;
      const form = document.createElement('form');
      form.method = 'post';
      form.action = 'editauthor';
      form.innerHTML = '<input type="hidden" name="action" value="delete">' +
                       '<input type="hidden" name="authorId" value="' + id + '">';
      document.body.appendChild(form);
      form.submit();
    }
    function logout() {
      if (confirm("Are you sure you want to logout?")) {
        window.location.href = "<%= request.getContextPath() %>/logout";
      }
    }
  </script>
</body>
</html>



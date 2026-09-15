<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.kalaa.Story" %>
<%@ page import="com.example.kalaa.Category" %>
<%@ page import="com.example.kalaa.Author" %>
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
  <title>Story Management - KALAA Admin Panel</title>
<%--  <link rel="stylesheet" href="Story.css">--%>
  <link rel="stylesheet" href="Admin.css">
  <link rel="stylesheet" href="Category.css">

</head>
<body>
  <div class="sidebar">
    <h2>KALAA</h2>
    <a href="<%= request.getContextPath() %>/admin.jsp">Dashboard</a>
    <a href="<%= request.getContextPath() %>/storymanagement" class="active">Story Management</a>
    <a href="<%= request.getContextPath() %>/authormanagement">Author Management</a>
    <a href="<%= request.getContextPath() %>/categorymanagement">Category Management</a>
    <a href="<%= request.getContextPath() %>/usermanagement">User Management</a>
    <a href="<%= request.getContextPath() %>/Setting.jsp">Settings</a>
  </div>

  <div class="main-content">
    <div class="topbar">
      <h1>Story Management</h1>
      <div class="admin-info">
        Welcome, <%= session.getAttribute("adminUsername") != null ? session.getAttribute("adminUsername") : "Admin" %>
        <button class="btn-logout" onclick="logout()" style="margin-left: 10px; padding: 5px 10px; background-color: #dc3545; color: white; border: none; border-radius: 4px; cursor: pointer;">Logout</button>
      </div>
    </div>

    <div class="form-container">
      <h2>Add New Story</h2>
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
      <form action="addstory" method="post" enctype="multipart/form-data">
        <div class="form-group">
          <label for="title">Story Title</label>
          <input type="text" id="title" name="title" placeholder="Enter story title" required>
        </div>
        <div class="form-group">
          <label for="author_id">Author</label>
          <select id="author_id" name="author_id" required>
            <option value="">Select author</option>
            <% 
            java.util.List<com.example.kalaa.Author> authors = (java.util.List<com.example.kalaa.Author>) request.getAttribute("authors");
            if (authors == null) {
              try { authors = new com.example.kalaa.AuthorDAO().getAllAuthors(); } catch (Exception e) { authors = null; }
            }
            if (authors != null) {
              for (com.example.kalaa.Author a : authors) {
            %>
            <option value="<%= a.getAuthorId() %>"><%= a.getName() %></option>
            <% 
              }
            }
            %>
          </select>
        </div>
        <div class="form-group">
          <label for="category">Category</label>
          <select id="category" name="category" required>
            <option value="">Select category</option>
            <% 
            java.util.List<com.example.kalaa.Category> categories = (java.util.List<com.example.kalaa.Category>) request.getAttribute("categories");
            if (categories == null) {
              try { categories = new com.example.kalaa.CategoryDAO().getAllCategories(); } catch (Exception e) { categories = null; }
            }
            if (categories != null) {
              for (com.example.kalaa.Category category : categories) {
            %>
            <option value="<%= category.getCategoryId() %>"><%= category.getName() %></option>
            <% 
              }
            }
            %>
          </select>
        </div>
        <div class="form-group">
          <label for="content">Story Content</label>
          <textarea id="content" name="content" rows="4" placeholder="Enter story content"></textarea>
        </div>
        <div class="form-group">
          <label for="image">Story Cover Image</label>
          <input type="file" id="image" name="image" accept="image/jpeg,image/png,image/gif,image/webp">
          <small style="color:#8b4513;">Optional. JPG, PNG, GIF, or WEBP up to 5 MB.</small>
        </div>
        <button type="submit" class="btn btn-submit">Add Story</button>
      </form>
    </div>

    <div class="table-container">
      <h2>Existing Stories</h2>
      <table>
        <thead>
          <tr>
            <th>Image</th>
            <th>Title</th>
            <th>Author</th>
            <th>Category</th>
            <th>Content</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          <% 
          List<Story> stories = (List<Story>) request.getAttribute("stories");
          if (stories != null && !stories.isEmpty()) {
            for (Story story : stories) {
          %>
          <tr>
            <td>
              <% if (story.getImagePath() != null && !story.getImagePath().isEmpty()) { %>
                <img src="<%= story.getImagePath() %>" alt="<%= story.getTitle() %>" style="width:60px;height:80px;object-fit:cover;border-radius:6px;">
              <% } else { %>
                <span style="color:#999;">No image</span>
              <% } %>
            </td>
            <td><%= story.getTitle() %></td>
            <td><%= story.getAuthorName() != null ? story.getAuthorName() : ("ID: " + story.getAuthorId()) %></td>
            <td><%= story.getCategory() != null ? story.getCategory() : "-" %></td>
            <td><%= story.getContent() != null && story.getContent().length() > 50 ? 
                     story.getContent().substring(0, 50) + "..." : 
                     (story.getContent() != null ? story.getContent() : "-") %></td>
            <td>
              <button class="btn-edit" onclick="openEditStory(
                <%= story.getStoryId() %>,
                '<%= story.getTitle().replace("'","\\'") %>',
                <%= story.getAuthorId() %>,
                '<%= story.getCategory() != null ? story.getCategory().replace("'","\\'") : "" %>',
                '<%= story.getContent() != null ? story.getContent().replace("'","\\'").replace("\n","\\n").replace("\r","") : "" %>',
                '<%= story.getImagePath() != null ? story.getImagePath().replace("'","\\'") : "" %>'
              )">Edit</button>
              <button class="btn-delete" onclick="deleteStory(<%= story.getStoryId() %>)">Delete</button>
            </td>
          </tr>
          <% 
            }
          } else {
          %>
          <tr>
            <td colspan="7" style="text-align: center;">No stories found</td>
          </tr>
          <% } %>
        </tbody>
      </table>
    </div>
  </div>

  <!-- Edit Story Modal -->
  <div id="editStoryModal" class="modal" style="display:none;">
    <div class="modal-content">
      <span class="close" onclick="closeEditStory()">&times;</span>
      <h2>Edit Story</h2>
      <form id="editStoryForm" method="post" action="editstory" enctype="multipart/form-data">
        <input type="hidden" name="action" value="update">
        <input type="hidden" name="storyId" id="editStoryId">

        <div class="form-group">
          <label for="editTitle">Title</label>
          <input type="text" id="editTitle" name="title" required>
        </div>

        <div class="form-group">
          <label for="editAuthor">Author</label>
          <select id="editAuthor" name="author_id" required>
            <option value="">Select author</option>
            <% 
            java.util.List<com.example.kalaa.Author> authorsForEdit = (java.util.List<com.example.kalaa.Author>) request.getAttribute("authors");
            if (authorsForEdit != null) {
              for (com.example.kalaa.Author a : authorsForEdit) {
            %>
            <option value="<%= a.getAuthorId() %>"><%= a.getName() %></option>
            <% 
              }
            }
            %>
          </select>
        </div>

        <div class="form-group">
          <label for="editCategory">Category</label>
          <select id="editCategory" name="category" required>
            <option value="">Select category</option>
            <% 
            java.util.List<com.example.kalaa.Category> catsForEdit = (java.util.List<com.example.kalaa.Category>) request.getAttribute("categories");
            if (catsForEdit != null) {
              for (com.example.kalaa.Category c : catsForEdit) {
            %>
            <option value="<%= c.getCategoryId() %>"><%= c.getName() %></option>
            <% 
              }
            }
            %>
          </select>
        </div>

        <div class="form-group">
          <label for="editContent">Content</label>
          <textarea id="editContent" name="content" rows="6"></textarea>
        </div>

        <div class="form-group">
          <label for="editImage">Story Cover Image</label>
          <img id="editImagePreview" src="" alt="Current cover" style="display:none;max-width:120px;max-height:160px;margin-bottom:8px;border-radius:6px;">
          <input type="file" id="editImage" name="image" accept="image/jpeg,image/png,image/gif,image/webp">
          <small style="color:#8b4513;">Leave empty to keep the current image.</small>
        </div>

        <button type="submit" class="btn btn-submit">Update Story</button>
      </form>
    </div>
  </div>

<script>
  function logout() {
    if (confirm("Are you sure you want to logout?")) {
      window.location.href = "<%= request.getContextPath() %>/logout"; 
    }
  }

  function openEditStory(id, title, authorId, categoryName, content, imagePath) {
    document.getElementById('editStoryId').value = id;
    document.getElementById('editTitle').value = title || '';
    document.getElementById('editContent').value = content || '';
    document.getElementById('editImage').value = '';

    var imagePreview = document.getElementById('editImagePreview');
    if (imagePath) {
      imagePreview.src = imagePath;
      imagePreview.style.display = 'block';
    } else {
      imagePreview.src = '';
      imagePreview.style.display = 'none';
    }

    var authorSelect = document.getElementById('editAuthor');
    if (authorSelect) { authorSelect.value = String(authorId || ''); }

    var categorySelect = document.getElementById('editCategory');
    if (categorySelect && categoryName) {
      for (var i = 0; i < categorySelect.options.length; i++) {
        if (categorySelect.options[i].text === categoryName) {
          categorySelect.selectedIndex = i;
          break;
        }
      }
    }

    document.getElementById('editStoryModal').style.display = 'block';
  }

  function closeEditStory() {
    document.getElementById('editStoryModal').style.display = 'none';
  }

  function deleteStory(id) {
    if (!confirm('Delete this story?')) return;
    const form = document.createElement('form');
    form.method = 'post';
    form.action = 'editstory';
    form.innerHTML = '<input type="hidden" name="action" value="delete">' +
                     '<input type="hidden" name="storyId" value="' + id + '">';
    document.body.appendChild(form);
    form.submit();
  }
</script>

</body>
</html>

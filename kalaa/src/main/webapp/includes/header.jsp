<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.example.kalaa.User" %>
<%
    boolean isLoggedIn = session != null && session.getAttribute("user") != null;
    boolean isAdminLoggedIn = session != null && session.getAttribute("admin") != null;
    String username = (String) session.getAttribute("username");
%>

<!-- Toastify CSS -->
<link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/toastify-js/src/toastify.min.css">

<!-- Toastify JS -->
<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/toastify-js"></script>

<script>
// Toastify notification function
function showToast(message, type = 'success') {
    const backgroundColor = type === 'success' ? '#28a745' : 
                          type === 'error' ? '#dc3545' : 
                          type === 'warning' ? '#ffc107' : '#17a2b8';
    
    Toastify({
        text: message,
        duration: 3000,
        gravity: "top",
        position: "right",
        backgroundColor: backgroundColor,
        stopOnFocus: true
    }).showToast();
}

// Check for URL parameters and show notifications
document.addEventListener('DOMContentLoaded', function() {
    const urlParams = new URLSearchParams(window.location.search);
    
    if (urlParams.get('success') === 'loginsuccess') {
        showToast('Login successful! Welcome back!', 'success');
    } else if (urlParams.get('success') === 'registered') {
        showToast('Registration successful! Please login.', 'success');
    } else if (urlParams.get('success') === 'loggedout') {
        showToast('You have been logged out successfully.', 'info');
    } else if (urlParams.get('success') === 'storysubmitted') {
        showToast('Story submitted successfully!', 'success');
    } else if (urlParams.get('error') === 'invalidcredentials') {
        showToast('Invalid email or password.', 'error');
    } else if (urlParams.get('error') === 'passwordmismatch') {
        showToast('Passwords do not match.', 'error');
    } else if (urlParams.get('error') === 'userexists') {
        showToast('User already exists with this email.', 'error');
    } else if (urlParams.get('error') === 'databaseerror') {
        showToast('Database error occurred. Please try again.', 'error');
    }
});
</script>

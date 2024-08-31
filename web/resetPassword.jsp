<%-- 
    Document   : resetPassword
    Created on : Aug 17, 2024, 1:45:18 AM
    Author     : Do Dat
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reset Password</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <link rel="stylesheet" href="css/resetPassword.css">
        <script src="js/resetPassword.js"></script>       
    </head>
    <body>
        <div class="container">
            <div class="login-form">
                <h2>Reset Password</h2>
                <form action="ResetPasswordController" method="post">
                    <input type="hidden" name="emailReset" value="${email}">

                    <div class="password-container">
                        <label for="newPassword">New Password</label>
                        <input type="password" id="newPassword" name="newPassword" placeholder="Enter new password" required>
                        <span class="toggle-password">
                            <i id="toggleNewPasswordIcon" class="fa-regular fa-eye"></i>
                        </span>
                    </div>

                    <div class="password-container">
                        <label for="confirmPassword">Confirm Password</label>
                        <input type="password" id="confirmPassword" name="confirmPassword" placeholder="Confirm new password" required>
                        <span class="toggle-password">
                            <i id="toggleConfirmPasswordIcon" class="fa-regular fa-eye"></i>
                        </span>
                    </div>

                    <button type="submit" class="btn" name="action" value="Reset Password">Reset Password</button>

                    <div class="login-link">
                        <a href="login.jsp">Click here back to login page</a>
                    </div>
                </form>
            </div>
            <div class="image-container">
                <img src="./img/dat_ren.jpg" alt="Forgot Password Image">
            </div>
        </div>

        <!-- Modal for error message -->
        <div id="error-modal" class="modal">
            <div class="modal-content">
                <span class="close-button" onclick="closeModal()">&times;</span>
                <p id="error-message">${errorMessage}</p>
            </div>
        </div>

        <c:if test="${not empty errorMessage}">
            <script>
                showModal('${errorMessage}');
            </script>
        </c:if>
        <c:if test="${not empty success}">
            <script>
                showModal('${success}');
            </script>
        </c:if>
    </body>
</html>


<%-- 
    Document   : forgotPassword
    Created on : Aug 16, 2024, 6:55:23 PM
    Author     : Do Dat
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Forgot Password</title>
        <link rel="stylesheet" href="css/login.css">
        <script src="js/login.js"></script>
        <style>
            .login-form .btn {
                width: 105%
            }
        </style>
    </head>
    <body>
        <div class="container">
            <div class="login-form">
                <h2>Forgot Password</h2>
                <form action="MainController" method="post">
                    <label for="email" class="email">Enter your email</label>
                    <input type="email" id="email" name="email" placeholder="Enter email" required>
                    <button type="submit" class="btn" name="action" value="Send otp">Send OTP</button>
                </form>
            </div>
            <div class="image-container">
                <img src="./img/dat_ren.jpg" alt="Forgot Password Image">
            </div>
        </div>
        
        <!-- Modal HTML -->
        <div id="error-modal" class="modal">
            <div class="modal-content">
                <span class="close-button" onclick="closeModal()">&times;</span>
                <p id="error-message">Some error message</p>
            </div>
        </div>

        <c:set var="error" value="${requestScope.errorMessage}"/>
        <c:if test="${not empty error and error != null}">
            <script>
                showModal('${error}');
            </script>
        </c:if> 
    </body>
</html>

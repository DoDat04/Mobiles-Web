<%-- 
    Document   : login
    Created on : Aug 4, 2024, 1:13:04 AM
    Author     : Do Dat
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Login Form</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <link rel="stylesheet" href="css/login.css">
        <script src="js/login.js"></script>
    </head>
    <body>
        <div class="container">
            <div class="login-form">
                <h2>Sign in</h2>
                <form id="login-form" action="login" method="post" onsubmit="return validateForm()">
                    <label for="email" class="email">Email</label>
                    <input type="email" id="email" name="email" placeholder="Enter email">

                    <label for="password" class="password">Password</label>
                    <div class="password-container">
                        <input type="password" id="password" name="password" placeholder="Enter password">
                        <span class="toggle-password">
                            <i id="togglePasswordIcon" class="fa-regular fa-eye"></i>
                        </span>
                    </div>

                    <div class="remember_forgot">
                        <input type="checkbox" id="remember" name="remember">
                        <label for="remember" class="remember">Remember me</label>
                        <a href="forgotPassword.jsp" class="forgot-password">Forgot password?</a>
                    </div>

                    <button type="submit" class="btn" name="action">Sign in</button>
                </form>
                <div class="sign-up-section">
                    <p>Don't have an account? <a href="signUp.jsp" class="sign-up-link">Sign up</a></p>
                </div>
                <div class="or-divider">
                    <hr>
                    <span>or</span>
                    <hr>
                </div>
                <a href="google-auth" class="google-login">
                    <img src="https://www.gstatic.com/firebasejs/ui/2.0.0/images/auth/google.svg" alt="Google Logo" width="20" height="20">
                    <span style="margin-left: 0.5rem;" class="sign-in-with-google">Sign in with Google</span>
                </a>
            </div>
            <div class="image-container">
                <img src="./img/dat_ren.jpg">
            </div>
        </div>

        <!-- Modal HTML -->
        <div id="error-modal" class="modal">
            <div class="modal-content">
                <span class="close-button" onclick="closeModal()">&times;</span>
                <p id="error-message">Some error message</p>
            </div>
        </div>

        <c:set var="error" value="${requestScope.ERROR}"/>
        <c:if test="${not empty error and error != null}">
            <script>
                showModal('${error}');
            </script>
        </c:if>             
    </body>
</html>

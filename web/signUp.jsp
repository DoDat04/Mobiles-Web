<%-- 
    Document   : signUp
    Created on : Aug 18, 2024, 4:13:00 PM
    Author     : Do Dat
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sign Up</title>
        <link rel="stylesheet" href="css/signUp.css">
        <script src="js/signUp.js"></script>
    </head>
    <body>
        <div class="container">
            <div class="login-form">
                <h2>Sign Up</h2>
                <form action="MainController" method="post">
                    <label for="fullName">Full Name</label>
                    <input type="text" id="fullName" name="fullName" placeholder="Enter full name" required>

                    <label for="email">Email</label>
                    <input type="email" id="email" name="email" placeholder="Enter email" required>

                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" placeholder="Enter password" required>

                    <label for="confirmPassword">Confirm Password</label>
                    <input type="password" id="confirmPassword" name="confirmPassword" placeholder="Confirm password" required>

                    <div class="terms">
                        <input type="checkbox" id="terms" name="terms" required>
                        <label for="terms">I agree with the <a onclick="openTermsModal()">Terms of Service & Privacy Policy</a></label>
                    </div>

                    <button type="submit" class="btn" name="action" value="Sign up">Sign Up</button>
                </form>

                <!-- Already have an account? -->
                <div class="already-account">
                    <span>Already have an account? <a href="login.jsp" class="sign-in-link">Sign in</a></span>
                </div>
            </div>
            <div class="image-container">
                <img src="./img/dat_ren.jpg" alt="Sign Up Image">
            </div>
        </div>

        <!-- Terms Modal HTML -->
        <div id="terms-modal" class="modal">
            <div class="modal-content">
                <!-- Close Button (X) -->
                <span class="close-button" onclick="closeTermsModal()">&times;</span>
                <h2>Terms of Service & Privacy Policy</h2>
                <p>Here you can include the content of your Terms of Service & Privacy Policy.</p>
            </div>
        </div>


        <!-- Error Modal HTML -->
        <div id="error-modal" class="modal">
            <div class="modal-content">
                <span class="close-button" onclick="closeModal()">&times;</span>
                <p id="error-message">Some error message</p>
            </div>
        </div>

        <c:set var="success" value="${requestScope.CREATE_SUCCESS}" />
        <c:set var="errors" value="${requestScope.CREATE_ERROR}"/>
        <c:if test="${not empty errors.usernameIsExisted}">
            <script>
                showModal('${errors.usernameIsExisted}');
            </script>
        </c:if>
        <c:if test="${not empty errors.passwordLengthErr}">
            <script>
                showModal('${errors.passwordLengthErr}');
            </script>
        </c:if>
        <c:if test="${not empty errors.fullnameLengthErr}">
            <script>
                showModal('${errors.fullnameLengthErr}');
            </script>
        </c:if>
        <c:if test="${not empty errors.confirmNotMacthed}">
            <script>
                showModal('${errors.confirmNotMacthed}');
            </script>
        </c:if>
        <c:if test="${not empty success}">
            <script>
                showModal('${success}');
            </script>
        </c:if>
    </body>
</html>


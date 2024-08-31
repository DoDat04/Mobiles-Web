<%-- 
    Document   : otpVerification
    Created on : Aug 16, 2024, 7:36:02 PM
    Author     : Do Dat
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Verify OTP</title>
        <link rel="stylesheet" href="css/otpVerification.css">
        <script src="js/otpVerification.js"></script>     
    </head>
    <body>
        <div class="container">
            <div class="login-form">
                <h2>Verify OTP</h2>
                <form action="MainController" method="post">
                    <label for="otp" class="otp">Enter OTP</label>
                    <input type="text" id="otp" name="otp" placeholder="Enter OTP" required>

                    <button type="submit" class="btn" name="action" value="Verify otp">Verify OTP</button>
                </form>
                <div id="countdown">Time remaining: <span id="time">60</span> seconds</div>
            </div>
            <div class="image-container">
                <img src="./img/dat_ren.jpg" alt="OTP Verification Image">
            </div>
        </div>

        <!-- Modal for error message -->
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


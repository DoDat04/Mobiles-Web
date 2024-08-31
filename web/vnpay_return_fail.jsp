<%-- 
    Document   : vnpay_return_fail
    Created on : Aug 31, 2024, 4:37:17 PM
    Author     : Do Dat
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>KẾT QUẢ THANH TOÁN</title>
        <link rel="stylesheet" href="css/vnpay_return.css"> 
        <style>
            .notification {
                display: none; /* Hidden by default */
                position: fixed;
                z-index: 1000;
                left: 50%;
                top: 15%;
                transform: translate(-50%, -50%);
                background-color: #fff;
                padding: 20px;
                border-radius: 10px;
                text-align: center;
            }
            .back-to-home {
                position: fixed;
                color: black;
                top: 30%;
                left: 41%;
                font-size: 28px;
                text-decoration: none;
            }
        </style>
    </head>
    <body>
        <c:set var="message" value="${sessionScope.message}"/>
        <c:set var="notificationSvg" value="${sessionScope.notificationSvg}"/>      

        <!-- Notification Box -->
        <div id="notification" class="notification">
            ${notificationSvg}
            <h2>${VNPay_message}</h2>
        </div>

        <a href="home" class="back-to-home">Click here back to home page</a>

        <script>
            // Function to show the notification
            function showNotification() {
                document.getElementById("notification").style.display = "block";
            }

            // Show the notification when the page loads
            window.onload = function () {
                showNotification();
            };
        </script>
    </body>
</html>

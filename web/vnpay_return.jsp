<%-- 
    Document   : vnpay_return
    Created on : Aug 29, 2024, 7:17:04 PM
    Author     : Do Dat
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.text.DecimalFormat" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>KẾT QUẢ THANH TOÁN</title>
        <!-- Bootstrap core CSS -->
        <link href="/vnpay_jsp/assets/bootstrap.min.css" rel="stylesheet"/>
        <!-- Custom styles for this template -->
        <link href="/vnpay_jsp/assets/jumbotron-narrow.css" rel="stylesheet">
        <script src="/vnpay_jsp/assets/jquery-1.11.3.min.js"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <link rel="stylesheet" href="css/vnpay_return.css">       
    </head>

    <body>    
        <c:set var="mail_message" value="${sessionScope.mail_message}"/>
        <c:set var="message" value="${sessionScope.message}"/>
        <c:set var="notificationSvg" value="${sessionScope.notificationSvg}"/>

        <div class="mail-notification">
            <p>${mail_message}</p>
        </div>

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

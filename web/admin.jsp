<%-- 
    Document   : admin
    Created on : Aug 21, 2024, 2:13:51 AM
    Author     : Do Dat
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Page</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <link rel="stylesheet" href="css/admin.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>    
    </head>
    <body>
        <div class="header">
            <h2>Order List</h2>
            <div class="auth-links">
                <a href="javascript:void(0);" class="auth-link" id="auth-icon">
                    <i class="fa-solid fa-user"></i>
                </a>
                <ul class="account_dropdown_menu dropdown-menu" aria-labelledby="dropdownMenuButton1">
                    <li><a class="dropdown-item" href="#"><i class="adm_icon fas fa-question-circle"></i> FAQs</a></li>
                    <hr class="account_hr_guest">
                    <li><a class="dropdown-item" href="MainController?action=Logout"><i class="fa-solid fa-right-from-bracket"></i> Sign out</a></li>
                </ul>
            </div>
        </div>
        <c:choose>
            <c:when test="${not empty requestScope.SORTED_ORDERS}">
                <table>
                    <thead>
                        <tr>
                            <th>Order ID</th>
                            <th>Date</th>
                            <th>Customer</th>
                            <th>Address</th>
                            <th>Email</th>
                            <th>Total</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="order" items="${requestScope.SORTED_ORDERS}">
                            <tr>
                                <td><c:out value="${order.id}" /></td>
                                <td>
                                    <fmt:formatDate value="${order.date}" pattern="dd-MM-yyyy" />
                                </td>
                                <td><c:out value="${order.customer}" /></td>
                                <td><c:out value="${order.address}" /></td>
                                <td><c:out value="${order.email}" /></td>
                                <td><c:out value="${order.total}" /></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <p>No orders available.</p>
            </c:otherwise>
        </c:choose>
        <script src="js/iconUser.js"></script>
    </body>
</html>

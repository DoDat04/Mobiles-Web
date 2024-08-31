<%-- 
    Document   : staff
    Created on : Aug 21, 2024, 11:11:09 PM
    Author     : Do Dat
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Staff Page</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <link rel="stylesheet" href="css/viewOrder.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    </head>
    <body>
        <div class="header">
            <h1>Mobiles List</h1>
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
            <c:when test="${not empty requestScope.PRODUCTS}">
                <table>
                    <thead>
                        <tr>
                            <th>Mobile ID</th>
                            <th>Description</th>
                            <th>Price</th>
                            <th>Mobile Name</th>
                            <th>Year Of Production</th>
                            <th>Quantity</th>
                            <th>Not Sale</th>
                            <th>Remove</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="order" items="${requestScope.PRODUCTS}">
                            <tr>
                                <td><c:out value="${order.mobileId}" /></td>
                                <td><c:out value="${order.description}" /></td>
                                <td><c:out value="${order.price}" /></td>
                                <td><c:out value="${order.mobileName}" /></td>
                                <td><c:out value="${order.yearOfProduction}" /></td>
                                <td><c:out value="${order.quantity}" /></td>
                                <td><c:out value="${order.notSale}" /></td>
                                <td>
                                    <form action="MainController" method="POST" onsubmit="return confirm('Are you sure you want to delete this mobile?');">
                                        <input type="hidden" name="mobileId" value="${order.mobileId}" />
                                        <button type="submit" name="action" value="DeleteMobile">Delete</button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <p>No mobiles available.</p>
            </c:otherwise>
        </c:choose>
        <script src="js/iconUser.js"></script>
    </body>
</html>

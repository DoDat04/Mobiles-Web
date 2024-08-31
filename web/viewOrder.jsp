<%-- 
    Document   : viewOrder
    Created on : Aug 13, 2024, 8:23:58 PM
    Author     : Do Dat
--%>

<%@page import="datdt.mobiles.MobilesDTO"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Order</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <link rel="stylesheet" href="css/viewOrder.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>       
    </head>
    <body>
        <div class="header">
            <h1>Your Order Details</h1>
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

        <table>
            <thead>
                <tr>
                    <th colspan="2" class="order-title">Order Information</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>Order ID:</td>
                    <td>${sessionScope.ORDER_ID}</td>
                </tr> 
                <tr>
                    <td>Date:</td>
                    <td>${sessionScope.ORDER_DATE}</td>
                </tr>
                <tr>
                    <td>Name:</td>
                    <td>${sessionScope.CUST_NAME}</td>
                </tr>
                <tr>
                    <td>Email:</td>
                    <td>${sessionScope.CUST_EMAIL}</td>
                </tr>
                <tr>
                    <td>Address:</td>
                    <td>${sessionScope.CUST_ADDRESS}</td>
                </tr>
            </tbody>
        </table>
        <br/>

        <table>
            <thead>
                <tr>
                    <th colspan="4" class="order-title">Product Information</th>
                </tr>
                <tr>
                    <th>MobileID</th>
                    <th>Name</th>
                    <th>Quantity</th>
                    <th>Price</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="product" items="${sessionScope.CART_ITEMS}">
                    <tr>
                        <td>
                            ${product.mobileId}
                        </td>
                        <td>
                            ${product.mobileName}
                        </td>
                        <td>
                            ${product.quantity}
                        </td>
                        <td>
                            ${product.price * product.quantity}
                        </td>                       
                    </tr>                   
                </c:forEach>
                <tr>
                    <td colspan="2">Total:</td>
                    <td>${sessionScope.TOTAL_QUANTITY}</td>
                    <td>${sessionScope.TOTAL_PRICE}</td>
                </tr>
            </tbody>
        </table>

        <form id="paymentForm" action="ajaxServlet" method="POST">
            <label>
                <input type="radio" name="paymentMethod" value="COD" checked> Thanh toán khi nhận hàng (COD)
            </label><br>
            <label>
                <input type="radio" name="paymentMethod" value="VNPay"> Thanh toán qua VNPay
            </label><br>

            <!-- Hidden fields cho VNPay -->
            <input type="hidden" id="amount" name="amount" value="${sessionScope.TOTAL_PRICE * 23000}"> <!-- Số tiền bằng VND -->
            <input type="hidden" id="bankCode" name="bankCode" value="VNBANK"> <!-- Mã ngân hàng ví dụ -->
            <input type="hidden" id="language" name="language" value="vn"> <!-- Ngôn ngữ -->

            <!-- Nút submit -->
            <button type="submit" id="submitBtn">Thanh Toán</button>
        </form>   

        <script>
            document.getElementById('paymentForm').addEventListener('submit', function (event) {
                var selectedPaymentMethod = document.querySelector('input[name="paymentMethod"]:checked').value;

                // Nếu chọn COD thì không gửi form qua ajaxServlet
                if (selectedPaymentMethod === 'COD') {
                    event.preventDefault();
                    alert("Bạn đã chọn thanh toán khi nhận hàng (COD).");
                    // Thực hiện các hành động khác nếu cần thiết
                }
                // Nếu chọn VNPay, form sẽ được gửi tới ajaxServlet
            });
        </script>

        <a href="ReturnMarketController" class="returnMarket">Return to Market</a>   
        <script src="js/iconUser.js"></script>
    </body>
</html>

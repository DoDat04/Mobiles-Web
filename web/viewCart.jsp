<%-- 
    Document   : viewCart
    Created on : Aug 5, 2024, 1:06:45 AM
    Author     : Do Dat
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Mobiles Cart</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <link rel="stylesheet" href="css/viewCart.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js" referrerpolicy="no-referrer"></script>   
        <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
    </head>
    <body>
        <div class="header">
            <div class="cart-section">
                <c:choose>
                    <c:when test="${sessionScope.LOGIN_USER != null}">
                        <h1 style="color: red">Giỏ hàng của ${sessionScope.LOGIN_USER.fullName}:</h1>
                    </c:when>
                    <c:when test="${sessionScope.LOGIN_GMAIL != null}">
                        <h1 style="color: red">Giỏ hàng của ${sessionScope.LOGIN_GMAIL.name}:</h1>               
                    </c:when>  
                    <c:otherwise>
                        <h1 style="color: red">Giỏ hàng của Guest:</h1>               
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="auth-links">
                <a href="javascript:void(0);" class="auth-link" id="auth-icon">
                    <i class="fa-solid fa-user"></i>
                </a>
                <c:choose>
                    <c:when test="${sessionScope.LOGIN_USER != null or sessionScope.LOGIN_GMAIL != null}">
                        <!-- Hiển thị Sign Out nếu người dùng đã đăng nhập -->
                        <ul class="account_dropdown_menu dropdown-menu" aria-labelledby="dropdownMenuButton1">
                            <li><a class="dropdown-item" href="#"><i class="adm_icon fas fa-question-circle"></i> FAQs</a></li>
                            <hr class="account_hr_guest">
                            <li><a class="dropdown-item" href="MainController?action=Logout"><i class="fa-solid fa-right-from-bracket"></i> Sign out</a></li>
                        </ul>
                    </c:when>
                    <c:otherwise>
                        <!-- Hiển thị Sign In nếu người dùng chưa đăng nhập -->
                        <ul class="account_dropdown_menu dropdown-menu" aria-labelledby="dropdownMenuButton1">
                            <li><a class="dropdown-item" href="#"><i class="adm_icon fas fa-question-circle"></i> FAQs</a></li>
                            <hr class="account_hr_guest">
                            <li><a class="dropdown-item" href="login.jsp"><i class="fa-solid fa-right-from-bracket"></i> Sign in</a></li>
                        </ul>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>     

        <c:if test="${not empty sessionScope.CART}">
            <c:set var="cart" value="${sessionScope.CART}" />
            <c:set var="items" value="${cart.items}" />
            <c:if test="${not empty items}">
                <form action="MainController">
                    <table border="1">
                        <thead>
                            <tr>
                                <th>Mobile ID</th>
                                <th>Description</th>                                              
                                <th>Price</th>                                              
                                <th>Mobile Name</th>
                                <th>Year Of Production</th>
                                <th>Quantity</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="dto" items="${items}">
                                <tr>
                                    <td>                                       
                                        ${dto.value.mobileId}
                                    </td>
                                    <td>
                                        ${dto.value.description}
                                    </td>
                                    <td>
                                        ${dto.value.price}$
                                    </td>
                                    <td>
                                        ${dto.value.mobileName}
                                    </td>
                                    <td>
                                        ${dto.value.yearOfProduction}
                                    </td>
                                    <td>
                                        ${dto.value.quantity}
                                    </td>                                                                                                                                           
                                    <td>
                                        <input type="checkbox" name="chkItem" value="${dto.key}" />                                             
                                    </td>
                                </tr>
                            </c:forEach>
                            <tr>
                                <td colspan="6">
                                    <a href="home">Wanna Buy More Mobiles?</a>
                                </td>
                                <td>
                                    <input type="submit" name="action" value="Remove Selected Item"/>
                                </td>                                   
                            </tr>
                        </tbody>
                    </table>
                </form>
                <form action="MainController" method="POST">
                    <c:choose>
                        <c:when test="${sessionScope.LOGIN_GMAIL != null}">
                            <div class="text-header">
                                <input type="text" name="txtCusName" value="${sessionScope.LOGIN_GMAIL.name}" required placeholder="Họ và tên"/> <br/>
                                <input type="email" name="txtEmail" value="${sessionScope.LOGIN_GMAIL.email}" required placeholder="Email"/> <br/>
                                <input type="text" name="txtDiaChi" value="" required placeholder="Địa chỉ"/> <br/>
                            </div>
                        </c:when>
                        <c:when test="${sessionScope.LOGIN_USER != null}">
                            <div class="text-header">
                                <input type="text" name="txtCusName" value="${sessionScope.LOGIN_USER.fullName}" required placeholder="Họ và tên"/> <br/>
                                <input type="email" name="txtEmail" value="${sessionScope.LOGIN_USER.userId}" required placeholder="Email"/> <br/>
                                <input type="text" name="txtDiaChi" value="" required placeholder="Địa chỉ"/> <br/>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="text-header">
                                <input type="text" name="txtCusName" value="" required placeholder="Họ và tên"/> <br/>
                                <input type="email" name="txtEmail" value="" required placeholder="Email"/> <br/>
                                <input type="text" name="txtDiaChi" value="" required placeholder="Địa chỉ"/> <br/>
                            </div>
                        </c:otherwise>
                    </c:choose>                                                         
                    <div class="address">
                        <div class="select-container">
                            <select id="city" name="city">
                                <option value="">Chọn tỉnh / thành</option>
                            </select>
                            <span class="separator">|</span>
                        </div>
                        <div class="select-container">
                            <select id="district" name="district">
                                <option value="">Chọn quận / huyện</option>
                            </select>
                            <span class="separator">|</span>
                        </div>
                        <div class="select-container">
                            <select id="ward" name="ward">
                                <option value="">Chọn phường / xã</option>
                            </select>
                            <span class="separator">|</span>
                        </div>
                    </div>
                    
                    <input type="submit" name="action" value="CheckOut" />
                </form>
            </c:if>
        </c:if>   
        <c:if test="${empty sessionScope.CART.items}">
            <h2>
                <font color="red">
                No cart is existed!!!
                </font>
            </h2>
            <a href="home">Continue Shopping</a>  
        </c:if>
        <script src="js/iconUser.js"></script>
        <script src="js/viewCart.js"></script>
    </body>
</html>

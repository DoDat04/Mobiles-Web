<%-- 
    Document   : index
    Created on : Aug 4, 2024, 1:12:58 AM
    Author     : Do Dat
--%>

<%@page import="datdt.controllers.ConfigVNPay"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.nio.charset.StandardCharsets"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Index Page</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <link rel="stylesheet" href="css/index.css">   
        <script src="js/index.js"></script>  
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <link rel="stylesheet" href="./css/owl.carousel.min.css">
    </head>
    <body>      
        <div class="header">
            <div class="welcome-section">
                <h1 style="color: red">
                    <c:choose>
                        <c:when test="${sessionScope.LOGIN_USER != null}">
                            Welcome, ${sessionScope.LOGIN_USER.fullName}
                        </c:when>
                        <c:when test="${sessionScope.LOGIN_GMAIL != null}">
                            Welcome, ${sessionScope.LOGIN_GMAIL.name}
                        </c:when>
                        <c:otherwise>
                            Welcome, Guest
                        </c:otherwise>
                    </c:choose>
                </h1>
            </div>
            <div class="auth-links">
                <a href="cart" class="auth-link">
                    <c:if test="${sessionScope.LOGIN_USER == null or sessionScope.LOGIN_GMAIL == null}">

                    </c:if>
                    <i class="fa-solid fa-cart-shopping cart-icon"></i>
                    View Your Cart
                    <span class="cart-badge">
                        <c:choose>
                            <c:when test="${sessionScope.cartItemCount != null or sessionScope.cartItemCount > 0}">
                                ${sessionScope.cartItemCount}
                            </c:when>
                            <c:otherwise>
                                0
                            </c:otherwise>
                        </c:choose>
                    </span>
                </a>
                <span class="separator">|</span>
                <a href="javascript:void(0);" class="auth-link" id="auth-icon">
                    <i class="fa-solid fa-user"></i>
                </a>
                <ul class="account_dropdown_menu dropdown-menu" aria-labelledby="dropdownMenuButton1">
                    <li><a class="dropdown-item" href="#"><i class="adm_icon fas fa-question-circle"></i> FAQs</a></li>
                    <hr class="account_hr_guest">
                    <c:choose>
                        <c:when test="${sessionScope.LOGIN_USER != null or sessionScope.LOGIN_GMAIL != null}">
                            <li><a class="dropdown-item" href="MainController?action=Logout"><i class="fa-solid fa-right-from-bracket"></i> Sign out</a></li>
                            </c:when>
                            <c:otherwise>
                            <li><a class="dropdown-item" href="login"><i class="adm_icon fas fa-sign-in-alt"></i> Sign in</a></li>
                            </c:otherwise>
                        </c:choose>
                </ul>
            </div>
        </div>  

        <div class="carousel">
            <div class="owl-carousel owl-theme">
                <div class="item">
                    <img src="./img/Mobiles_Img/1.jpg" alt="carousel">
                </div>
                <div class="item">
                    <img src="./img/Mobiles_Img/2.jpg" alt="carousel">
                </div>
                <div class="item">
                    <img src="./img/Mobiles_Img/3.jpg" alt="carousel">
                </div>
                <div class="item">
                    <img src="./img/Mobiles_Img/4.jpg" alt="carousel">
                </div>            
            </div>
        </div>

        <div class="sort">
            <form id="sortForm" action="MobilesController" method="GET">
                <div class="dropdown">
                    <button id="sortButton" class="dropbtn">Sort by Price</button>
                    <div id="dropdownContent" class="dropdown-content">
                        <a href="home?action=Sort&ddlSort=ascending" onclick="updateSort('ascending')">Sort ascending by price</a>
                        <a href="home?action=Sort&ddlSort=descending" onclick="updateSort('descending')">Sort descending by price</a>
                    </div>
                </div>
                <input type="hidden" name="action" value="Sort">
                <input type="hidden" name="ddlSort" id="ddlSort" value="${sessionScope.selectedSort}">
            </form>
        </div>

        <script>
            document.getElementById('sortButton').addEventListener('click', function (event) {
                event.preventDefault(); // Prevent the default action
                document.getElementById('dropdownContent').classList.toggle('show');
            });

        </script>

        <div class="product-grid">
            <c:forEach var="dto" items="${products}">
                <div class="product">
                    <img src="img/Mobiles_Img/${dto.mobileId}.jpg" alt="${dto.mobileName}">
                    <div class="product-name">${dto.mobileName}</div>
                    <div class="product-price">${dto.price}$</div>
                    <form action="AddToCart" method="GET">
                        <input type="hidden" name="mobileId" value="${dto.mobileId}" />
                        <input type="submit" value="Add To Cart" />
                    </form>
                </div>
            </c:forEach>
        </div>       

        <div class="comment-section">
            <h2>Leave a Review</h2>
            <form action="MainController" method="post">
                <textarea name="comment" placeholder="Write your review here..." required></textarea>
                <br/>
                <input type="submit" value="Submit Review" name="action"/>
            </form>
        </div>

        <div class="comments-display">
            <h2>User Reviews</h2>
            <c:choose>
                <c:when test="${not empty requestScope.comments}">
                    <c:forEach var="comment" items="${requestScope.comments}">
                        <div class="comment">
                            <p><strong>${comment.userId}:</strong> ${comment.commentText}</p>
                            <p><em>Posted on: <fmt:formatDate value="${comment.commentDate}" pattern="dd-MM-yyyy HH:mm:ss"/></em></p>
                        </div>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <p>No comments available.</p>
                </c:otherwise>
            </c:choose>
        </div>

        <!-- BACK TO TOP -->
        <a href="#" class="backToTop cd-top text-replace js-cd-top">
            <i class="fas fa-angle-up"></i>
        </a>

        <script src="https://code.jquery.com/jquery-3.6.3.min.js"
        integrity="sha256-pvPw+upLPUjgMXY0G+8O0xUf+/Im1MZjXxxgOcBQBXU=" crossorigin="anonymous"></script>

        <script src="./js/owl.carousel.min.js"></script>       

        <!-- Modal Box -->
        <div id="success-modal" class="modal">
            <div class="modal-content">
                <span class="close-button" onclick="closeModal()">&times;</span>
                <p id="success-message"></p>
            </div>
        </div>

        <c:set var="errorMessage" value="${requestScope.error}"/>
        <c:set var="successMessage" value="${requestScope.message}"/>
        <c:if test="${not empty errorMessage}">
            <script>
                showModal('${errorMessage}');
            </script>
        </c:if>
        <c:if test="${not empty successMessage}">
            <script>
                showModal('${successMessage}');
            </script>
        </c:if>
        <script src="js/backToTop.js"></script>  
        <script src="js/iconUser.js"></script>  
        <script>
                $(document).ready(function () {
                    $('.owl-carousel').owlCarousel({
                        loop: true,
                        margin: 10,
                        nav: false,
                        autoplay: true,
                        autoplayTimeout: 3000, // Thay đổi thời gian tự động chuyển slide (3000ms = 3s)
                        autoplayHoverPause: true,
                        responsive: {
                            0: {
                                items: 1 // Hiển thị 1 hình tại màn hình nhỏ
                            },
                            600: {
                                items: 2 // Hiển thị 2 hình tại màn hình vừa
                            },
                            1000: {
                                items: 1 // Hiển thị 1 hình tại màn hình lớn
                            }
                        }
                    });
                });
        </script>

    </body>
</html>



/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package datdt.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import datdt.cart.CartBean;
import datdt.mobiles.MobilesDAO;
import datdt.mobiles.MobilesDTO;
import datdt.order.OrderDAO;
import datdt.order.OrderDTO;
import datdt.users.UsersDAO;
import datdt.users.UsersDTO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Base64;
import java.util.List;

/**
 *
 * @author Do Dat
 */
@WebServlet(name = "LoginController", urlPatterns = {"/login"})
public class LoginController extends HttpServlet {

    private static final String LOGIN_PAGE = "login.jsp";
    private static final String INDEX_PAGE = "home";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = LOGIN_PAGE;

        try {
            String userID = request.getParameter("email");
            String password = request.getParameter("password");

            if (userID == null || userID.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            } else {
                UsersDAO dao = new UsersDAO();
                UsersDTO result = dao.checkLogin(userID, password);
                // Authenticate here
                if (result != null) {
                    url = INDEX_PAGE;
                    HttpSession session = request.getSession();
                    session.removeAttribute("LOGIN_GMAIL"); // Clear Google login user info
                    session.removeAttribute("cartItemCount");
                    session.removeAttribute("CART");
                    session.setAttribute("LOGIN_USER", result);
                    String sanitizedUserId = getUserIdBeforeAt(result.getUserId());
                    System.out.println(sanitizedUserId + " huhuhu");
                    session.setAttribute("userId", sanitizedUserId);

                    CartBean userCart = getCartFromCookie(request);
                    session.setAttribute("CART", userCart);

                    String role = result.getRole();
                    if (role.equals("Manager")) {
                        url = "admin.jsp";
                        OrderDAO daoo = new OrderDAO();
                        List<OrderDTO> sortedOrders = daoo.getAllOrders();
                        request.setAttribute("SORTED_ORDERS", sortedOrders);
                    } else if (role.equals("Staff")) {
                        url = "staff.jsp";
                        MobilesDAO mobileDao = new MobilesDAO();
                        List<MobilesDTO> mobilesList = mobileDao.getAllProducts();
                        request.setAttribute("PRODUCTS", mobilesList);
                    }
                } else {
                    request.setAttribute("ERROR", "Your username or password is wrong!!!");
                }
            }
        } catch (Exception e) {
            log("Error at MainController: " + e.toString());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    // Load cart từ cookie sau đó gắn vào session để hiển thị
    private CartBean getCartFromCookie(HttpServletRequest request) throws IOException {
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userId");
        String sanitizedUserId = getUserIdBeforeAt(userId);
        String cookieName = "USER_CART" + sanitizedUserId;
        System.out.println(cookieName + " hahaha");

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookieName.equals(cookie.getName())) {
                    try {
                        byte[] cartBytes = Base64.getDecoder().decode(cookie.getValue());
                        CartBean cart = objectMapper.readValue(cartBytes, CartBean.class);

                        // Cập nhật số lượng mặt hàng trong giỏ hàng vào session
                        int cartItemCount = cart.getTotalQuantity();
                        session.setAttribute("cartItemCount", cartItemCount);

                        return cart;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null; // Trả về giỏ hàng rỗng nếu không tìm thấy cookie
    }
    
    // Hàm dùng để tách lấy giá trị trước @
    private String getUserIdBeforeAt(String userId) {
        int atIndex = userId.indexOf('@');
        if (atIndex > 0) {
            return userId.substring(0, atIndex);
        }
        return userId; // Nếu không có dấu '@', trả về toàn bộ chuỗi
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

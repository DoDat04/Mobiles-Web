/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package datdt.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import datdt.cart.CartBean;
import datdt.utils.GoogleUtils;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Base64;

/**
 *
 * @author Do Dat
 */
@WebServlet(name = "LoginGoogleServlet", urlPatterns = {"/LoginGoogleServlet"})
public class LoginGoogleServlet extends HttpServlet {
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
            String code = request.getParameter("code");
            if (code == null || code.isEmpty()) {
                url = LOGIN_PAGE;
            } else {
                String accessToken = GoogleUtils.getToken(code);
                GooglePojo googlePojo = GoogleUtils.getUserInfo(accessToken);               
                if (googlePojo != null) {
                    HttpSession session = request.getSession();
                    session.removeAttribute("LOGIN_USER"); // Clear regular login user info
                    session.removeAttribute("cartItemCount");
                    session.removeAttribute("CART");
                    session.setAttribute("LOGIN_GMAIL", googlePojo); // Set Google user details under LOGIN_USER
                    String sanitizedUserId = getUserIdBeforeAt(googlePojo.getEmail());
                    session.setAttribute("email", sanitizedUserId);
                    
                    CartBean gmailCart = getCartFromCookie(request);
                    session.setAttribute("CART", gmailCart);
                    
                    url = INDEX_PAGE;
                } 
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            response.sendRedirect(url);
        }
    }
    
    // Load cart từ cookie sau đó gắn vào session để hiển thị
    private CartBean getCartFromCookie(HttpServletRequest request) throws IOException {
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("email");
        String sanitizedUserId = getUserIdBeforeAt(userId);
        String cookieName = "GMAIL_CART" + sanitizedUserId;

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
    
    private String getUserIdBeforeAt(String email) {
        int atIndex = email.indexOf('@');
        if (atIndex > 0) {
            return email.substring(0, atIndex);
        }
        return email; // Nếu không có dấu '@', trả về toàn bộ chuỗi
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

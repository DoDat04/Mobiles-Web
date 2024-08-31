/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package datdt.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import datdt.cart.CartBean;
import datdt.mobiles.MobilesDTO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Base64;
import java.util.Map;

/**
 *
 * @author Do Dat
 */
@WebServlet(name = "RemoveItemFromCartController", urlPatterns = {"/RemoveItemFromCartController"})
public class RemoveItemFromCartController extends HttpServlet {

    private static final String CART_PAGE = "cart";
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
        try {
            //1. Cust goes to his/her carts place
            HttpSession session = request.getSession(false); //do chi la ao giac o client
            if (session != null) {
                //2. Cust takes his/her cart
                CartBean cart = (CartBean) session.getAttribute("CART");
                if (cart != null) {
                    //3. Cust gets items
                    Map<String, MobilesDTO> items = cart.getItems();
                    if (items != null) {
                        //4. Cust chooses remove some item
                        //tao thanh mang kieu string
                        String[] selectedItems = request.getParameterValues("chkItem");
                        if (selectedItems != null) {
                            for (String item : selectedItems) {
                                cart.removeItemFromCart(item);
                            }//each item is process
                            session.setAttribute("CART", cart);//gio hang trong tay minh nen can setAtt
                            // Cập nhật lại số lượng sản phẩm trong giỏ hàng
                            int totalQuantity = cart.getTotalQuantity();
                            session.setAttribute("cartItemCount", totalQuantity);

                            String userId = null;
                            String cookieName;
                            if (session.getAttribute("LOGIN_USER") != null) {
                                userId = (String) session.getAttribute("userId");
                                cookieName = "USER_CART" + userId;
                            } else if (session.getAttribute("LOGIN_GMAIL") != null) {
                                userId = (String) session.getAttribute("email");
                                cookieName = "GMAIL_CART" + userId;
                            } else {
                                cookieName = "GUEST_CART";
                            }
                         
                            UpdateCookie(request, response, cookieName, cart);

                        }//user choose at least 1 item to remove     
                    }//items have existed
                }//cart has existed                               
            }//check co cart place thi moi xoa           
        } finally {
            response.sendRedirect(CART_PAGE);//neu dung req dispatcher se tao ra array trung btAction
        }
    }

    private void UpdateCookie(HttpServletRequest request, HttpServletResponse response, String cookieName, CartBean cart) throws IOException {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    // Lấy cookie cũ và cập nhật
                    Cookie updatedCartCookie = new Cookie(cookieName, encodeCartToCookie(cart));
                    updatedCartCookie.setMaxAge(60 * 60 * 24 * 7); // Lưu trong 1 tuần
                    updatedCartCookie.setPath("/"); // Đặt đường dẫn cookie
                    response.addCookie(updatedCartCookie); // Ghi cookie đã cập nhật trở lại trình duyệt
                    break;
                }
            }
        }
    }

    private String encodeCartToCookie(CartBean cart) throws IOException {
        byte[] cartBytes = objectMapper.writeValueAsBytes(cart);
        return Base64.getEncoder().encodeToString(cartBytes);
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

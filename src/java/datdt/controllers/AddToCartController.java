/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package datdt.controllers;

import datdt.cart.CartBean;
import datdt.mobiles.MobilesDAO;
import datdt.mobiles.MobilesDTO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Base64;
import com.fasterxml.jackson.databind.ObjectMapper; // Thư viện Jackson để xử lý JSON
import jakarta.servlet.http.Cookie;

/**
 *
 * @author Do Dat
 */
@WebServlet(name = "AddToCartController", urlPatterns = {"/AddToCart"})
public class AddToCartController extends HttpServlet {

    private static final String MOBILES_PAGE = "home";
    private static final ObjectMapper objectMapper = new ObjectMapper(); // Jackson ObjectMapper

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
        String url = MOBILES_PAGE;
        String mobileId = request.getParameter("mobileId");

        try {
            HttpSession session = request.getSession();
            // Chỗ này là lấy danh sách products đã sắp xếp để khi nếu đã sắp xếp mà nhấn add to cart
            // Ngăn trường hợp nó quay lại list chưa sắp xếp
            List<MobilesDTO> sortedProducts = (List<MobilesDTO>) session.getAttribute("products");
            if (sortedProducts == null) {
                MobilesDAO daoo = new MobilesDAO();
                sortedProducts = daoo.getAllProducts();
                session.setAttribute("products", sortedProducts);
            }

            // Tìm sản phẩm trong list thông qua mobileId
            MobilesDTO item = null;
            for (MobilesDTO product : sortedProducts) {
                if (product.getMobileId().equals(mobileId)) {
                    item = product;
                    break;
                }
            }

            if (item != null) {
                CartBean cart = getCartFromCookies(request, session);
                if (cart == null) {
                    cart = new CartBean();
                }

                cart.addItemTocart(item);
                session.setAttribute("CART", cart);

                int updatedQuantity = cart.getItemQuantity(mobileId);
                session.setAttribute("cartItemCount", cart.getTotalQuantity());

                saveCartToCookies(response, cart, request, session);

                // Vì xài sendRedirect nên ở đây để session nhưng cứ mỗi lần load trang home sẽ hiển thị
                session.setAttribute("message", "Thêm sản phẩm có tên: " + item.getMobileName() + " vào giỏ hàng thành công! Số lượng trong giỏ: " + updatedQuantity);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddToCartController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddToCartController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // Tải lại trang sau khi thêm vào giỏ hàng
            String redirectUrl = request.getContextPath() + "/" + url + "?mobileId" + mobileId;
            response.sendRedirect(redirectUrl);
        }
    }

    private CartBean getCartFromCookies(HttpServletRequest request, HttpSession session) throws IOException {
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

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookieName.equals(cookie.getName())) {
                    return decodeCartFromCookie(cookie.getValue());
                }
            }
        }
        return null;
    }

    private void saveCartToCookies(HttpServletResponse response, CartBean cart, HttpServletRequest request, HttpSession session) throws IOException {
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

        String cartEncoded = encodeCartToCookie(cart);
        Cookie cartCookie = new Cookie(cookieName, cartEncoded);
        cartCookie.setMaxAge(60 * 60 * 24 * 7); // cookie tồn tại 7 ngày
        cartCookie.setPath("/"); // áp dụng cho toàn ứng dụng
        response.addCookie(cartCookie);
    }
    
    // Lưu dữ liệu dạng byte sau đó dùng base64 đễ mã hóa lưu vào cookie
    private String encodeCartToCookie(CartBean cart) throws IOException {
        byte[] cartBytes = objectMapper.writeValueAsBytes(cart);
        return Base64.getEncoder().encodeToString(cartBytes);
    }
    
    // Giải mã phần code xong đọc giá trị từ cookie
    private CartBean decodeCartFromCookie(String cartEncoded) throws IOException {
        byte[] cartBytes = Base64.getDecoder().decode(cartEncoded);
        return objectMapper.readValue(cartBytes, CartBean.class);
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

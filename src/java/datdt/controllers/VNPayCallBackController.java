/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package datdt.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import datdt.cart.CartBean;
import datdt.mobiles.MobilesDTO;
import datdt.order.OrderDAO;
import datdt.orderdetail.OrderDetailDAO;
import datdt.orderdetail.OrderDetailDTO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Do Dat
 */
@WebServlet(name = "VNPayCallBackController", urlPatterns = {"/VNPayCallBackController"})
public class VNPayCallBackController extends HttpServlet {
    private static final String SUCCESS_VNPAY = "vnpay_return.jsp";
    private static final String FAIL_VNPAY = "vnpay_return_fail.jsp";
    private static final String GMAIL_USERNAME = ""; // Thay bằng mail của bạn
    private static final String GMAIL_APP_PASSWORD = "xels hpiv urjw joaa"; // Thay bằng app password của bạn
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
        String cookieName;
        String userId = null;
        String url = FAIL_VNPAY;
        try {
            // Begin process return from VNPAY
            Map<String, String> fields = new HashMap<>();
            for (Enumeration<String> params = request.getParameterNames(); params.hasMoreElements();) {
                String fieldName = URLEncoder.encode(params.nextElement(), StandardCharsets.US_ASCII.toString());
                String fieldValue = URLEncoder.encode(request.getParameter(fieldName), StandardCharsets.US_ASCII.toString());
                if (fieldValue != null && !fieldValue.isEmpty()) {
                    fields.put(fieldName, fieldValue);
                }
            }

            String vnp_SecureHash = request.getParameter("vnp_SecureHash");
            fields.remove("vnp_SecureHashType");
            fields.remove("vnp_SecureHash");
            String signValue = ConfigVNPay.hashAllFields(fields);

            // Determine transaction status
            String transactionStatus = request.getParameter("vnp_TransactionStatus");
            String message = null;
            String notificationSvg = null;
            String mail_message = null;

            HttpSession session = request.getSession(false);
            if (session != null) {
                String custName = (String) session.getAttribute("CUST_NAME");
                String custEmail = (String) session.getAttribute("CUST_EMAIL");
                String custAddress = (String) session.getAttribute("CUST_ADDRESS");
                CartBean cart = (CartBean) session.getAttribute("CART");
                if (cart != null && signValue.equals(vnp_SecureHash)) {
                    // Nếu thanh toán thành công mới sendEmail, createOrder, addOrderDetail và RemoveCookie
                    if ("00".equals(transactionStatus)) {
                        url = SUCCESS_VNPAY;
                        message = "Giao dịch thành công!";
                        mail_message = "Đơn xác nhận đã được gửi qua email. Vui lòng kiểm tra email!";
                        notificationSvg = "<svg viewBox='0 0 52 52'><path class='checkmark' fill='none' stroke='green' stroke-width='4' d='M14 27 L22 35 L38 19' /></svg>";
                        
                        Map<String, MobilesDTO> items = cart.getItems();
                        if (items != null) {
                            float totalPrice = cart.getTotalPrice();
                            int totalQuantity = cart.getTotalQuantity();
                            OrderDAO dao = new OrderDAO();
                            
                            String orderId = dao.createOrder(custName, custEmail, custAddress, totalQuantity);

                            Timestamp today = new Timestamp(System.currentTimeMillis());
                            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                            String formattedDate = sdf.format(today);

                            OrderDetailDAO detailDao = new OrderDetailDAO();

                            // Initialize an email content string
                            StringBuilder emailContentBuilder = new StringBuilder();
                            emailContentBuilder.append("Cảm ơn quý khách ").append(custName).append(" đã đặt hàng bên chúng tôi.\n")
                                    .append("Địa chỉ giao hàng của quý khách: ").append(custAddress).append("\n\n")
                                    .append("Chi tiết đơn hàng:\n");

                            for (MobilesDTO product : items.values()) {
                                OrderDetailDTO orderDetail = new OrderDetailDTO(
                                        product.getMobileId(),
                                        product.getPrice(),
                                        product.getQuantity(),
                                        orderId,
                                        today,
                                        product.getPrice() * product.getQuantity()
                                );
                                detailDao.addOrderDetail(orderDetail);

                                emailContentBuilder.append("- Sản phẩm: ").append(product.getMobileName())
                                        .append(", Số lượng: ").append(product.getQuantity())
                                        .append(", Giá: ").append(product.getPrice())
                                        .append("\n");
                            }

                            emailContentBuilder.append("\nTổng số lượng: ").append(totalQuantity)
                                    .append("\nTổng giá trị: ").append(totalPrice).append("\n");

                            String emailContent = emailContentBuilder.toString();
                            sendEmail(custEmail, emailContent, orderId);

                            

                            if (session.getAttribute("LOGIN_USER") != null) {
                                userId = (String) session.getAttribute("userId");
                                cookieName = "USER_CART" + userId;
                                System.out.println(cookieName + " hihihi");
                            } else if (session.getAttribute("LOGIN_GMAIL") != null) {
                                userId = (String) session.getAttribute("email");
                                cookieName = "GMAIL_CART" + userId;
                            } else {
                                cookieName = "GUEST_CART";
                            }

                            RemoveCookie(request, response, cookieName, cart);
                        }
                    } else {
                        url = FAIL_VNPAY;
                        message = "Giao dịch thất bại.";
                        notificationSvg = "<svg viewBox='0 0 52 52' class='xmark'><line x1='15' y1='15' x2='37' y2='37' stroke='red' stroke-width='4' /><line x1='37' y1='15' x2='15' y2='37' stroke='red' stroke-width='4' /></svg>";
                    }
                } else {
                    url = FAIL_VNPAY;
                    message = "Chữ ký không hợp lệ.";
                    notificationSvg = "<svg viewBox='0 0 52 52' class='xmark'><line x1='15' y1='15' x2='37' y2='37' stroke='red' stroke-width='4' /><line x1='37' y1='15' x2='15' y2='37' stroke='red' stroke-width='4' /></svg>";
                }
                session.setAttribute("mail_message", mail_message);
                session.setAttribute("VNPay_message", message);
                session.setAttribute("notificationSvg", notificationSvg);
            }            
            
        } catch (SQLException ex) {
            Logger.getLogger(VNPayCallBackController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(VNPayCallBackController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(VNPayCallBackController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            response.sendRedirect(url);
        }
    }

    private String encodeCartToCookie(CartBean cart) throws IOException {
        byte[] cartBytes = objectMapper.writeValueAsBytes(cart);
        return Base64.getEncoder().encodeToString(cartBytes);
    }

    private void RemoveCookie(HttpServletRequest request, HttpServletResponse response, String cookieName, CartBean cart) throws IOException {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    // Xóa cookie
                    Cookie updatedCartCookie = new Cookie(cookieName, encodeCartToCookie(cart));
                    updatedCartCookie.setMaxAge(0); // Xóa cookie
                    updatedCartCookie.setPath("/"); // Đặt đường dẫn cookie
                    response.addCookie(updatedCartCookie); // Ghi cookie đã cập nhật trở lại trình duyệt
                    break;
                }
            }
        }
    }

    private void sendEmail(String toEmail, String emailContent, String orderId) throws Exception {
        String host = "smtp.gmail.com";
        String port = "587";

        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(GMAIL_USERNAME, GMAIL_APP_PASSWORD);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(GMAIL_USERNAME));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));

        // Set the subject with UTF-8 encoding and include the order ID to make it unique
        String subject = "Cửa Hàng Điện Thoại Thành Đạt - Mã Đơn Hàng " + orderId;
        message.setSubject("=?UTF-8?B?" + java.util.Base64.getEncoder().encodeToString(subject.getBytes("UTF-8")) + "?=");

        // Set the content with UTF-8 encoding
        message.setContent(emailContent, "text/plain; charset=UTF-8");

        Transport.send(message);
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

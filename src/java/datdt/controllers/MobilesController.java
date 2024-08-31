/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package datdt.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import datdt.cart.CartBean;
import datdt.comment.CommentDAO;
import datdt.comment.CommentDTO;
import datdt.mobiles.MobilesDAO;
import datdt.mobiles.MobilesDTO;
import datdt.users.UsersDTO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;

/**
 *
 * @author Do Dat
 */
@WebServlet(name = "MobilesController", urlPatterns = {"/home"})
public class MobilesController extends HttpServlet {

    private static final String MOBILES_PAGE = "index.jsp";
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
        String action = request.getParameter("action");
        String url = MOBILES_PAGE;

        HttpSession session = request.getSession();

        // Check if the user is logged in or guest
        UsersDTO loginUser = (UsersDTO) session.getAttribute("LOGIN_USER");
        GooglePojo loginGmail = (GooglePojo) session.getAttribute("LOGIN_GMAIL");

        CartBean guestCart = getCartFromCookie(request);
        session.setAttribute("CART", guestCart);


        // chỗ này dùng để tải comments
        if ("loadComments".equals(action)) {
            handleLoadComments(request, response);
            return;
        }

        String commentText = request.getParameter("comment");
        String userName = null;
        CommentDAO dao = new CommentDAO();
        MobilesDAO daoo = new MobilesDAO();

        try {
            if (loginUser != null) {
                userName = loginUser.getFullName();
            } else if (loginGmail != null) {
                userName = loginGmail.getName();
            }

            if (commentText != null && !commentText.trim().isEmpty()) {
                if (userName != null) {
                    dao.addComment(userName, commentText);
                } else {
                    dao.addComment("Guest", commentText);
                }
            }

            List<CommentDTO> comments = dao.getAllComments();
            request.setAttribute("comments", comments);
            if ("Sort".equals(action)) {
                String sortOrder = request.getParameter("ddlSort");
                session.setAttribute("selectedSort", sortOrder);

                if (sortOrder != null) {
                    boolean ascending = "ascending".equals(sortOrder);
                    List<MobilesDTO> sortedProducts = daoo.getProductsSortedByPrice(ascending);
                    session.setAttribute("products", sortedProducts); //set attribute danh sách đã sắp xếp
                }
            }

            // Chỗ này dùng products ở trên là mặc định sẽ là danh sách đã sắp xếp
            List<MobilesDTO> products = (List<MobilesDTO>) session.getAttribute("products");
            // Nếu danh sách sản phẩm chưa được sắp xếp nghĩa là list ở dòng 89 là null
            // Suy ra nó sẽ xài list mặc định được tải lên từ DB
            if (products == null) {
                products = daoo.getAllProducts();
                session.setAttribute("products", products);
            }
            request.setAttribute("products", products);

            // Chỗ này ta sẽ lấy message ở session bên addtocart đổi nó thành request xong hủy session là nó sẽ hiển thị 1 lần
            String message = (String) session.getAttribute("message");
            if (message != null) {
                request.setAttribute("message", message);
                session.removeAttribute("message"); // Clear message after displaying
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        }
    }

    private void handleLoadComments(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            CommentDAO dao = new CommentDAO();
            List<CommentDTO> comments = dao.getAllComments();
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();

            // Render trực tiếp HTML của phần bình luận
            for (CommentDTO comment : comments) {
                out.println("<div class='comment'>");
                out.println("<p><strong>" + comment.getUserId() + ":</strong> " + comment.getCommentText() + "</p>");
                out.println("<p><em>Posted on: " + new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(comment.getCommentDate()) + "</em></p>");
                out.println("</div>");
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    // Load cart từ cookie sau đó gắn vào session để hiển thị
    private CartBean getCartFromCookie(HttpServletRequest request) throws IOException {
        Cookie[] cookies = request.getCookies();
        String cookieName = null;
        HttpSession session = request.getSession();
        int cartItemCount = 0; // Default to 0 if no cookie found

        if (session.getAttribute("LOGIN_USER") != null) {
            String userId = (String) session.getAttribute("userId");
            cookieName = "USER_CART" + userId;
        } else if (session.getAttribute("LOGIN_GMAIL") != null) {
            String userEmail = (String) session.getAttribute("email");
            cookieName = "GMAIL_CART" + userEmail;
        } else {
            cookieName = "GUEST_CART";
        }

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookieName.equals(cookie.getName())) {
                    try {
                        byte[] cartBytes = Base64.getDecoder().decode(cookie.getValue());
                        CartBean cart = objectMapper.readValue(cartBytes, CartBean.class);
                        cartItemCount = cart.getTotalQuantity(); // Update cart item count from cookie

                        // Update cart item count in session
                        session.setAttribute("cartItemCount", cartItemCount);

                        return cart;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        // Nếu không có cookie mặc định số lượng là 0
        session.setAttribute("cartItemCount", cartItemCount);
        return null; // Return an empty cart
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

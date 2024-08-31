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
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Do Dat
 */
@WebServlet(name = "CheckOutOrderController", urlPatterns = {"/CheckOutOrderController"})
public class CheckOutOrderController extends HttpServlet {
    private static final String VIEW_ORDER_PAGE = "viewOrder.jsp";     

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
        String url = VIEW_ORDER_PAGE;
        String custName = request.getParameter("txtCusName");
        String custEmail = request.getParameter("txtEmail");
        String diachi = request.getParameter("txtDiaChi");
        String city = request.getParameter("city");
        String district = request.getParameter("district");
        String ward = request.getParameter("ward");
        String custAddress = diachi + "," + ward + ", " + district + ", " + city;      

        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                CartBean cart = (CartBean) session.getAttribute("CART");
                if (cart != null) {
                    Map<String, MobilesDTO> items = cart.getItems();
                    if (items != null) {
                        float totalPrice = cart.getTotalPrice();
                        int totalQuantity = cart.getTotalQuantity();
                        OrderDAO dao = new OrderDAO();
                        String orderId = dao.createOrderId();

                        Timestamp today = new Timestamp(System.currentTimeMillis());
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                        String formattedDate = sdf.format(today);
                                                                                          
                        session.setAttribute("ORDER_DATE", formattedDate);
                        session.setAttribute("CART_ITEMS", items.values());
                        session.setAttribute("CUST_NAME", custName);
                        session.setAttribute("CUST_EMAIL", custEmail);
                        session.setAttribute("CUST_ADDRESS", custAddress);
                        session.setAttribute("ORDER_ID", orderId);
                        session.setAttribute("TOTAL_QUANTITY", totalQuantity);
                        session.setAttribute("TOTAL_PRICE", totalPrice);                     
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(CheckOutOrderController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        }
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

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package datdt.controllers;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author Do Dat
 */
@WebServlet(name = "MainController", urlPatterns = {"/MainController"})
public class MainController extends HttpServlet {
    private static final String DEFAULT_PAGE = "home";
    private static final String VIEW_YOUR_CART = "ViewCartController";
    private static final String LOGIN_CONTROLLER = "LoginController";
    private static final String LOGOUT_CONTROLLER = "LogoutController";
    private static final String ADD_TO_CART = "AddToCartController";
    private static final String REMOVE_ITEM_FROM_CART_CONTROLLER = "RemoveItemFromCartController";
    private static final String CHECK_OUT_ORDER_CONTROLLER = "CheckOutOrderController";
    private static final String SEND_OTP_CONTROLLER = "SendOTPController";
    private static final String VERIFY_OTP_CONTROLLER = "VerifyOTPController";
    private static final String RESET_PASSWORD_CONTROLLER = "ResetPasswordController";
    private static final String SIGN_UP_CONTROLLER = "SignUpController";
    private static final String RETURN_TO_MARKET_CONTROLLER = "ReturnMarketController";
    private static final String DELETE_MOBILES_CONTROLLER = "DeleteMobilesController";
    private static final String PROCESS_PAYMENT_CONTROLLER = "ProcessPaymentController";
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
        String url = DEFAULT_PAGE;
        try {
            String action = request.getParameter("action");
            if (action == null) {
                url = DEFAULT_PAGE;
            } else if (action.equals("Login")) {
                url = LOGIN_CONTROLLER;
            } else if (action.equals("Logout")) {
                url = LOGOUT_CONTROLLER;
            } else if (action.equals("Add To Cart")) {//user click buy books
                url = ADD_TO_CART;
            } else if (action.equals("View Your Cart")) {
                url = VIEW_YOUR_CART;
            } else if (action.equals("Remove Selected Item")) {
                url = REMOVE_ITEM_FROM_CART_CONTROLLER;
            } else if (action.equals("CheckOut")) {
                url = CHECK_OUT_ORDER_CONTROLLER;
            } else if (action.equals("Send otp")) {
                url = SEND_OTP_CONTROLLER;
            } else if (action.equals("Verify otp")) {
                url = VERIFY_OTP_CONTROLLER;
            } else if (action.equals("Reset Password")) {
                url = RESET_PASSWORD_CONTROLLER;
            } else if (action.equals("Sign up")) {
                url = SIGN_UP_CONTROLLER;
            } else if (action.equals("Return to Market")) {
                url = RETURN_TO_MARKET_CONTROLLER;
            } else if (action.equals("DeleteMobile")) {
                url = DELETE_MOBILES_CONTROLLER;
            } else if (action.equals("Submit Review")) {
                url = DEFAULT_PAGE;
            } else if (action.equals("ProcessPayment")) {
                url = PROCESS_PAYMENT_CONTROLLER;
            }  
        } catch (Exception e) {
            log("Error at MainController: " + e.toString());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
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

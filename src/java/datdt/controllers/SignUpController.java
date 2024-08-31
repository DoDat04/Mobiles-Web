/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package datdt.controllers;

import datdt.users.RegistrationCreateError;
import datdt.users.UsersDAO;
import datdt.users.UsersDTO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;

/**
 *
 * @author Do Dat
 */
@WebServlet(name = "SignUpController", urlPatterns = {"/SignUpController"})
public class SignUpController extends HttpServlet {
    private static final String SIGN_UP_PAGE = "signUp.jsp";
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
        String fullName = request.getParameter("fullName");
        String userId = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        boolean foundErr = false;
        RegistrationCreateError errors = new RegistrationCreateError();//object de chua loi
        String url = SIGN_UP_PAGE;
        try {
            if (password.trim().length() < 6 || password.trim().length() > 50) {
                foundErr = true;
                errors.setPasswordLengthErr("Password is required from 6 to 50 characters");
            } else if (!confirmPassword.trim().equals(password.trim())) {
                foundErr = true;
                errors.setConfirmNotMacthed("Confirm must match password");
            }
            if (fullName.trim().length() < 2 || fullName.trim().length() > 50) {
                foundErr = true;
                errors.setPasswordLengthErr("Fullname is required from 6 to 50 characters");
            }
            if (foundErr) { //errors occur
                request.setAttribute("CREATE_ERROR", errors);
            } else { //no errors
                //3. call method of Model/DAO
                UsersDAO dao = new UsersDAO();
                UsersDTO dto = new UsersDTO(userId, password, fullName, "User", 0, "");
                boolean result = dao.createAccount(dto);
                if (result) {
                    url = SIGN_UP_PAGE;
                    request.setAttribute("CREATE_SUCCESS", "Your account was created successfully");
                }//creating account successfully
            }
        } catch (SQLException ex) {
            String msg = ex.getMessage();
            log("AddAccountServlet _ SQL: " + ex.getMessage());
            if (msg.contains("duplicate")) {
                errors.setUsernameIsExisted(userId + " is existed");
                request.setAttribute("CREATE_ERROR", errors);
            }
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
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

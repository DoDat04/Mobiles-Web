/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package datdt.controllers;

import datdt.users.UsersDAO;
import java.io.IOException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "SendOTPController", urlPatterns = {"/SendOTPController"})
public class SendOTPController extends HttpServlet {
    private static final String GMAIL_USERNAME = "dothanhdat339@gmail.com"; // Replace with your Gmail address
    private static final String GMAIL_APP_PASSWORD = "xels hpiv urjw joaa"; // Replace with your app password

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String email = request.getParameter("email");
        UsersDAO userDao = new UsersDAO();

        try {
            if (userDao.checkEmailExist(email)) {
                String otp = userDao.generateOTP();

                // Send the OTP to the email
                sendEmail(email, otp);

                // Set OTP in session to validate later
                request.getSession().setAttribute("OTP", otp);
                request.getSession().setAttribute("email", email);
                request.getSession().setAttribute("otpGeneratedTime", System.currentTimeMillis());

                // Redirect to OTP verification page
                response.sendRedirect("otpVerification.jsp");
            } else {
                // Handle case where email does not exist
                request.setAttribute("errorMessage", "Email does not exist!");
                request.getRequestDispatcher("forgotPassword.jsp").forward(request, response);
            }
        } catch (Exception e) {
            log("Error at SendOTPController: " + e.toString());
        }
    }

    private void sendEmail(String toEmail, String otp) throws Exception {
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
        message.setSubject("Your OTP Code");
        message.setText("Your OTP code is: " + otp);

        Transport.send(message);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Send OTP via Gmail";
    }
}

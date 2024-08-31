/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datdt.order;

import datdt.utils.DBUtils;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Do Dat
 */
public class OrderDAO implements Serializable {
    public String createOrderId() throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        String orderId = null;
        LocalDate currentDate = LocalDate.now();

        try {
            con = DBUtils.getConnection();

            String sqlGetLastOrderId = "SELECT MAX(id) AS lastOrderId FROM [Order] WHERE date = ? ";
            stm = con.prepareStatement(sqlGetLastOrderId);
            stm.setDate(1, java.sql.Date.valueOf(currentDate));
            rs = stm.executeQuery();

            if (rs.next()) {
                String lastOrderId = rs.getString("lastOrderId");

                if (lastOrderId != null) {
                    int lastNumber = Integer.parseInt(lastOrderId.substring(2)); 
                    int newNumber = lastNumber + 1;
                    orderId = String.format("Od%03d", newNumber); 
                } else {
                    orderId = "Od001"; // First order of the day
                }
            } else {
                // If no previous orders exist for the day, start with Od001
                orderId = "Od001";
            }

            // Ensure the generated order ID is valid
            if (orderId == null || orderId.isEmpty()) {
                throw new SQLException("Failed to generate valid order ID.");
            }

        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return orderId;
    }
    
    public String createOrder(String custName, String custEmail, String custAddress, int orderQuantity) throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        String orderId = null;
        LocalDate currentDate = LocalDate.now();

        try {
            con = DBUtils.getConnection();

            String sqlGetLastOrderId = "SELECT MAX(id) AS lastOrderId FROM [Order] WHERE date = ? ";
            stm = con.prepareStatement(sqlGetLastOrderId);
            stm.setDate(1, java.sql.Date.valueOf(currentDate));
            rs = stm.executeQuery();

            if (rs.next()) {
                String lastOrderId = rs.getString("lastOrderId");

                if (lastOrderId != null) {
                    int lastNumber = Integer.parseInt(lastOrderId.substring(2)); 
                    int newNumber = lastNumber + 1;
                    orderId = String.format("Od%03d", newNumber); 
                } else {
                    orderId = "Od001"; // First order of the day
                }
            } else {
                // If no previous orders exist for the day, start with Od001
                orderId = "Od001";
            }

            // Ensure the generated order ID is valid
            if (orderId == null || orderId.isEmpty()) {
                throw new SQLException("Failed to generate valid order ID.");
            }

            // Insert the order into the database
            String sqlInsertOrder = "INSERT INTO [Order](id, date, customer, address, email, total) VALUES(?, ?, ?, ?, ?, ?)";
            stm = con.prepareStatement(sqlInsertOrder);
            stm.setString(1, orderId);
            stm.setDate(2, java.sql.Date.valueOf(currentDate));
            stm.setString(3, custName);
            stm.setString(4, custAddress);
            stm.setString(5, custEmail);
            stm.setFloat(6, orderQuantity);
            stm.executeUpdate();

        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return orderId;
    }
    
    public List<OrderDTO> getAllOrders() throws SQLException, ClassNotFoundException {
        List<OrderDTO> ordersList = new ArrayList<>();
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBUtils.getConnection();
            if (con != null) {
                String sql = "SELECT id, date, customer, address, email, total "
                           + "FROM [Order] "
                           + "ORDER BY date ASC";
                
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();

                while (rs.next()) {
                    String id = rs.getString("id");
                    Timestamp date = rs.getTimestamp("date");
                    String customer = rs.getString("customer");
                    String address = rs.getString("address");
                    String email = rs.getString("email");
                    float total = rs.getFloat("total");

                    OrderDTO order = new OrderDTO(id, date, customer, address, email, total);
                    ordersList.add(order);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return ordersList;
    }
}

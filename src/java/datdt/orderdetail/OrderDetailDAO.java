/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datdt.orderdetail;

import datdt.utils.DBUtils;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


/**
 *
 * @author Do Dat
 */
public class OrderDetailDAO implements Serializable{
    public boolean addOrderDetail(OrderDetailDTO orderdetail) throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;
        try {
            // 1. get connection
            con = DBUtils.getConnection();
            if (con != null) {
                // 2. Create SQL string
                String sql = "INSERT INTO OrderDetail (mobileId, unitPrice, quantity, orderId, orderDate, total) "
                        + "VALUES (?, ?, ?, ?, ?, ?)";
                // 3. Create Statement
                stm = con.prepareStatement(sql);
                stm.setString(1, orderdetail.getProductId());
                stm.setDouble(2, orderdetail.getUnitPrice());
                stm.setInt(3, orderdetail.getQuantity());
                stm.setString(4, orderdetail.getOrderId());
                stm.setTimestamp(5, orderdetail.getOrderDate()); 
                stm.setDouble(6, orderdetail.getTotal());
                // 4. Execute query
                int affectedRows = stm.executeUpdate();
                // 5. Process result
                if (affectedRows > 0) {
                    result = true;
                } // Insert successfully
            } // Connect successfully
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return result;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datdt.tempcart;

import datdt.utils.DBUtils;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

/**
 *
 * @author Do Dat
 */
public class TempCartDAO implements Serializable{
    public void addItemToCart(String mobileId, int quantity) throws SQLException, ClassNotFoundException{
        Connection con = null;
        PreparedStatement stm = null;
        
        try {
            con = DBUtils.getConnection();
            if (con != null) {
                String sql = "INSERT INTO TempCart (mobileId, quantity) VALUES (?, ?) ";
                stm = con.prepareStatement(sql);
                stm.setString(1, mobileId);
                stm.setInt(2, quantity);
                stm.executeUpdate();
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }    
}

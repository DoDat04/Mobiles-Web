/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datdt.mobiles;

import datdt.utils.DBUtils;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Do Dat
 */
public class MobilesDAO implements Serializable {
    public List<MobilesDTO> getAllProducts() throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<MobilesDTO> products = new ArrayList<>();

        try {
            con = DBUtils.getConnection(); // Assuming DBHelper provides a valid database connection
            if (con != null) {
                String sql = "SELECT mobileId, description, price, mobileName, yearOfProduction, quantity "
                        + "FROM Mobiles "
                        + "WHERE notSale = 1";
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();

                while (rs.next()) {
                    String mobileId = rs.getString("mobileId");
                    String description = rs.getString("description");
                    double price = rs.getDouble("price");
                    String mobileName = rs.getString("mobileName");
                    int yearOfProduction = rs.getInt("yearOfProduction");
                    int quantity = rs.getInt("quantity");
                    MobilesDTO dto = new MobilesDTO(mobileId, description, price, mobileName, yearOfProduction, quantity, true);
                    products.add(dto);
                }
            }
        } finally {
            // Close all resources in reverse order of creation
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
        return products;
    }

    public boolean deleteMobiles(String mobileId) throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;

        try {
            con = DBUtils.getConnection();
            if (con != null) {
                String sql = "UPDATE Mobiles "
                        + "SET notSale = 0"
                        + "WHERE mobileId = ? ";
                stm = con.prepareStatement(sql);
                stm.setString(1, mobileId);
                int affectedRows = stm.executeUpdate();
                if (affectedRows > 0) {
                    result = true;
                }
            }
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

    public List<MobilesDTO> getProductsSortedByPrice(boolean ascending) throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<MobilesDTO> products = new ArrayList<>();

        try {
            con = DBUtils.getConnection();
            if (con != null) {
                String sql = "SELECT mobileId, description, price, mobileName, yearOfProduction, quantity "
                        + "FROM Mobiles "
                        + "WHERE notSale = 1 "
                        + "ORDER BY price " + (ascending ? "ASC" : "DESC");
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();

                while (rs.next()) {
                    String mobileId = rs.getString("mobileId");
                    String description = rs.getString("description");
                    double price = rs.getDouble("price");
                    String mobileName = rs.getString("mobileName");
                    int yearOfProduction = rs.getInt("yearOfProduction");
                    int quantity = rs.getInt("quantity");
                    MobilesDTO dto = new MobilesDTO(mobileId, description, price, mobileName, yearOfProduction, quantity, true);
                    products.add(dto);
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
        return products;
    }
}

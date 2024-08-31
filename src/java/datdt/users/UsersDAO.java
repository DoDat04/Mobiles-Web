/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datdt.users;

import datdt.utils.DBUtils;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

/**
 *
 * @author Do Dat
 */
public class UsersDAO implements Serializable {
    public UsersDTO checkLogin(String userId, String password) throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        UsersDTO result = null;

        try {
            con = DBUtils.getConnection();
            if (con != null) {//11
                String sql = "SELECT fullName, role, gmailID, gmailLink "
                        + "FROM Users "
                        + "WHERE userId = ? "
                        + "AND password = ? ";
                stm = con.prepareStatement(sql);
                stm.setString(1, userId);
                stm.setString(2, password);
                rs = stm.executeQuery();
                if (rs.next()) {
                    String fullName = rs.getString("fullName");
                    String role = rs.getString("role");
                    int gmailID = rs.getInt("gmailID");
                    String gmailLink = rs.getString("gmailLink");
                    result = new UsersDTO(userId, "", fullName, role, gmailID, gmailLink);
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
        return result;
    }

    public boolean checkEmailExist(String email) throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        boolean exist = false;

        try {
            con = DBUtils.getConnection();
            if (con != null) {
                String sql = "SELECT userId "
                        + "FROM Users "
                        + "WHERE userId = ? ";
                stm = con.prepareStatement(sql);
                stm.setString(1, email);
                rs = stm.executeQuery();
                if (rs.next()) {
                    exist = true;
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
        return exist;
    }

    public boolean resetPassword(String email, String passReset) throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        boolean success = false;

        try {
            con = DBUtils.getConnection();
            if (con != null) {
                String sql = "UPDATE Users "
                        + "SET password = ? "
                        + "WHERE userId = ? ";
                stm = con.prepareStatement(sql);
                stm.setString(1, passReset);
                stm.setString(2, email);
                int rowsUpdated = stm.executeUpdate();
                if (rowsUpdated > 0) {
                    success = true;
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
        return success;
    }
    
    public boolean createAccount(UsersDTO dto) throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;
        
        try {
            con = DBUtils.getConnection();
            if (con != null) {
                String sql = "INSERT INTO Users("
                        + "userId,password,fullName,role,gmailID, gmailLink"
                        + ") VALUES("
                        + "?,?,?,?,?,?"
                        + ")";
                stm = con.prepareStatement(sql);
                stm.setString(1, dto.getUserId());
                stm.setString(2, dto.getPassword());
                stm.setString(3, dto.getFullName());
                stm.setString(4, dto.getRole());
                stm.setInt(5, dto.getGmailID());
                stm.setString(6, dto.getGmailLink());
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

    public String generateOTP() {
        Random random = new Random();
        int otp = random.nextInt(1000000);
        return String.format("%06d", otp);
    }
}

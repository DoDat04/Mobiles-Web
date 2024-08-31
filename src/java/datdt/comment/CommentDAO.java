/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datdt.comment;

import datdt.utils.DBUtils;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Do Dat
 */
public class CommentDAO implements Serializable{
    public boolean addComment(String userId, String commentText) throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;

        try {
            con = DBUtils.getConnection();
            if (con != null) {
                String sql = "INSERT INTO Comment(userId, commentText, commentDate) VALUES(?, ?, CURRENT_TIMESTAMP)";
                stm = con.prepareStatement(sql);
                stm.setString(1, userId);
                stm.setString(2, commentText);
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
    
    public List<CommentDTO> getAllComments() throws SQLException, ClassNotFoundException {
        List<CommentDTO> comments = new ArrayList<>();
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBUtils.getConnection();
            if (con != null) {
                String sql = "SELECT userId, commentText, commentDate FROM Comment";
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();

                while (rs.next()) {
                    String userId = rs.getString("userId");
                    String commentText = rs.getString("commentText");
                    Timestamp commentDate = rs.getTimestamp("commentDate");

                    comments.add(new CommentDTO(userId, commentText, commentDate));
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

        return comments;
    }
}

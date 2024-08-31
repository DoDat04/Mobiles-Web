/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datdt.comment;

import java.io.Serializable;
import java.sql.Timestamp;
/**
 *
 * @author Do Dat
 */
public class CommentDTO implements Serializable{
    private int commentId;
    private String userId;
    private String commentText;
    private Timestamp commentDate;

    public CommentDTO() {
    }

    public CommentDTO(String userId, String commentText, Timestamp commentDate) {
        this.userId = userId;
        this.commentText = commentText;
        this.commentDate = commentDate;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public Timestamp getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Timestamp commentDate) {
        this.commentDate = commentDate;
    }      
}

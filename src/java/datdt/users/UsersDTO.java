/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datdt.users;

import java.io.Serializable;

/**
 *
 * @author Do Dat
 */
public class UsersDTO implements Serializable{
    private String userId;
    private String password;
    private String fullName;
    private String role;
    private int gmailID;
    private String gmailLink;

    public UsersDTO() {
    }

    public UsersDTO(String userId, String password, String fullName, String role, int gmailID, String gmailLink) {
        this.userId = userId;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
        this.gmailID = gmailID;
        this.gmailLink = gmailLink;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getGmailID() {
        return gmailID;
    }

    public void setGmailID(int gmailID) {
        this.gmailID = gmailID;
    }

    public String getGmailLink() {
        return gmailLink;
    }

    public void setGmailLink(String gmailLink) {
        this.gmailLink = gmailLink;
    }        
}

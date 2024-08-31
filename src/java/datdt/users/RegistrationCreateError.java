/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datdt.users;

import java.io.Serializable;

/**
 *
 * @author Do Dat
 */
public class RegistrationCreateError implements Serializable{
    private String passwordLengthErr;
    private String fullnameLengthErr;
    private String confirmNotMacthed;
    private String usernameIsExisted;

    public RegistrationCreateError() {
    }

    public RegistrationCreateError(String passwordLengthErr, String fullnameLengthErr, String confirmNotMacthed, String usernameIsExisted) {
        this.passwordLengthErr = passwordLengthErr;
        this.fullnameLengthErr = fullnameLengthErr;
        this.confirmNotMacthed = confirmNotMacthed;
        this.usernameIsExisted = usernameIsExisted;
    }

    public String getPasswordLengthErr() {
        return passwordLengthErr;
    }

    public void setPasswordLengthErr(String passwordLengthErr) {
        this.passwordLengthErr = passwordLengthErr;
    }

    public String getFullnameLengthErr() {
        return fullnameLengthErr;
    }

    public void setFullnameLengthErr(String fullnameLengthErr) {
        this.fullnameLengthErr = fullnameLengthErr;
    }

    public String getConfirmNotMacthed() {
        return confirmNotMacthed;
    }

    public void setConfirmNotMacthed(String confirmNotMacthed) {
        this.confirmNotMacthed = confirmNotMacthed;
    }

    public String getUsernameIsExisted() {
        return usernameIsExisted;
    }

    public void setUsernameIsExisted(String usernameIsExisted) {
        this.usernameIsExisted = usernameIsExisted;
    }
}

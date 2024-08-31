/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datdt.tempcart;

import java.io.Serializable;

/**
 *
 * @author Do Dat
 */
public class TempCartDTO implements Serializable{
    private int tempCartId;
    private String mobileId;
    private int quantity;

    public TempCartDTO() {
    }

    public TempCartDTO(String mobileId, int quantity) {
        this.mobileId = mobileId;
        this.quantity = quantity;
    }

    public int getTempCartId() {
        return tempCartId;
    }

    public void setTempCartId(int tempCartId) {
        this.tempCartId = tempCartId;
    }

    public String getMobileId() {
        return mobileId;
    }

    public void setMobileId(String mobileId) {
        this.mobileId = mobileId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

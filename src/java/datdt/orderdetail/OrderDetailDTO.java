/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datdt.orderdetail;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author Do Dat
 */
public class OrderDetailDTO implements Serializable {
    private String productId;
    private double unitPrice;
    private int quantity;
    private String orderId;
    private Timestamp orderDate; 
    private double total;

    public OrderDetailDTO(String productId, double unitPrice, int quantity, String orderId, Timestamp orderDate, double total) {
        this.productId = productId;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.total = total;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}

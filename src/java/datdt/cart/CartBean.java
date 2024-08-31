/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datdt.cart;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import datdt.mobiles.MobilesDTO;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Do Dat
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CartBean implements Serializable {

    private Map<String, MobilesDTO> items;

    public Map<String, MobilesDTO> getItems() {
        return items;
    }

    public void addItemTocart(MobilesDTO item) {
        if (item == null) {
            return;
        }
        // 1. Kiểm tra xem giỏ hàng có tồn tại chưa
        if (this.items == null) {
            this.items = new HashMap<>();
        } // Giỏ hàng chưa tồn tại

        String mobileId = item.getMobileId();

        // 2. Kiểm tra xem sản phẩm đã tồn tại trong giỏ hàng chưa
        if (this.items.containsKey(mobileId)) {
            // Sản phẩm đã tồn tại, tăng số lượng lên 1
            MobilesDTO existingItem = this.items.get(mobileId);
            existingItem.setQuantity(existingItem.getQuantity() + 1);
        } else {
            // Sản phẩm chưa tồn tại, đặt số lượng là 1
            item.setQuantity(1);
            this.items.put(mobileId, item);
        }
    }

    public void removeItemFromCart(String mobileId) {
        if (mobileId == null || mobileId.trim().isEmpty()) {
            return;
        }

        // 1. Kiểm tra giỏ hàng có tồn tại không
        if (this.items == null) {
            return;
        }

        // 2. Kiểm tra sản phẩm có tồn tại trong giỏ hàng không
        if (this.items.containsKey(mobileId)) {
            MobilesDTO existingItem = this.items.get(mobileId);

            // 3. Giảm số lượng sản phẩm đi 1 nếu số lượng lớn hơn 1
            if (existingItem.getQuantity() > 1) {
                existingItem.setQuantity(existingItem.getQuantity() - 1);
            } else {
                // 4. Nếu số lượng sản phẩm là 1, xóa sản phẩm khỏi giỏ hàng
                this.items.remove(mobileId);

                // Nếu giỏ hàng rỗng sau khi xóa, đặt giỏ hàng về null
                if (this.items.isEmpty()) {
                    this.items = null;
                }
            }
        }
    }

    public int getItemQuantity(String mobileId) {
        MobilesDTO item = this.items.get(mobileId);
        return item != null ? item.getQuantity() : 0;
    }

    @JsonProperty("totalQuantity")
    public int getTotalQuantity() {
        int totalQuantity = 0;
        if (items != null) {
            for (MobilesDTO product : items.values()) {
                totalQuantity += product.getQuantity();
            }
        }
        return totalQuantity;
    }

    public float getTotalPrice() {
        float totalPrice = 0;
        if (items != null) {
            for (MobilesDTO product : items.values()) {
                totalPrice += product.getPrice() * product.getQuantity();
            }
        }
        return totalPrice;
    }
}

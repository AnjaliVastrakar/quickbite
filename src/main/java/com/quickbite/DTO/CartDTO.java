
package com.quickbite.DTO;

import java.math.BigDecimal;

public class CartDTO {

    private Long id;

    private Long userId;

    private Long menuItemId;

    private String menuItemName;

    private Integer quantity;

    private BigDecimal price;

    public CartDTO() {
    }

    public CartDTO(Long id, Long userId, Long menuItemId,
                   String menuItemName, Integer quantity,
                   BigDecimal price) {

        this.id = id;
        this.userId = userId;
        this.menuItemId = menuItemId;
        this.menuItemName = menuItemName;
        this.quantity = quantity;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(Long menuItemId) {
        this.menuItemId = menuItemId;
    }

    public String getMenuItemName() {
        return menuItemName;
    }

    public void setMenuItemName(String menuItemName) {
        this.menuItemName = menuItemName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}

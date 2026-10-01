
package com.quickbite.Entity;

import java.math.BigDecimal;

import jakarta.persistence.*;

@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_seq")
    @SequenceGenerator(
            name = "cart_seq",
            sequenceName = "CART_SEQ",
            allocationSize = 1
    )
    private Long id;

    private Long userId;

    private Long menuItemId;

    private Integer quantity;

    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    public Cart() {
    }

    public Cart(Long id, Long userId, Long menuItemId,
                Integer quantity, BigDecimal price) {
        this.id = id;
        this.userId = userId;
        this.menuItemId = menuItemId;
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


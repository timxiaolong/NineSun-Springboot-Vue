package com.springboot.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author xl
 * @since 2025-06-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="CartItem对象", description="")
public class CartItem implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer cartItemId;

    private String name;

    private Integer quantity;

    private String picUrl;

    private Integer price;

    public Integer getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(Integer cartItemId) {
        this.cartItemId = cartItemId;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public CartItem(String name, Integer quantity, String picUrl, Integer price,Integer cartItemId) {
        this.cartItemId = cartItemId;
        this.price = price;
        this.name = name;
        this.quantity = quantity;
        this.picUrl = picUrl;
    }
}

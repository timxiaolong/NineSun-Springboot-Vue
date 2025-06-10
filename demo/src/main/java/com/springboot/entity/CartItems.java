package com.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@ApiModel(value="CartItems对象", description="")
public class CartItems implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "cart_item_id", type = IdType.AUTO)
    private Integer cartItemId;

    private Integer userId;

    private Integer productId;

    private Integer quantity;

    private LocalDateTime addedAt;


}

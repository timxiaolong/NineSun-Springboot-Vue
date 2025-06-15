package com.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author xl
 * @since 2025-06-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="OrderItems对象", description="")
public class OrderItems implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "order_items_id", type = IdType.AUTO)
    private Integer orderItemsId;

    private String name;

    private Integer price;

    private Integer productId;

    private String quantity;

    private String amount;

    private Integer orderId;


}

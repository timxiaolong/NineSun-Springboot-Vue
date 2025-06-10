package com.springboot.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
@ApiModel(value="Products对象", description="")
public class Products implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "product_id", type = IdType.AUTO)
    private Integer productId;

    private String name;

    private String description;

    private BigDecimal price;

    private Integer stockQuantity;

    private Integer categoryId;

    private Boolean isActive;


}

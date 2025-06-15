package com.springboot.mapper;

import com.springboot.entity.OrderItems;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xl
 * @since 2025-06-14
 */
public interface OrderItemsMapper extends BaseMapper<OrderItems> {
    @Select("select * from order_items as o left join products as p on o.product_id = p.product_id where o.order_id = #{orderId}")
    List<OrderItems> getOrderItems(Integer orderId);
}

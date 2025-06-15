package com.springboot.mapper;

import com.springboot.entity.CartItem;
import com.springboot.entity.CartItems;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xl
 * @since 2025-06-07
 */
public interface CartItemsMapper extends BaseMapper<CartItems> {
    @Select("select c.cart_item_id,p.name,c.quantity,p.pic_url,p.price,p.product_id from cart_items c " +
            "left join supermarket.products p on p.product_id = c.product_id " +
            "where c.user_id = #{userid}")
    List<CartItem> getUserCart(Integer userid);
}

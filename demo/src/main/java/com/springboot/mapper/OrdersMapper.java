package com.springboot.mapper;

import com.springboot.entity.OrderItems;
import com.springboot.entity.Orders;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
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
public interface OrdersMapper extends BaseMapper<Orders> {

}

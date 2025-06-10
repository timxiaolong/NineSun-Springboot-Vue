package com.springboot.service.impl;

import com.springboot.entity.OrderItems;
import com.springboot.mapper.OrderItemsMapper;
import com.springboot.service.OrderItemsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xl
 * @since 2025-06-07
 */
@Service
public class OrderItemsServiceImpl extends ServiceImpl<OrderItemsMapper, OrderItems> implements OrderItemsService {

}

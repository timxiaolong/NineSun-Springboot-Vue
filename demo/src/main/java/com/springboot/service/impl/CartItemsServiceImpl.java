package com.springboot.service.impl;

import com.springboot.entity.CartItems;
import com.springboot.mapper.CartItemsMapper;
import com.springboot.service.CartItemsService;
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
public class CartItemsServiceImpl extends ServiceImpl<CartItemsMapper, CartItems> implements CartItemsService {

}

package com.springboot.controller;

import com.springboot.entity.OrderItems;
import com.springboot.mapper.OrderItemsMapper;
import com.springboot.service.OrderItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xl
 * @since 2025-06-14
 */
@CrossOrigin
@RestController
@RequestMapping("/order-items")
public class OrderItemsController {
    @Autowired
    private OrderItemsService orderItemsService;

    @Autowired
    private OrderItemsMapper orderItemsMapper;

    @GetMapping("/getOrderItems")
    public List<OrderItems> getOrderItems(@RequestParam Integer orderId) {
        return orderItemsMapper.getOrderItems(orderId);
    }
}

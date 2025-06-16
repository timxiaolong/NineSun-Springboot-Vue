package com.springboot.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.springboot.entity.Message;
import com.springboot.entity.OrderItems;
import com.springboot.entity.Orders;
import com.springboot.mapper.OrdersMapper;
import com.springboot.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xl
 * @since 2025-06-07
 */
@CrossOrigin
@RestController
@RequestMapping("/orders")
public class OrdersController {
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private OrdersMapper ordersMapper;

    @GetMapping("/getUserOrders")
    public List<Orders> getUserOders(@RequestParam Integer userId) {
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Orders::getUserId, userId);
        List<Orders> list = ordersMapper.selectList(queryWrapper);
        return list;
    }

    @GetMapping("/getAllOrders")
    public List<Orders> getAllOrders() {
        return ordersMapper.selectList(null);
    }

    @PostMapping("/saveOrUpdateOrders")
    public Message saveOrUpdateOrders(@RequestBody Orders orders){
        if (ordersService.saveOrUpdate(orders)){
            return new Message("更新成功", 200, null);
        }else  {
            return new Message("更新失败", 200, null);
        }
    }

}

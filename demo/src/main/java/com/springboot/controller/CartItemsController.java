package com.springboot.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.springboot.entity.*;
import com.springboot.mapper.CartItemsMapper;
import com.springboot.mapper.OrderItemsMapper;
import com.springboot.mapper.OrdersMapper;
import com.springboot.service.CartItemsService;
import com.springboot.service.OrdersService;
import com.springboot.service.ProductsService;
import com.springboot.service.UsersService;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
@RequestMapping("/cart-items")
public class CartItemsController {
    @Autowired
    private CartItemsService cartItemsService;
    @Autowired
    private CartItemsMapper cartItemsMapper;
    @Autowired
    private UsersService usersService;
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private OrderItemsMapper orderItemsMapper;

    @GetMapping("/getcart")
    public List<CartItem> getCart(@RequestParam Integer userId) {
        System.out.println(cartItemsMapper.getUserCart(userId));
        return cartItemsMapper.getUserCart(userId);
    }

    @DeleteMapping("/deleteCart")
    public Message deleteCart(@RequestParam Integer Id) {
        Integer result = cartItemsMapper.deleteById(Id);
        if (result == 1) {
            return new Message("删除成功",200,null);
        } else {
            return new Message("删除失败，请联系管理员",400,null);
        }
    }

    @DeleteMapping("/deleteAllCart")
    public Message deleteAllCart(@RequestParam Integer userId){
        LambdaQueryWrapper<CartItems> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CartItems::getUserId,userId);
        if (cartItemsService.remove(queryWrapper)){
            return new Message("删除成功",200,null);
        }else  {
            return new Message("删除失败",400,null);
        }
    }

    @PutMapping("/addToCart")
    public Message addToCart(@RequestParam Integer userId, @RequestParam Integer productId) {
        CartItems cartItems = new CartItems();
        cartItems.setUserId(userId);
        cartItems.setProductId(productId);
        cartItems.setQuantity(1);
        cartItems.setAddedAt(LocalDateTime.now());
        if(cartItemsService.save(cartItems))
        {
            return new Message("添加成功",200,null);
        }else {
            return new Message("添加失败",400,null);
        }
    }

    @PostMapping("/checkout")
    public Message checkout(@RequestBody List<CartItem> list,@RequestParam("userId") Integer userId) {
        //先存入一个订单，获取到订单号
        Orders orders = new Orders();
        orders.setUserId(userId);
        orders.setStatus("已下单");
        orders.setOrderTime(LocalDateTime.now());
        Users users = usersService.getById(userId);
        orders.setShippingAddress(users.getAddress());
        ordersMapper.insert(orders);
        Integer orderId = orders.getOrderId();
        System.out.println(orderId);
        for(CartItem item:list){
            OrderItems orderItems = new OrderItems();
            orderItems.setOrderId(orderId);
            orderItems.setQuantity(String.valueOf(item.getQuantity()));
            orderItems.setProductId(item.getProductId());
            orderItems.setAmount(String.valueOf(item.getPrice() *  item.getQuantity()));
            orderItemsMapper.insert(orderItems);
        }
        return null;
    }
}

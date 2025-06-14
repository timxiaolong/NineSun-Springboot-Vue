package com.springboot.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.springboot.entity.*;
import com.springboot.mapper.CartItemsMapper;
import com.springboot.service.CartItemsService;
import com.springboot.service.ProductsService;
import com.springboot.service.UsersService;
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
@RequestMapping("/cart-items")
public class CartItemsController {
    @Autowired
    private CartItemsService cartItemsService;
    @Autowired
    private CartItemsMapper cartItemsMapper;
    @Autowired
    private UsersService usersService;

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

    @PutMapping("/addToCart")
    public Message addToCart(@RequestParam Integer userId, @RequestParam Integer productId) {

        cartItemsService.save(new CartItems());
        return null;
    }

}

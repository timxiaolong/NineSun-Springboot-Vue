package com.springboot.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.springboot.entity.*;
import com.springboot.mapper.CartItemsMapper;
import com.springboot.mapper.OrderItemsMapper;
import com.springboot.mapper.OrdersMapper;
import com.springboot.mapper.UsersMapper;
import com.springboot.service.AdminsService;
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
@RequestMapping("/admins")
public class AdminsController {
    @Autowired
    private AdminsService adminsService;
    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private CartItemsMapper cartItemsMapper;
    @Autowired
    private OrdersMapper ordersMapper;

    @GetMapping("/login")
    public Message Login(@RequestParam("username") String username, @RequestParam("password") String password) {
        LambdaQueryWrapper<Admins> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Admins::getUsername, username);
        Admins admins = adminsService.getOne(lambdaQueryWrapper);
        if (admins == null) {
            return new Message("用户名或密码有误", 400, null);
        } else if (!admins.getPassword().equals(password)) {
            return new Message("用户名或密码有误", 400, null);
        } else {
            return new Message("成功登录", 200, admins);
        }
    }

    @GetMapping("/getAllUsers")
    public List<Users> getAllUsers() {
        return usersMapper .selectList(null);
    }

    @DeleteMapping("/deleteUserById")
    public Message deleteUserById(@RequestParam Integer userId){
        QueryWrapper<CartItems> queryWrapper = new QueryWrapper<>();
        cartItemsMapper.delete(queryWrapper.lambda().eq(CartItems::getUserId,userId));

        QueryWrapper<Orders> queryWrapper1 = new QueryWrapper<>();
        ordersMapper.delete(queryWrapper1.lambda().eq(Orders::getUserId,userId));


        if (usersMapper.deleteById(userId)==1){
            return new Message("删除成功", 200, null);
        }else  {
            return new Message("删除失败", 400, null);
        }
    }

    @PostMapping("/changeUserInfo")
    public Message changeUserInfo(@RequestBody Users users){
        if (usersMapper.updateById(users) == 1){
            return new Message("更改成功", 200, null);
        }else  {
            return new Message("更改失败", 400, null);
        }
    }
}

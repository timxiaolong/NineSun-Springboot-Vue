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

    @GetMapping("/getAdminInfo")
    public Admins getAdminInfo(@RequestParam Integer adminId){
        return adminsService.getById(adminId);
    }

    @PostMapping("/saveAdminInfo")
    public Message saveAdminInfo(@RequestBody Admins admins){
        if (adminsService.updateById(admins)){
            return new Message("修改成功", 200, null);
        }else  {
            return new Message("修改失败", 400, null);
        }
    }

    @PostMapping("/changePassword")
    public Message changePassword(@RequestParam Integer adminId,@RequestParam String oldPassword, @RequestParam String newPassword, @RequestParam String rePassword){
        if (!newPassword.equals(rePassword)){
            return new Message("两次密码输入错误", 400, null);
        }
        LambdaQueryWrapper<Admins> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Admins::getAdminId,adminId);
        Admins admins = adminsService.getOne(lambdaQueryWrapper);
        if (!admins.getPassword().equals(oldPassword)){
            return new Message("旧密码输入错误，请重试", 400, null);
        }else {
            admins.setPassword(newPassword);
            adminsService.updateById(admins);
            return new Message("修改成功，正在返回登录界面", 200, null);
        }
    }
}

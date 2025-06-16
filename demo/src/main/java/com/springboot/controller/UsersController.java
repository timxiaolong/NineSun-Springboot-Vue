package com.springboot.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.springboot.entity.CartItems;
import com.springboot.entity.Message;
import com.springboot.entity.Products;
import com.springboot.entity.Users;
import com.springboot.mapper.ProductsMapper;
import com.springboot.mapper.UsersMapper;
import com.springboot.service.ProductsService;
import com.springboot.service.UsersService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private UsersService usersService;
    @Autowired
    private ProductsService productsService;

    @GetMapping("/login")
    public Message Login(@RequestParam("username") String username, @RequestParam("password") String password){
        LambdaQueryWrapper<Users> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Users::getUsername,username);
        Users users = usersService.getOne(lambdaQueryWrapper);
        System.out.println(users);
        if(users==null){
            return new Message("用户名或密码有误",400,null);
        }else if(!users.getPassword().equals(password)){
            return new Message("用户名或密码有误",400,null);
        }else {
            return new Message("成功登录",200,users);
        }
    }

    @PostMapping("/signup")
    public Message signup(@RequestBody Users users){
        try {
            if(usersService.save(users))
                return new Message("注册成功，正在返回登录页面",200,users);
            else  return new Message("注册失败，请重试",400,null);
        }catch (Exception e){
            return new Message("注册失败，请重试",400,null);
        }
    }

    @GetMapping("/getUserInfo")
    public Users getUserInfo(@RequestParam Integer id){
        LambdaQueryWrapper<Users> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Users::getUserId,id);
        return usersService.getOne(lambdaQueryWrapper);
    }

    @PostMapping("/changeUserInfo")
    public Message changeUserInfo(@RequestBody Users users){
        System.out.println(users);
        if (usersService.updateById(users)){
            return new Message("更改成功",200,null);
        }else  {
            return new Message("更改失败",400,null);
        }
    }

    @PostMapping("/changeUserPassword")
    public Message changeUserPassword(@RequestParam String oldPassword, @RequestParam String newPassword, @RequestParam Integer userId){
        LambdaQueryWrapper<Users> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Users::getUserId,userId).eq(Users::getPassword,oldPassword);
        Users users = usersService.getOne(lambdaQueryWrapper);
        if (users!=null){
            users.setPassword(newPassword);
            if (usersService.updateById(users)){
                return new Message("修改成功！即将跳转至主页面",200,null);
            }else {
                return new Message("修改失败，请检查所填信息",400,null);
            }
        }else  {
            return new Message("修改失败，请检查所填信息",400,null);
        }
    }
}

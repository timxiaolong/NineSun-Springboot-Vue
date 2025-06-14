package com.springboot.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.springboot.entity.Admins;
import com.springboot.entity.Message;
import com.springboot.entity.Users;
import com.springboot.service.AdminsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}

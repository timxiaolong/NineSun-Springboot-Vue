package com.springboot.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.springboot.entity.Products;
import com.springboot.service.ProductsService;
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
@RequestMapping("/products")
public class ProductsController {
    @Autowired
    private ProductsService productsService;

    @GetMapping("/getSingleProducts")
    public Products getProducts(@RequestParam Integer productId) {
        LambdaQueryWrapper<Products> queryWrapper = new LambdaQueryWrapper<Products>();
        queryWrapper.eq(Products::getProductId, productId);
        return productsService.getOne(queryWrapper);
    }
}

package com.springboot.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.springboot.entity.Message;
import com.springboot.entity.Products;
import com.springboot.service.ProductsService;
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

    @GetMapping("/getproducts")
    public List<Products> getProducts(){
        LambdaQueryWrapper<Products> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.select(Products::getProductId,Products::getName,Products::getDescription,Products::getPrice,Products::getPicUrl,Products::getCategoryId,Products::getStockQuantity);
        return productsService.list(lambdaQueryWrapper);
    }

    @PostMapping("/addOrSaveProduct")
    public Message addProduct(@RequestBody Products products){
        System.out.println(products);
        if (productsService.saveOrUpdate(products)){
            return new Message("保存成功", 200, null);
        }else  {
            return new Message("保存失败", 400, null);
        }

    }

    @DeleteMapping("/deleteProduct")
    public Message deleteProduct(@RequestParam Integer productId){
        if (productsService.removeById(productId)){
            return new Message("删除成功", 200, null);
        }else  {
            return new Message("删除失败", 400, null);
        }
    }
}

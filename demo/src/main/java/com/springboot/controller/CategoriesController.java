package com.springboot.controller;


import com.springboot.entity.Categories;
import com.springboot.mapper.CategoriesMapper;
import com.springboot.service.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/categories")
public class CategoriesController {
    @Autowired
    private CategoriesService categoriesService;
    @Autowired
    private CategoriesMapper categoriesMapper;

    @GetMapping("/getAllCategories")
    public List<Categories> getAllCategories() {
        return categoriesMapper.selectList(null);
    }
}

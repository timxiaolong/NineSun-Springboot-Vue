package com.springboot.service.impl;

import com.springboot.entity.Categories;
import com.springboot.mapper.CategoriesMapper;
import com.springboot.service.CategoriesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xl
 * @since 2025-06-07
 */
@Service
public class CategoriesServiceImpl extends ServiceImpl<CategoriesMapper, Categories> implements CategoriesService {

}

package com.springboot.service.impl;

import com.springboot.entity.Admins;
import com.springboot.mapper.AdminsMapper;
import com.springboot.service.AdminsService;
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
public class AdminsServiceImpl extends ServiceImpl<AdminsMapper, Admins> implements AdminsService {

}

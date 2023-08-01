package com.cui.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cui.entity.Dish;
import com.cui.mapper.DishMapper;
import com.cui.service.DishService;
import org.springframework.stereotype.Service;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements  DishService{
}

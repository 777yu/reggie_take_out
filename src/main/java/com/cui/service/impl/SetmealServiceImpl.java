package com.cui.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cui.entity.Setmeal;
import com.cui.mapper.SetmealMapper;
import com.cui.service.SetmealService;
import org.springframework.stereotype.Service;


@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
}

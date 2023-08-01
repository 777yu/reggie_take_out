package com.cui.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cui.common.R;
import com.cui.entity.Category;
import com.cui.entity.Dish;
import com.cui.entity.Setmeal;
import com.cui.mapper.CategoryMapper;
import com.cui.service.CategoryService;
import com.cui.service.DishService;
import com.cui.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    public void remove(Long id){
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();

        dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);

        int count = dishService.count(dishLambdaQueryWrapper);

        if (count > 0 ){
            throw new RuntimeException("当前分页关联了菜品 不能删除");
        }
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();

        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);

        int count1 = setmealService.count(setmealLambdaQueryWrapper);

        if (count1 > 0 ){
            throw new RuntimeException("当前分页关联了套餐 不能删除");
        }

        super.removeById(id);
    }
}

package com.cui.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cui.common.R;
import com.cui.entity.Category;
import com.cui.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.security.krb5.internal.PAData;

import java.util.List;

@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 保存分类信息
     * @param category
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody Category category){
        log.info("新增分类{}",category);
        categoryService.save(category);
        return R.success("新增分类成功");
    }

    /**
     * 分类分页查询
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize){

        Page<Category> categoryPageInfo = new Page<>(page,pageSize);

        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.orderByDesc(Category::getSort);

        categoryService.page(categoryPageInfo, queryWrapper);

        return R.success(categoryPageInfo);

    }


    /**
     * 删除分类
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> remove(Long ids){
        log.info("删除的id为 {}" ,ids);

        categoryService.remove(ids);

        return R.success("删除分类成功");
    }

    /**
     * 修改分类信息
     * @param category
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody Category category) {
        log.info("开始修改分类信息");
        categoryService.updateById(category);
        return R.success("修改成功");
    }

    /**
     * 查询菜品分类
     * @param category
     * @return
     */

    @GetMapping("/list")
    public R<List<Category>> list( Category category){
        // 创建条件构造器
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        // 添加查询条件
        queryWrapper.eq(category.getType() != null,Category::getType,category.getType());

        // 添加排序条件

        queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);

        List<Category> categoryList = categoryService.list(queryWrapper);

        return R.success(categoryList);
    }
}

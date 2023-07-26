package com.cui.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cui.common.BaseContext;
import com.cui.common.R;
import com.cui.entity.Employee;
import com.cui.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * 用户登陆
     * @param httpServletRequest
     * @param employee
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public R<Employee> login(HttpServletRequest httpServletRequest, @RequestBody Employee employee){
        //1、将页面提交的密码password进行md5加密处理
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        //2、根据页面提交的用户名username查询数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);
        //3、如果没有查询到则返回登录失败结果
        if(emp == null){
            return R.error("用户名不存在");
        }

        //4、密码比对，如果不一致则返回登录失败结果

        if(!emp.getPassword().equals(password)){
            return R.error("用户名或密码不正确");
        }

        //5、查看员工状态，如果为已禁用状态，则返回员工已禁用结果

        if(emp.getStatus() == 0){
            R.error("登录失败，账号已禁用");
        }

        //6、登录成功，将员工id存入Session并返回登录成功结果
        httpServletRequest.getSession().setAttribute("employee",emp.getId());

        return R.success(emp);
    }

    /**
     * 退出登陆
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    public R<String> logout(HttpServletRequest httpServletRequest){
        httpServletRequest.getSession().removeAttribute("employee");
        return R.success("退出登录");
    }

    /**
     * 保存用户
     * @param request
     * @param employee
     * @return
     */
    @PostMapping
    public R<String> save(HttpServletRequest request , @RequestBody Employee employee){

        // 设置密码
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

        // 设置创建时间 修改时间
//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());

        // 设置创建人 修改人
//        Long empId = (Long) request.getSession().getAttribute("employee");
//        employee.setCreateUser(empId);
//        employee.setUpdateUser(empId);
        employeeService.save(employee);
        return R.success("保存成功");
    }


    /**
     * 分页查询员工
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @RequestMapping("/page")
    public R<Page> page(@RequestParam("page")int page,@RequestParam("pageSize")int pageSize, String name){

        // 创建分页构造器
        Page pageInfo = new Page(page, pageSize);

        // 创建条件查询器
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.like(StringUtils.isNotEmpty(name),Employee::getName,name);

        queryWrapper.orderByDesc(Employee::getUpdateTime);

        // 执行查询
        employeeService.page(pageInfo,queryWrapper);
        log.info("page={},pageSize={},name={}",page,pageSize,name);
        return R.success(pageInfo);
    }

    /**
     * 更新用户
     * @param request
     * @param employee
     * @return
     */
    @PutMapping
    public R<String> update(HttpServletRequest request , @RequestBody Employee employee){

        // 获取当前操作用户的id
        Long empId = (Long) request.getSession().getAttribute("employee");
        // 更新用户操作人id
//        employee.setUpdateTime(LocalDateTime.now());
//        employee.setUpdateUser(empId);
        // 更新用户的状态
        employeeService.updateById(employee);
        return R.success("更新用户成功");
    }

    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable String id){
        Employee employee = employeeService.getById(id);

        if (employee != null){
            return R.success(employee);
        }
        return R.error("没有查询到员工信息");
    }
}

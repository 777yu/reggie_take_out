package com.cui.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cui.common.R;
import com.cui.entity.Employee;
import com.cui.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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
}

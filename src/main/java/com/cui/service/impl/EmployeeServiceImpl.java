package com.cui.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cui.entity.Employee;
import com.cui.mapper.EmployeeMapper;
import com.cui.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}

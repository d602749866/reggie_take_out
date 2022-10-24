package com.wmpt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wmpt.common.R;
import com.wmpt.entity.Employee;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户登录功能实现
 */
public interface EmployeeService extends IService<Employee> {
    R<Employee> login(HttpServletRequest request, Employee employee);
}

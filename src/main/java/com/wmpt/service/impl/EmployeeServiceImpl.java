package com.wmpt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wmpt.common.R;
import com.wmpt.entity.Employee;
import com.wmpt.mapper.EmployeeMapper;
import com.wmpt.service.EmployeeService;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

    @Override
    public R<Employee> login(HttpServletRequest request, Employee employee) {
        // 1.密码md5加密操作
        String passward = DigestUtils.md5DigestAsHex(employee.getPassword().getBytes());
        // 2.根据用户名username查找数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = getOne(queryWrapper);
        // 3.如果没找到返回错误
        if (emp == null) {
            return R.error("用户名不存在！");
        }
        // 4.判断密码是否正确
        if (!emp.getPassword().equals(passward)) {
            return R.error("密码错误！");
        }
        // 5.判断用户账号是否被冻结
        if (emp.getStatus() == 0) {
            return R.error("用户已被冻结");
        }

        // 6.登陆成功，将员工id存入session
        request.getSession().setAttribute("employee", emp.getId());
        return R.success(emp);

    }
}

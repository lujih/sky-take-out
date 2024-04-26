package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.dto.PasswordEditDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * 新增员工
     */
    void add(EmployeeDTO employeeDTO, String token);

    /**
     * 分页查询
     */
    PageResult pageFind(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 修改启用禁用员工
     */
    void updateStatus(Integer status, long id);

    /**
     * 根据id查询员工数据回显
     */
    Employee getEmployeeById(long id);

    /**
     * 编辑员工数据
     */
    void updateEmp(EmployeeDTO employeeDTO);

    /**
     * 员工修改密码
     *
     */
    void updatePassword(PasswordEditDTO passwordEditDTO);
}

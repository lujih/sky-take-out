package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.PasswordConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.dto.PasswordEditDTO;
import com.sky.entity.Employee;
import com.sky.exception.AccountLockedException;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.PasswordErrorException;
import com.sky.mapper.EmployeeMapper;
import com.sky.result.PageResult;
import com.sky.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        //对密码进行md5加密
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }

    /*
     * 新增员工
     *
     * */
    @Override
    public void add(EmployeeDTO employeeDTO, String token) {
        //创建员工实体类
        Employee employee = new Employee();
        //属性拷贝
        BeanUtils.copyProperties(employeeDTO, employee);
        //设置账号可用性
        employee.setStatus(StatusConstant.ENABLE);
        //设置默认密码
        employee.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));

        //设置创建时间和修改时间
        //employee.setCreateTime(LocalDateTime.now());
        //employee.setUpdateTime(LocalDateTime.now());

        //设置创建人
        //获取本地线程存储的数据
        //Long id = BaseContext.getCurrentId();
        //employee.setCreateUser(id);
        //employee.setUpdateUser(id);
        //手动清除
        //BaseContext.removeCurrentId();
        //调用方法
        employeeMapper.add(employee);
    }

    /**
     * 分页查询
     */
    @Override
    public PageResult pageFind(EmployeePageQueryDTO employeePageQueryDTO) {
        PageHelper.startPage(employeePageQueryDTO.getPage(), employeePageQueryDTO.getPageSize());
        Page<Employee> page = employeeMapper.find(employeePageQueryDTO);
        PageResult pageResult = new PageResult(page.getTotal(), page.getResult());
        return pageResult;
    }

    /**
     * 修改启用禁用员工
     */
    @Override
    public void updateStatus(Integer status, long id) {
        Employee employee = new Employee();
        employee.setId(id);
        employee.setStatus(status);
        //employee.setUpdateTime(LocalDateTime.now());
        //employee.setUpdateUser(BaseContext.getCurrentId());

        employeeMapper.update(employee);
    }

    /**
     * 根据id查询员工数据回显
     */
    @Override
    public Employee getEmployeeById(long id) {
        Employee employee = employeeMapper.getEmployeeById(id);
        employee.setPassword("****");
        return employee;
    }

    /**
     * 编辑员工数据
     */
    @Override
    public void updateEmp(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        //获取本地线程传递的id
        //employee.setUpdateUser(BaseContext.getCurrentId());
        //BaseContext.removeCurrentId();
        //设置修改时间
        //employee.setUpdateTime(LocalDateTime.now());
        employeeMapper.update(employee);
    }

    /**
     * 员工修改密码
     *
     */
    @Override
    public void updatePassword(PasswordEditDTO passwordEditDTO) {
        Employee employee = employeeMapper.getEmployeeById(BaseContext.getCurrentId());
        String oldPassword = DigestUtils.md5DigestAsHex(passwordEditDTO.getOldPassword().getBytes());
        String newPassword = DigestUtils.md5DigestAsHex(passwordEditDTO.getNewPassword().getBytes());

        if (employee.getPassword().equals(oldPassword)) {
            employee.setPassword(newPassword);
            //employee.setUpdateTime(LocalDateTime.now());
            //employee.setUpdateUser(BaseContext.getCurrentId());
            employeeMapper.update(employee);
        }else {
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }
    }
}

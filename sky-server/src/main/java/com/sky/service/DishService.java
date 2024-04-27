package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;

import java.util.List;

public interface DishService {

    /**
     * 新增菜品
     */
    void addDish(DishDTO dishDTO);

    /**
     * 菜品分页查询
     */
    PageResult dishPage(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 批量删除菜品
     */
    void deleteDish(List<Long> ids);
}

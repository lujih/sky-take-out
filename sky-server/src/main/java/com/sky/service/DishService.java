package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

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

    /**
     * 根据id查询菜品
     *
     * @param id
     * @return
     */
    DishVO getDishById(Long id);

    /*
     * 修改菜品
     * */
    void updateDish(DishDTO dishDTO);

    /**
     * 菜品起售、停售
     *
     * @param status
     * @param id
     */
    void updateStatus(Integer status, Long id);

    /**
     * 根据菜品分类id查询数据
     *
     * @param categoryId
     * @return
     */
    List<Dish> getDishByCategoryId(Long categoryId);

    /**
     * 条件查询菜品和口味
     *
     * @param dish
     * @return
     */
    List<DishVO> listWithFlavor(Dish dish);
}

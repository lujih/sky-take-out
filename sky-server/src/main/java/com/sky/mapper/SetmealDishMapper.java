package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealDishMapper {

    /**
     * 根据id查询菜品对应套餐id
     * @param ids
     * @return
     */
    List<Long> getById(List<Long> ids);

    /**
     * 添加套餐关联菜品
     * @param setmealDish
     */
    void addSetmealDish(SetmealDish setmealDish);
}

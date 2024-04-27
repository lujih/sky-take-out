package com.sky.mapper;

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
}

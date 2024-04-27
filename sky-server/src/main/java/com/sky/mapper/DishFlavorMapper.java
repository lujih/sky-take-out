package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DishFlavorMapper {

    /**
     * 新增菜品口味
     *
     * @param flavors
     */
    void addDishFlavor(List<DishFlavor> flavors);

    /**
     * 删除菜品关联口味的数据
     * @param id
     */
    @Delete("delete from dish_flavor where dish_id = #{id}")
    void deleteDishFlavor(Long id);
}

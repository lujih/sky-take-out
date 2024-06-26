package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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
     *
     * @param id
     */
    @Delete("delete from dish_flavor where dish_id = #{id}")
    void deleteDishFlavor(Long id);

    /**
     * 批量删除菜品关联口味的数据
     *
     * @param ids
     */
    void PdeleteDishFlavor(List<Long> ids);

    /**
     * 根据id查询菜品口味
     *
     * @param id
     * @return
     */
    @Select("select * from dish_flavor where dish_id=#{id}")
    List<DishFlavor> getByDishID(Long id);
}

package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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

    /**
     * 根据套餐id删除关联菜品
     * @param ids
     */
    void delet(List<Long> ids);

    /**
     * 根据id查询套餐关联的菜品
     * @param id
     * @return
     */
    @Select("select * from setmeal_dish where setmeal_id = #{id}")
    List<SetmealDish> getByOneId(Long id);
}

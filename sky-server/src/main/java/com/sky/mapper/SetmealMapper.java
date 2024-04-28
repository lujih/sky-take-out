package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealMapper {

    /**
     * 根据分类id查询套餐的数量
     *
     * @param id
     * @return
     */
    @Select("select count(id) from setmeal where category_id = #{categoryId}")
    Integer countByCategoryId(Long id);

    /**
     * 添加套餐
     *
     * @param setmeal
     */
    @AutoFill(OperationType.INSERT)
    void addSetmeal(Setmeal setmeal);

    /**
     * 分页查询套餐
     *
     * @param setmealPageQueryDTO
     * @return
     */
    Page<Setmeal> getsetmealPage(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * 根据套餐id查询套餐数据
     *
     * @param ids
     * @return
     */
    List<Setmeal> getById(List<Long> ids);

    /**
     * 删除套餐
     *
     * @param ids
     */
    void delet(List<Long> ids);

    /**
     * 根据id查询套餐
     * @param id
     * @return
     */
    @Select("select * from setmeal where id = #{id}")
    Setmeal getByOneId(Long id);
}

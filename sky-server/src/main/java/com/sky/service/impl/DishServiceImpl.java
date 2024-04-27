package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;

    /**
     * 新增菜品
     */
    @Override
    @Transactional //开启事务
    public void addDish(DishDTO dishDTO) {
        //添加菜品
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.addDish(dish);
        //添加菜品口味
        Long dishId = dish.getId();
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && flavors.size() > 0) {
            for (DishFlavor df : flavors) {
                df.setDishId(dishId);
            }
            dishFlavorMapper.addDishFlavor(flavors);
        }

    }

    /**
     * 菜品分页查询
     */
    @Override
    public PageResult dishPage(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        Page<DishVO> page = dishMapper.dishPage(dishPageQueryDTO);
        PageResult pageResult = new PageResult(page.getTotal(), page.getResult());
        return pageResult;
    }

    /**
     * 批量删除菜品
     */
    @Override
    public void deleteDish(List<Long> ids) {
        //是否存在起售中菜品
        for (Long id : ids) {
            Dish dish = dishMapper.getById(id);
            if (dish.getStatus() == StatusConstant.ENABLE) {
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }
        //是否被套餐关联
        List<Long> setmealIds = setmealDishMapper.getById(ids);
        if (setmealIds != null && setmealIds.size() > 0) {
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_DISH);
        }
        /*//删除菜品表中的菜品数据
        for (Long id : ids) {
            dishMapper.deleteDish(id);
            //删除菜品关联口味的数据
            dishFlavorMapper.deleteDishFlavor(id);
        }*/
        //批量删除
        dishMapper.PdeleteDish(ids);
        dishFlavorMapper.PdeleteDishFlavor(ids);
    }

    /**
     * 根据id查询菜品及口味
     *
     * @param id
     * @return
     */
    @Override
    public DishVO getDishById(Long id) {
        //查询菜品
        Dish dish = dishMapper.getById(id);
        //查询菜品关联的口味
        List<DishFlavor> dishFlavor = dishFlavorMapper.getByDishID(id);
        //创建返回数据对象
        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish, dishVO);
        dishVO.setFlavors(dishFlavor);
        return dishVO;
    }

    @Override
    public void updateDish(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.update(dish);
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && flavors.size() > 0) {
            dishFlavorMapper.deleteDishFlavor(dish.getId());
            for (DishFlavor df : flavors) {
                df.setDishId(dish.getId());
            }
            dishFlavorMapper.addDishFlavor(flavors);
        }
    }
}

package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;

    @Override
    public void addSetmeal(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        //先添加套餐
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmealMapper.addSetmeal(setmeal);
        //获取套餐关联的菜品
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        //添加套餐关联菜品
        for (SetmealDish setmealDish : setmealDishes) {
            setmealDish.setSetmealId(setmeal.getId());
            setmealDishMapper.addSetmealDish(setmealDish);
        }
    }

    /**
     * 分页查询套餐
     *
     * @param setmealPageQueryDTO
     * @return
     */
    @Override
    public PageResult setmealPage(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());
        Page<Setmeal> page = setmealMapper.getsetmealPage(setmealPageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 根据id批量删除套餐
     * @param ids
     */
    @Override
    public void deleteSetmeal(List<Long> ids) {
        //根据id查询套餐数据
        List<Setmeal> setmeals = setmealMapper.getById(ids);
        //起售中的套餐不能删除
        for (Setmeal setmeal : setmeals) {
            if (setmeal.getStatus() == StatusConstant.ENABLE){
                throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
            }
        }
        //批量删除
        setmealMapper.delet(ids);
        setmealDishMapper.delet(ids);
    }

    /**
     * 根据id查询套餐
     * @param id
     * @return
     */
    @Override
    public SetmealVO getById(Long id) {
        Setmeal setmeal = setmealMapper.getByOneId(id);
        List<SetmealDish> setmealDishs = setmealDishMapper.getByOneId(id);

        SetmealVO setmealVO = new SetmealVO();
        BeanUtils.copyProperties(setmeal, setmealVO);
        setmealVO.setSetmealDishes(setmealDishs);
        return setmealVO;
    }
}

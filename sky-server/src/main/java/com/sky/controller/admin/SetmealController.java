package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin/setmeal")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @PostMapping
    public Result addSetmeal(@RequestBody SetmealDTO setmealDTO) {
        log.info("添加套餐:{}", setmealDTO);
        setmealService.addSetmeal(setmealDTO);
        return Result.success();
    }

    @GetMapping("/page")
    public Result<PageResult> getSetmealPage(SetmealPageQueryDTO setmealPageQueryDTO) {
        log.info("套餐分页查询: {}", setmealPageQueryDTO);
        PageResult pageResult = setmealService.setmealPage(setmealPageQueryDTO);
        return Result.success(pageResult);
    }

    @DeleteMapping
    public Result deleteSetmeal(@RequestParam List<Long> ids) {
        log.info("根据id批量删除套餐:{}",ids);
        setmealService.deleteSetmeal(ids);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<SetmealVO> getSetmeal(@PathVariable Long id) {
        log.info("根据id查询套餐:{}",id);
        SetmealVO setmealVO = setmealService.getById(id);
        return Result.success(setmealVO);
    }
}


package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/admin/dish")
public class DishController {

    @Autowired
    private DishService dishService;
    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping
    public Result addDish(@RequestBody DishDTO dishDTO) {
        log.info("添加菜品: {}", dishDTO);
        dishService.addDish(dishDTO);
        //清除缓存
        redisTemplate.delete("dish_" + dishDTO.getCategoryId());
        return Result.success();
    }

    @GetMapping("/page")
    public Result<PageResult> DishPage(DishPageQueryDTO dishPageQueryDTO) {
        log.info("菜品分页查询: {}", dishPageQueryDTO);
        PageResult pageResult = dishService.dishPage(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    @DeleteMapping
    public Result deleteDish(@RequestParam List<Long> ids) {
        log.info("批量删除菜品:{}", ids);
        dishService.deleteDish(ids);
        //清除缓存
        Set keys = redisTemplate.keys("dish_");
        redisTemplate.delete(keys);
        return Result.success();
    }

    /**
     * 根据id查询菜品
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<DishVO> getDish(@PathVariable Long id) {
        log.info("根据id查询菜品:{}", id);
        DishVO dishVO = dishService.getDishById(id);
        return Result.success(dishVO);
    }

    /**
     * 修改菜品数据
     *
     * @param dishDTO
     * @return
     */
    @PutMapping
    public Result updateDish(@RequestBody DishDTO dishDTO) {
        log.info("修改菜品数据:{}",dishDTO);
        dishService.updateDish(dishDTO);
        //清除缓存
        Set keys = redisTemplate.keys("dish_");
        redisTemplate.delete(keys);
        return Result.success();
    }

    /**
     * 菜品起售、停售
     *
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    public Result updateDishStatus(@PathVariable Integer status, Long id) {
        log.info("菜品起售、停售:{}{}", status, id);
        dishService.updateStatus(status, id);
        //清除缓存
        Set keys = redisTemplate.keys("dish_");
        redisTemplate.delete(keys);
        return Result.success();
    }

    /**
     * 根据菜品分类id查询数据
     *
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    public Result<List<Dish>> getDishList(Long categoryId) {
        log.info("根据菜品分类id查询数据:{}", categoryId);
        List<Dish> dishs = dishService.getDishByCategoryId(categoryId);
        return Result.success(dishs);
    }
}


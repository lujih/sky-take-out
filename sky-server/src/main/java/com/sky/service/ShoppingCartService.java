package com.sky.service;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {

    /**
     * 添加购物车
     * @param shoppingCartDTO
     */
    void add(ShoppingCartDTO shoppingCartDTO);

    /**
     * 查看购物车
     * @return
     */
    List<ShoppingCart> viewShoppingCart();

    /**
     * 删除商品
     * @param shoppingCartDTO
     */
    void deletingProducts(ShoppingCartDTO shoppingCartDTO);

    /**
     * 清空购物车
     */
    void clean();
}

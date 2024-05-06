package com.sky.mapper;

import com.sky.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderDetailMapper {

    /**
     * 用户下单
     * @param orderDetail
     */
    void creatingAnOrder(OrderDetail orderDetail);
}

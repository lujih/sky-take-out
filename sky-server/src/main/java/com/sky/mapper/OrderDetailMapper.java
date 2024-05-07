package com.sky.mapper;

import com.sky.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderDetailMapper {

    /**
     * 用户下单
     * @param orderDetail
     */
    void creatingAnOrder(OrderDetail orderDetail);

    /**
     * 订单id查询订单详情
     * @param orderid
     * @return
     */
    @Select("select * from order_detail where order_id = #{orderid}")
    List<OrderDetail> orderInquiry(Long orderid);
}

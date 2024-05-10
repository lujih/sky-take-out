package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface OrderMapper {

    /**
     * 创建订单
     *
     * @param orders
     */
    void creatingAnOrder(Orders orders);

    /**
     * 根据订单号查询订单
     *
     * @param orderNumber
     */
    @Select("select * from orders where number = #{orderNumber}")
    Orders getByNumber(String orderNumber);

    /**
     * 修改订单信息
     *
     * @param orders
     */
    void update(Orders orders);

    @Update("update orders set status = #{orderStatus},pay_status = #{orderPaidStatus} ,checkout_time = #{check_out_time} where id = #{id}")
    void updateStatus(Integer orderStatus, Integer orderPaidStatus, LocalDateTime check_out_time, Long id);

    /**
     * 历史订单查询
     *
     * @param ordersPageQueryDTO
     * @return
     */
    Page<Orders> orderInquiry(OrdersPageQueryDTO ordersPageQueryDTO);

    /**
     * 查询订单详情id
     *
     * @param id
     * @return
     */
    @Select("select * from orders where id = #{id}")
    Orders getById(Long id);

    /**
     * 各个状态的订单数量统计
     *
     * @param status
     * @return
     */
    Integer countStatus(LocalDateTime begin,Integer status);

    /**
     * 获取支付超时的订单
     *
     * @param status
     * @param time
     * @return
     */
    @Select("select * from orders where status = #{status} and order_time < #{time}")
    List<Orders> getByStatusAndOrderTime(Integer status, LocalDateTime time);

    /**
     * 根据时间查询订单
     * @param begin
     * @param end
     * @param status
     * @return
     */
    @Select("select sum(amount) from orders where status = #{status} and order_time BETWEEN #{begin} AND #{end};")
    Double getAmountByTime(LocalDateTime begin, LocalDateTime end, Integer status);

    /**
     * 获取订单数
     * @param orders
     * @return
     */
    Integer getTotalOrders(Orders orders);

    /**
     * 根据日期获取订单数
     * @param beginTime
     * @param endTime
     * @return
     */
    Integer getOrderNumber(LocalDateTime beginTime, LocalDateTime endTime, Integer status );
}


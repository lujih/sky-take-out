package com.sky.mapper;

import com.sky.dto.GoodsSalesDTO;
import com.sky.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface OrderDetailMapper {

    /**
     * 用户下单
     *
     * @param orderDetail
     */
    void creatingAnOrder(OrderDetail orderDetail);

    /**
     * 订单id查询订单详情
     *
     * @param orderid
     * @return
     */
    @Select("select * from order_detail where order_id = #{orderid}")
    List<OrderDetail> orderInquiry(Long orderid);

    /**
     * 查询销量排名top10
     *
     * @param beginTime
     * @param endTime
     * @param status
     */
    @Select("select od.name,sum(od.number) number " +
            "from order_detail od,orders o " +
            "where od.order_id = o.id and o.status = #{status} and o.order_time BETWEEN #{beginTime} AND #{endTime} " +
            "group by od.name " +
            "order by number " +
            "limit 0,10")
    List<GoodsSalesDTO> getTop10(LocalDateTime beginTime, LocalDateTime endTime, Integer status);
}

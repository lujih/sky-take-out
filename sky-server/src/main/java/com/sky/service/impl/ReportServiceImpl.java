package com.sky.service.impl;

import com.sky.dto.GoodsSalesDTO;
import com.sky.entity.Orders;
import com.sky.mapper.OrderDetailMapper;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.ReportService;
import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;

    /**
     * 营业额统计
     *
     * @param begin
     * @param end
     * @return
     */
    @Override
    public TurnoverReportVO turnoverStatistics(LocalDate begin, LocalDate end) {
        //整理营业时间
        List<LocalDate> datelist = new ArrayList<>();
        datelist.add(begin);
        while (!begin.equals(end)) {
            //计算间隔日期
            begin = begin.plusDays(1);
            datelist.add(begin);
        }
        //转为字符串
        String time = StringUtils.join(datelist, ",");

        //计算营业额
        List<Double> turnoverList = new ArrayList<>();
        for (LocalDate date : datelist) {
            //计算每天的开始和结束具体时间
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);
            Double amount = orderMapper.getAmountByTime(beginTime, endTime, Orders.COMPLETED);
            amount = amount == null ? 0.0 : amount;
            turnoverList.add(amount);
        }
        String turnover = StringUtils.join(turnoverList, ",");

        TurnoverReportVO turnoverReportVO = new TurnoverReportVO(time, turnover);
        return turnoverReportVO;
    }

    /**
     * 用户统计
     *
     * @param begin
     * @param end
     * @return
     */
    @Override
    public UserReportVO userStatistics(LocalDate begin, LocalDate end) {
        //整理营业时间
        List<LocalDate> datelist = new ArrayList<>();
        datelist.add(begin);
        while (!begin.equals(end)) {
            //计算间隔日期
            begin = begin.plusDays(1);
            datelist.add(begin);
        }
        //转为字符串
        String time = StringUtils.join(datelist, ",");

        List<Integer> newuserList = new ArrayList<>();
        List<Integer> alluserList = new ArrayList<>();

        for (LocalDate date : datelist) {
            //计算每天的开始和结束具体时间
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);
            Integer newusers = userMapper.getUserByTime(beginTime, endTime);
            newusers = newusers == null ? 0 : newusers;
            newuserList.add(newusers);
            Integer allusers = userMapper.getallUserByTime(endTime);
            allusers = allusers == null ? 0 : allusers;
            alluserList.add(allusers);
        }
        String newuser = StringUtils.join(newuserList, ",");
        String alluser = StringUtils.join(alluserList, ",");


        return new UserReportVO(time, alluser, newuser);
    }

    /**
     * 订单统计
     *
     * @param begin
     * @param end
     * @return
     */
    @Override
    public OrderReportVO ordersStatistics(LocalDate begin, LocalDate end) {
        //整理营业时间
        List<LocalDate> datelist = new ArrayList<>();
        datelist.add(begin);
        while (!begin.equals(end)) {
            //计算间隔日期
            begin = begin.plusDays(1);
            datelist.add(begin);
        }
        //转为字符串
        String time = StringUtils.join(datelist, ",");
        Orders orders = new Orders();
        //订单总数
        Integer totalOrderCount = orderMapper.getTotalOrders(orders);
        //有效订单数
        orders.setStatus(Orders.COMPLETED);
        Integer validOrderCount = orderMapper.getTotalOrders(orders);
        //订单完成率
        double orderCompletionRate = (double) validOrderCount / totalOrderCount;
        //计算每日数据
        List<Integer> OrderNumbers = new ArrayList<>();
        List<Integer> validOrderNumbers = new ArrayList<>();

        for (LocalDate date : datelist) {
            //计算每天的开始和结束具体时间
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);
            //每日订单数
            Integer OrderNumber = orderMapper.getOrderNumber(beginTime, endTime, null);
            OrderNumber = OrderNumber == null ? 0 : OrderNumber;
            OrderNumbers.add(OrderNumber);
            //每日完成订单数
            Integer validOrderNumber = orderMapper.getOrderNumber(beginTime, endTime, Orders.COMPLETED);
            validOrderNumber = validOrderNumber == null ? 0 : validOrderNumber;
            validOrderNumbers.add(validOrderNumber);
        }
        String orderCountList = StringUtils.join(OrderNumbers, ",");
        String validOrderCountList = StringUtils.join(validOrderNumbers, ",");


        return new OrderReportVO(time, orderCountList, validOrderCountList, totalOrderCount, validOrderCount, orderCompletionRate);
    }

    /**
     * 查询销量排名top10
     *
     * @param begin
     * @param end
     * @return
     */
    @Override
    public SalesTop10ReportVO top10(LocalDate begin, LocalDate end) {
        //计算开始和结束具体时间
        LocalDateTime beginTime = LocalDateTime.of(begin, LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(end, LocalTime.MAX);

        List<GoodsSalesDTO> list = orderDetailMapper.getTop10(beginTime, endTime, Orders.COMPLETED);

        List<String> nameLists = new ArrayList<>();
        List<Integer> numberLists = new ArrayList<>();
        for (GoodsSalesDTO goodsSalesDTO : list) {
            nameLists.add(goodsSalesDTO.getName());
            numberLists.add(goodsSalesDTO.getNumber());
        }
        String namelist = StringUtils.join(nameLists, ",");
        String numberlist = StringUtils.join(numberLists, ",");

        return new SalesTop10ReportVO(namelist, numberlist);
    }
}

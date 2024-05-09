package com.sky.service.impl;

import com.sky.entity.Orders;
import com.sky.mapper.OrderDetailMapper;
import com.sky.mapper.OrderMapper;
import com.sky.service.ReportService;
import com.sky.vo.TurnoverReportVO;
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
    private OrderDetailMapper orderDetailMapper;


    /**
     * 营业额统计接口
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
        List<Double> turnoverList = new ArrayList<>();
        for (LocalDate date : datelist) {
            //计算每天的开始和结束具体时间
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);
            Double amount = orderMapper.getByTime(beginTime, endTime, Orders.COMPLETED);
            amount = amount == null ? 0.0 : amount;
            turnoverList.add(amount);
        }
        String turnover = StringUtils.join(turnoverList, ",");

        TurnoverReportVO turnoverReportVO = new TurnoverReportVO(time,turnover);
        return turnoverReportVO;
    }
}

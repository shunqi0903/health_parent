package com.shunqi.dao;

import com.shunqi.pojo.Order;

import java.util.Date;
import java.util.List;

public interface OrderDao {

    static void add(Order order) {
    }

    public List<Order> findByCondition(Order order);

    public int findVisitsNumberByDate(String date);

    int thisMonthOrderNumber(String firstDay4ThisMonth);

    int thisMonthVisitsNumber(String firstDay4ThisMonth);

    int thisWeekOrderNumber(String thisWeekMonday);

    int thisWeekVisitsNumber(String thisWeekMonday);

    int todayOrderNumber(String today);
}

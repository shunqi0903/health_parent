package com.shunqi.dao;

import com.shunqi.pojo.Order;

import java.util.List;

public interface OrderDao {

    static void add(Order order) {
    }

    public List<Order> findByCondition(Order order);
}

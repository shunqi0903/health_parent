package com.shunqi.service;

import com.shunqi.pojo.OrderSetting;

import java.util.Date;
import java.util.List;

public interface OrderSettingService {
    public void add(List<OrderSetting> list);

    public List<OrderSetting> getOrderSettingByMonth(String date);
    public void editNumberByDate(Date orderDate , Integer number);
}
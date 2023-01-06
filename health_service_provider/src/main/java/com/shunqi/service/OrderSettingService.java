package com.shunqi.service;

import com.shunqi.pojo.OrderSetting;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface OrderSettingService {
    public void add(List<OrderSetting> list);

    public List<OrderSetting> getOrderSettingByMonth(String date) throws ParseException;

    public void editNumberByDate(Date orderDate , Integer number) throws ParseException;
}

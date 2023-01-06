package com.shunqi.dao;

import com.shunqi.pojo.OrderSetting;
import com.shunqi.pojo.Setmeal;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface OrderSettingDao {

    public void add(OrderSetting orderSetting);

    public long countByOrderDate(Date orderDate);

    void editReservationsByOrderDate(OrderSetting orderSetting);

    public OrderSetting findByOrderDate(Date date);

    public void update(OrderSetting orderSetting);

    public List<OrderSetting> getOrderSettingByMonth(HashMap<String, Date> map);

    public void editNumberByDate(@Param("orderDate") Date orderDate, @Param("number") Integer number);

}

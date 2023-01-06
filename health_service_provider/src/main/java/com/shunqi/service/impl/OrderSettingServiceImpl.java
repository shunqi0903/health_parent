package com.shunqi.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.shunqi.dao.OrderSettingDao;
import com.shunqi.pojo.OrderSetting;
import com.shunqi.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service(interfaceClass = OrderSettingService.class)
public class OrderSettingServiceImpl implements OrderSettingService {
    @Autowired
    private OrderSettingDao orderSettingDao;

    @Override
    public void add(List<OrderSetting> orderSettingList) {
        if (orderSettingList.size() > 0) {
            for (OrderSetting orderSetting : orderSettingList) {
                Date orderDate = orderSetting.getOrderDate();
                long count = orderSettingDao.countByOrderDate(orderDate);
                if (count > 0) {
                    orderSettingDao.update(orderSetting);
                } else {
                    orderSettingDao.add(orderSetting);
                }
            }
        }
    }

    @Override
    public List<OrderSetting> getOrderSettingByMonth(String date) throws ParseException {
//        Date dateBegin = new Date(date + "-1");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateBegin = sdf.parse(date + "-1");
        Date dateEnd = sdf.parse(date + "-31");
//        Date dateEnd = new Date(date + "-31");
        HashMap<String, Date> map = new HashMap<>();
        map.put("dateBegin",dateBegin);
        map.put("dateEnd",dateEnd);
        List<OrderSetting> orderSettingList = orderSettingDao.getOrderSettingByMonth(map);
        return orderSettingList;
    }

    @Override
    public void editNumberByDate(Date orderDate , Integer number) throws ParseException {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Date orderDate = sdf.parse(dateStr);
        orderSettingDao.editNumberByDate(orderDate,number);
    }
}

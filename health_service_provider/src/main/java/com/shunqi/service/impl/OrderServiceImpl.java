package com.shunqi.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.unit.DataUnit;
import com.shunqi.constant.MessageConstant;
import com.shunqi.dao.MemberDao;
import com.shunqi.dao.OrderDao;
import com.shunqi.dao.OrderSettingDao;
import com.shunqi.entity.Result;
import com.shunqi.pojo.Member;
import com.shunqi.pojo.Order;
import com.shunqi.pojo.OrderSetting;
import com.shunqi.service.OrderService;
import com.shunqi.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class OrderServiceImpl implements OrderService {
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private OrderSettingDao orderSettingDao;
    @Autowired
    private OrderDao orderDao;
    @Override
    public Result order(Map map) {
//        1、检查用户所选择的预约日期是否已经提前进行了预约设置，如果没有设置则无法进行预约
        String orderDate = (String) map.get("orderDate");
        OrderSetting orderSetting ;
        Date date;
        try {
            date = DateUtils.parseString2Date(orderDate);
            orderSetting = orderSettingDao.findByOrderDate(date);
        } catch (Exception ignored) {
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }
        if(orderSetting == null){
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }
//        2、检查用户所选择的预约日期是否已经约满，如果已经约满则无法预约
        int number = orderSetting.getNumber();
        int reservations = orderSetting.getReservations();
        if(number == reservations){
            return new Result(false,MessageConstant.ORDER_FULL);
        }
//        3、检查用户是否重复预约（同一个用户在同一天预约了同一个套餐），如果是重复预约则无法完成再次预约
//        4、检查当前用户是否为会员，如果是会员则直接完成预约，如果不是会员则自动完成注册并进行预约
        String telephone = (String) map.get("telephone");
        Member member = memberDao.findByTelephone(telephone);
        if(member != null){
            Integer memberId = member.getId();
            Integer setmealId = Integer.parseInt((String) map.get("setmealId"));
            Order order = new Order(memberId,date,null,null,setmealId);
            List<Order> list = orderDao.findByCondition(order);
            if(list.size() > 0){
                return new Result(false,MessageConstant.HAS_ORDERED);
            }
        }
        orderSetting.setReservations(reservations+1);
        orderSettingDao.editReservationsByOrderDate(orderSetting);
        if(member == null){
            member = new Member();
            member.setName((String) map.get("name"));
            member.setIdCard((String) map.get("idCard"));
            member.setPhoneNumber(telephone);
            member.setSex((String) map.get("sex"));
            member.setRegTime(new Date());
            memberDao.add(member);
        }
//        5、预约成功，更新当日的已预约人数
        Order order = new Order();
        order.setMemberId(member.getId());
        order.setOrderDate(date);
        order.setOrderType((String) map.get("orderType"));
        order.setOrderStatus(Order.ORDERTYPE_WEIXIN);
        order.setSetmealId((Integer) map.get("stealId"));
        OrderDao.add(order);
        return new Result(true,MessageConstant.ORDER_SUCCESS,order.getId());
    }
}

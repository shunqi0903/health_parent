package com.shunqi.service.impl;

import com.shunqi.dao.MemberDao;
import com.shunqi.dao.OrderDao;
import com.shunqi.dao.SetmealDao;
import com.shunqi.service.OrderService;
import com.shunqi.service.ReportService;
import com.shunqi.service.SetmealService;
import com.shunqi.util.DateUtils;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Reference
public class ReportServiceImpl implements ReportService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private SetmealDao setmealDao;
    @Autowired
    private SetmealService setmealService;
    @Override
    public Map<String, Object> getBusinessReportData() throws Exception {
        String today = DateUtils.parseDate2String(DateUtils.getToday());
        String thisWeekMonday = DateUtils.parseDate2String(DateUtils.getThisWeekMonday());
        String firstDay4ThisMonth = DateUtils.parseDate2String(DateUtils.getFirstDay4ThisMonth());
        Map<String, Object> data = new HashMap<>();
        // "todayNewMember"
        int todayNewMember = memberDao.todayNewMember(today);
        data.put("todayVisitsNumber",todayNewMember);
        //totalMember
        int totalMember = memberDao.totalMember();
        data.put("totalMember",totalMember);
        //thisWeekNewMember
        int thisWeekNewMember =memberDao.thisWeekNewMember(thisWeekMonday);
        data.put("thisWeekNewMember",thisWeekNewMember);
        //thisMonthNewMember
        int thisMonthNewMember =memberDao.thisMonthNewMember(firstDay4ThisMonth);
        data.put("thisMonthNewMember",thisMonthNewMember);
        //todayOrderNumber
        int todayOrderNumber = orderDao.todayOrderNumber(today);
        data.put("todayOrderNumber",todayOrderNumber);
        // "todayVisitsNumber"
        int visitsNumberByDate = orderDao.findVisitsNumberByDate(today);
        data.put("todayVisitsNumber",visitsNumberByDate);
        //thisWeekOrderNumber
        int thisWeekOrderNumber  = orderDao.thisWeekOrderNumber(thisWeekMonday);
        data.put("thisWeekOrderNumber",thisWeekOrderNumber );
        //thisWeekVisitsNumber
        int thisWeekVisitsNumber = orderDao.thisWeekVisitsNumber(thisWeekMonday);
        data.put("thisWeekVisitsNumber",thisWeekVisitsNumber);
        //thisMonthOrderNumber
        int thisMonthOrderNumber = orderDao.thisMonthOrderNumber(firstDay4ThisMonth);
        data.put("thisMonthOrderNumber",thisMonthOrderNumber);
        //thisMonthVisitsNumber
        int thisMonthVisitsNumber = orderDao.thisMonthVisitsNumber(firstDay4ThisMonth);
        data.put("thisMonthVisitsNumber",thisMonthVisitsNumber);
        //hotSetmeal
        List<Map<String,Object>> hotSetmeal = setmealDao.hotSetmeal();
        data.put("hotSetmeal",hotSetmeal);
        return data;
    }
}
